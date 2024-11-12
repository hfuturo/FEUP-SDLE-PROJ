package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import feup.sdle.ProtobufSerializable;
import feup.sdle.cluster.ring.operations.HashRingLogOperation;
import feup.sdle.crdts.HashRingLongTimestamp;
import feup.sdle.crdts.VersionStamp;
import feup.sdle.message.HashRingOperationMessage;

import java.util.*;

/**
 * It is a list of the operations done over the hash ring. It can only add elements.
 *
 * It is meant to be shared and merged between the replicas and if they got
 * desync for some reason and have conflicts, they will always get the same result. For this case
 * we are going to use vector clocks which is simulated through the DotContext
 */
public class HashRingLog implements ProtobufSerializable {
    private List<HashRingLongTimestamp<HashRingLogOperation>> operations;
    private int localIdentifier;
    private int dot;
    private int currentSequenceNumber;

    // This is to avoid full scans of the log upon merging. If we store the last sequence number
    // seen from a node, when we are merging the two logs we can scan only from that sequence number
    // instead of performing a full scan
    private HashMap<VersionStamp, Integer> sequenceNumberReplicaMapping;

    public HashRingLog(int localIdentifier) {
        this.operations = new ArrayList<>();
        this.localIdentifier = localIdentifier;
        this.currentSequenceNumber = 1;
        this.dot = 1;

        this.sequenceNumberReplicaMapping = new HashMap<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.operations);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof HashRingLog hashRingLog)) return false;

        return this.operations.equals(hashRingLog.operations) && this.currentSequenceNumber == hashRingLog.currentSequenceNumber
                && this.dot == hashRingLog.dot && this.localIdentifier == hashRingLog.localIdentifier;
    }

    public List<HashRingLongTimestamp<HashRingLogOperation>> getOperations() {
        return this.operations;
    }

    public void getOperationsStr() {
        System.out.println("Log entry: ");
        System.out.println(this.operations.size());
        for(HashRingLongTimestamp<HashRingLogOperation> operation: this.operations) {
            System.out.print("(" + operation.getSequenceNumber() + "," + operation.getVersionStamp().getIdentifier() + "," + operation.getVersionStamp().getDot() + ")");
        }
        System.out.print("\n");

        System.out.println("Log entry ended");
    }

    public void add(HashRingLogOperation operation) {
        this.operations.add(new HashRingLongTimestamp<>(operation.getNodeIdentifier(), dot, operation, this.currentSequenceNumber));

        this.currentSequenceNumber++;
        this.dot++;
    }

    /**
     *
     * @return The index of the operations array from where we must start applying the new operations
     */
    public synchronized int merge(HashRingLog other) {
        synchronized (this) {
            try {
                int localSeqConflictStart = -1;
                int otherSeqConflictStart = -1;
                boolean conflictFound = false;
                int applyIndex = -1;

                // 1. Detect if there are conflicts (if there are different versions with same sequence number)
                for (int i = 0; i < this.operations.size(); i++) {
                    HashRingLongTimestamp<HashRingLogOperation> localOperation = this.operations.get(i);

                    for (int j = 0; j < other.operations.size(); j++) {
                        HashRingLongTimestamp<HashRingLogOperation> otherOperation = other.operations.get(j);

                        if ((localOperation.getSequenceNumber() == otherOperation.getSequenceNumber())
                                && (!Objects.equals(localOperation.getIdentifier(), otherOperation.getIdentifier()))) {
                            localSeqConflictStart = i;
                            otherSeqConflictStart = j;
                            conflictFound = true;

                            break;
                        }
                    }

                    if (conflictFound) break;
                }

                if (conflictFound) {
                    // 1. Obtain the operation in conflict with the same sequence number
                    HashRingLongTimestamp<HashRingLogOperation> localOperation = this.operations.get(localSeqConflictStart);
                    HashRingLongTimestamp<HashRingLogOperation> otherOperation = other.operations.get(otherSeqConflictStart);

                    // 2. Obtain the sublist with the common elements with same sequence number and same version between the two logs
                    List<HashRingLongTimestamp<HashRingLogOperation>> common = localSeqConflictStart == 0 ?
                            new ArrayList<>() :
                            this.operations.subList(0, localSeqConflictStart);

                    // We have to undo the operations which are common between the conflicted sequences
                    List<HashRingLongTimestamp<HashRingLogOperation>> toUndo = new ArrayList<>(
                            this.operations.subList(localSeqConflictStart, this.operations.size())
                    );
                    toUndo.removeAll(other.operations.subList(otherSeqConflictStart, other.operations.size()));

                    // 3. Compare the version stamp of the conflicting timestamps
                    if (localOperation.getVersionStamp().compareTo(otherOperation.getVersionStamp()) < 0) {
                        // 1. Add to the common list between the two hash ring logs the operations of the local replica
                        common.addAll(this.operations.subList(localSeqConflictStart, this.operations.size()));
                        applyIndex = common.size(); // index from where the local node does not have

                        List<HashRingLongTimestamp<HashRingLogOperation>> replacedList = other.operations.subList(otherSeqConflictStart, other.operations.size());
                        replacedList.removeAll(common);

                        this.updateSequenceNumber(common.getLast().getSequenceNumber() + 1, replacedList);
                        common.addAll(replacedList);
                    } else {
                        common.addAll(other.operations.subList(otherSeqConflictStart, other.operations.size()));
                        applyIndex = localSeqConflictStart + 1; // The other operations will be right on the localSeqConflictStart index, so we must start applying from that point

                        List<HashRingLongTimestamp<HashRingLogOperation>> replacedList = this.operations.subList(localSeqConflictStart, this.operations.size());
                        replacedList.removeAll(common);

                        this.updateSequenceNumber(common.getLast().getSequenceNumber() + 1, replacedList);
                        common.addAll(replacedList);
                    }

                    this.operations = common;
                } else {
                    applyIndex = this.operations.size();

                    List<HashRingLongTimestamp<HashRingLogOperation>> difference = new ArrayList<>(other.operations);
                    difference.removeAll(this.operations);

                    // We don't need to change the sequence numbers because there were not any conflicts
                    this.operations.addAll(difference);

                    Collections.sort(this.operations);
                }

                this.sequenceNumberReplicaMapping.put(new VersionStamp(other.localIdentifier, this.operations.size()), this.operations.size());
                return applyIndex;
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }

            return 0;
        }
    }

    public static HashRingLog fromHashRingLogMessage(HashRingOperationMessage.HashRingLogOperationMessage msg, NodeIdentifier senderNode) {
        HashRingLog log = new HashRingLog(msg.getReplicaId());

        for(HashRingOperationMessage.HashRingLogTimestamp operation: msg.getTimestampsList()) {
            HashRingLongTimestamp<HashRingLogOperation> timestamp = new HashRingLongTimestamp<>(
                    NodeIdentifier.fromMessageNodeIdentifier(operation.getIdentifier()),
                    operation.getDot(),
                    HashRingLogOperation.fromHashRingOperationMessage(operation.getOperation(), NodeIdentifier.fromMessageNodeIdentifier(operation.getIdentifier())),
                    operation.getSequence()
            );

            log.getOperations().add(timestamp);
        }

        return log;
    }

    /**
     * Update sequence number in case of conflicts of the replaced sublist
     */
    public void updateSequenceNumber(Integer baseValue, List<HashRingLongTimestamp<HashRingLogOperation>> list) {
        int offset = 0;
        for(HashRingLongTimestamp<HashRingLogOperation> value: list) {
            value.setSequenceNumber(baseValue + offset);
            offset++;
        }
    }

    @Override
    public ByteString toProtoBuf() {
        return this.toMessage().toByteString();
    }

    @Override
    public HashRingOperationMessage.HashRingLogOperationMessage toMessage() {
        HashRingOperationMessage.HashRingLogOperationMessage.Builder msgBuilder = HashRingOperationMessage.HashRingLogOperationMessage.newBuilder();

        // 1. Set local replica id
        msgBuilder.setReplicaId(this.localIdentifier);

        // 2. Set operations list
        this.operations.forEach(el -> msgBuilder.addTimestamps(el.toMessage()));

        return msgBuilder.build();
    }
}