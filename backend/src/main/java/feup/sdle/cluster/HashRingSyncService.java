package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.cluster.ring.HashRingSyncState;
import feup.sdle.cluster.ring.operations.HashRingLogOperation;
import feup.sdle.crdts.HashRingLongTimestamp;
import feup.sdle.message.HashRingOperationMessage;
import feup.sdle.message.HashRingOperationMessage.HashRingOperation;
import feup.sdle.message.HashRingOperationMessage.HashRingLogOperationMessage;
import feup.sdle.message.Hashcheck;
import feup.sdle.message.Hashcheck.HashCheck;
import feup.sdle.message.Message;
import feup.sdle.message.Message.MessageFormat;
import feup.sdle.message.NodeIdentifierMessage;
import org.zeromq.ZMQ;

import java.math.BigInteger;
import java.util.List;

/**
 * This is the service responsible to sync the hash ring state by publishing the state
 * in the specified gossip service
 */
public class HashRingSyncService {
    private GossipService gossipService;
    private int timeoutMs;
    private int fanout;
    private HashRing hashRing;
    private HashRingSyncState hashRingSyncState;
    private Node node;

    public HashRingSyncService(Node node, HashRing hashRing, GossipService gossipService, int timeoutMs, int fanout) {
        this.gossipService = gossipService;
        this.timeoutMs = timeoutMs;
        this.fanout = fanout;
        this.hashRing = hashRing;
        this.hashRingSyncState = HashRingSyncState.RECEIVINGHASH;
        this.node = node;

        this.initSyncService();
    }

    /**
     * When a node is receiving a hash it only needs to check local hash
     * If the hash is different it needs to send the current log to the other node
     */
    public void processMessage(MessageFormat msgFormat, NodeIdentifier senderNode) throws InvalidProtocolBufferException {
        switch(msgFormat.getMessageType()) {
            case HASHRING_LOG_HASH_CHECK -> {
                HashCheck hashCheck = HashCheck.parseFrom(msgFormat.getMessage());
                HashRingLog hashRingLog = this.hashRing.getHashRingLog();

                if(!String.valueOf(hashRingLog.hashCode()).equals(hashCheck.getHash())) {
                    System.out.println("HASH DIFFERENT, SENDING HASH RING LOG");
                    this.hashRing.getHashRingLog().getOperationsStr();
                    this.sendHashRingLog(senderNode);
                }

            }
            case HASH_RING_LOG -> {
                HashRingLogOperationMessage hashRingLogOperationMessage = HashRingLogOperationMessage.parseFrom(msgFormat.getMessage());
                HashRingLog otherHashRingLog = HashRingLog.fromHashRingLogMessage(hashRingLogOperationMessage, senderNode);

                int startApplyIndex = this.hashRing.getHashRingLog().merge(otherHashRingLog);
                System.out.println("APPLY INDEX: " + startApplyIndex);
                this.hashRing.applyOperations(startApplyIndex);
            }
        }
    }

    private void sendHashRingLog(NodeIdentifier nodeToSend) {
        ZMQ.Socket socket = nodeToSend.getSocket(this.node.getZmqContext());

        System.out.println("Isn't this sending the hash ring log: " + this.node.getNodeIdentifier().toMessageNodeIdentifier());

        MessageFormat msgFormat = MessageFormat.newBuilder().setMessageType(MessageFormat.MessageType.HASH_RING_LOG)
                .setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier())
                .setMessage(this.hashRing.getHashRingLog().toProtoBuf())
                .build();

        socket.send(msgFormat.toByteArray());
        this.hashRingSyncState = HashRingSyncState.SENTLOG;
    }

    private void initSyncService() {
        Thread.ofVirtual().start(() -> {
            while(true) {
                for(var node: this.hashRing.getRing().values()) {
                    System.out.println("SKILL ISSUE: " + node.getHttpPort());
                }
                try {
                    Thread.sleep(this.timeoutMs);
                    HashCheck hashCheck = HashCheck.newBuilder()
                            .setHash(String.valueOf(this.hashRing.getHashRingLog().hashCode()))
                            .setType(HashCheck.ContextType.forNumber(HashCheck.ContextType.HASHRINGLOG_VALUE))
                            .build();

                    NodeIdentifier currentNodeIdentifier = this.node.getNodeIdentifier();
                    MessageFormat msgFormat = MessageFormat.newBuilder()
                            .setMessageType(MessageFormat.MessageType.HASHRING_LOG_HASH_CHECK)
                            .setNodeIdentifier(NodeIdentifierMessage.NodeIdentifier.newBuilder()
                                    .setHostname(currentNodeIdentifier.getHostName())
                                    .setId(currentNodeIdentifier.getId())
                                    .setPort(currentNodeIdentifier.getPort())
                                    .build()
                            )
                            .setMessage(hashCheck.toByteString())
                            .build();

                    System.out.println("Sending hash check message!");
                    this.hashRing.getHashRingLog().getOperationsStr();

                    this.gossipService.publish(this.fanout, msgFormat.toByteArray());
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

    public Node getNode() {
        return this.node;
    }

}
