package feup.sdle.cluster;

import feup.sdle.cluster.ring.operations.HashRingLogOperation;
import feup.sdle.crdts.HashRingLongTimestamp;

import java.util.*;

/**
 * It is a list of the operations done over the hash ring. It can only add elements.
 *
 * It is meant to be shared and merged between the replicas and if they got
 * desync for some reason and have conflicts, they will always get the same result. For this case
 * we are going to use vector clocks which is simulated through the DotContext
 */
public class HashRingLog {
    private List<HashRingLongTimestamp<HashRingLogOperation>> operations;
    private int localIdentifier;
    private int dot;
    private int currentSequenceNumber;

    public HashRingLog(int localIdentifier) {
        this.operations = new ArrayList<>();
        this.localIdentifier = localIdentifier;
        this.currentSequenceNumber = 1;
    }

    public void getOperationsStr() {
        System.out.println("Log entry: ");
        for(HashRingLongTimestamp<HashRingLogOperation> operation: this.operations) {
            System.out.print(operation.getValue().getMock() + ", ");
        }
        System.out.print("\n");

        System.out.println("Log entry ended");
    }

    public void add(HashRingLogOperation operation) {
        this.operations.add(new HashRingLongTimestamp<>(this.localIdentifier, dot, operation, this.currentSequenceNumber));

        this.currentSequenceNumber++;
        this.dot++;
    }

    public void merge(HashRingLog other) {
        int localSeqConflictStart = -1;
        int otherSeqConflictStart = -1;
        boolean conflictFound = false;

        // 1. Detect if there are conflicts (if there are different versions with same sequence number)
        for(int i = 0; i < this.operations.size(); i++) {
            HashRingLongTimestamp<HashRingLogOperation> localOperation = this.operations.get(i);

            for(int j = 0; j < other.operations.size(); j++) {
                HashRingLongTimestamp<HashRingLogOperation> otherOperation = other.operations.get(j);

                if((localOperation.getSequenceNumber() == otherOperation.getSequenceNumber())
                        && (!Objects.equals(localOperation.getIdentifier(), otherOperation.getIdentifier()))) {
                    localSeqConflictStart = i;
                    otherSeqConflictStart = j;
                    conflictFound = true;

                    break;
                }
            }

            if(conflictFound) break;
        }

        if(conflictFound) {
            HashRingLongTimestamp<HashRingLogOperation> localOperation = this.operations.get(localSeqConflictStart);
            HashRingLongTimestamp<HashRingLogOperation> otherOperation = other.operations.get(otherSeqConflictStart);

            List<HashRingLongTimestamp<HashRingLogOperation>> common = localSeqConflictStart == 0 ? new ArrayList<>() : this.operations.subList(0, localSeqConflictStart + 1);

            if(localOperation.getVersionStamp().compareTo(otherOperation.getVersionStamp()) < 0) {
                common.addAll(this.operations.subList(localSeqConflictStart, this.operations.size()));

                List<HashRingLongTimestamp<HashRingLogOperation>> replacedList = other.operations.subList(otherSeqConflictStart, other.operations.size());
                this.updateSequenceNumber(common.getLast().getSequenceNumber() + 1, replacedList);
                common.addAll(replacedList);
            } else {
                common.addAll(other.operations.subList(otherSeqConflictStart, other.operations.size()));

                List<HashRingLongTimestamp<HashRingLogOperation>> replacedList = this.operations.subList(otherSeqConflictStart, other.operations.size());
                this.updateSequenceNumber(common.getLast().getSequenceNumber() + 1, replacedList);
                common.addAll(replacedList);
            }

            this.operations = common;
        } else {
            List<HashRingLongTimestamp<HashRingLogOperation>> difference = new ArrayList<>(other.operations);
            difference.removeAll(this.operations);

            // We don't need to change the sequence numbers because there were not any conflicts
            this.operations.addAll(difference);
            Collections.sort(this.operations);
        }
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
}