package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.Document;
import feup.sdle.crypto.MD5HashAlgorithm;
import feup.sdle.message.HashRingMessage;
import feup.sdle.message.Hashcheck;
import feup.sdle.message.Message;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.math.BigInteger;
import java.util.*;

@Component
public class Node {
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);
    private final NodeIdentifier identifier;
    private HashRing ring;
    private ZContext zmqContext;
    private static final int REPLICATION_FACTOR = 2;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets
    private GossipService gossipService;
    private HashRingSyncService hashRingSyncService;
    private MemoryStorageProvider<String, Document> storage;
    private boolean starter;

    /**
    * The HashRing can already be populated, which is useful for start bootstraping
    * purposes as well as testing
    */
    public Node(@Value("${node.id}") int id,
                @Value("${node.hostname}") String hostname,
                @Value("${node.port}") int port,
                @Value("${node.starter}") boolean starter) {

        this.zmqContext = new ZContext();

        this.identifier = new NodeIdentifier(id, hostname, port, true);

        this.starter = starter;

        this.manageHashRing();

        this.storage = new MemoryStorageProvider<>(new FileStorageProvider());

        this.gossipService = new GossipService(this, this.zmqContext);
        this.hashRingSyncService = new HashRingSyncService(this, this.ring, this.gossipService, 10000, 3);
    }

    public void manageHashRing() {
        this.ring = new HashRing(new MD5HashAlgorithm(), this.identifier.getId(), this );

        if(!this.starter) {
           this.tryToJoinRing();
        }
    }

    private void tryToJoinRing() {
        int ringSize = this.ring.getRing().size();
        int randomIndex = new Random().nextInt(ringSize);

        Iterator<Map.Entry<BigInteger, NodeIdentifier>> iterator = this.ring.getRing().entrySet().iterator();
        for (int i = 0; i < randomIndex; i++) {
            iterator.next();
        }

        NodeIdentifier chosenNode = iterator.next().getValue();
        chosenNode.getSocket(this.zmqContext).send(Message.MessageFormat.newBuilder()
                .setNodeIdentifier(this.getNodeIdentifier().toMessageNodeIdentifier())
                .setMessageType(Message.MessageFormat.MessageType.HASHRING_JOIN)
                .build().toByteArray());
    }

    public boolean getStarter() {
        return this.starter;
    }

    public int getPort() {
        return this.identifier.getPort();
    }

    public ZContext getZmqContext() {
        return this.zmqContext;
    }

    public List<NodeIdentifier> getPreferenceList() {
        List<NodeIdentifier> result = new ArrayList<>();
        boolean foundLocalNode = false;
        int count = 0;

        for (Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
            boolean nodeIsLocalNode = entry.getValue().getId() == this.identifier.getId();
            if (nodeIsLocalNode) {
                foundLocalNode = true;
            }

            if(count >= REPLICATION_FACTOR) {
                break;
            }

            if(!nodeIsLocalNode && foundLocalNode) {
                result.add(entry.getValue());
                count++;
            }
        }

        return result;
    }

    public void processAddNodeRequest(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        try {
            // 1. Add node to ring
            this.ring.addNode(senderNode);

            // 2. Send the current view of the ring to the sender node
            HashRingMessage.HashRing.Builder hashRingBuilder = HashRingMessage.HashRing.newBuilder();
            for(Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
                hashRingBuilder.putNodes(entry.getKey().toString(), entry.getValue().toMessageNodeIdentifier());
            }

            senderNode.getSocket(this.zmqContext).send(
                    Message.MessageFormat.newBuilder()
                            .setMessageType(Message.MessageFormat.MessageType.HASHRING_GET)
                            .setNodeIdentifier(senderNode.toMessageNodeIdentifier())
                            .setMessage(
                                    hashRingBuilder.build().toByteString()
                            )
                            .build()
                            .toByteArray()
            );

            for(Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
                if(entry.getValue().getId() == senderNode.getId()) {
                    System.out.println("HERE MYMAN!!!");
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public void processHashRingSyncMessage(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        try {
            this.hashRingSyncService.processMessage(msgFormat, senderNode);
        } catch (Exception e) {
            System.out.println("Processing hash ring sync message");
            LOGGER.error(e.toString());
        }
    }

    public void processRingLogHashCheck(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        try {
            this.hashRingSyncService.processMessage(msgFormat, senderNode);
        } catch (InvalidProtocolBufferException e) {
            LOGGER.error(e.toString());
        }
    }

    public NodeIdentifier getNodeIdentifier() {
        return this.identifier;
    }

    public GossipService getGossipService() {
        return gossipService;
    }

    public Optional<Document> retrieveDocument(String key) {
        return this.storage.retrieve(key);
    }

    public Map<String, Document> retrieveAllDocuments() {
        return this.storage.retrieveAll();
    }

    public void storeDocument(String key, Document document) {
        this.storage.store(key, document);
    }

    public void deleteDocument(String key) {
        this.storage.delete(key);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Node node)) return false;

        return this.identifier.equals(node.getNodeIdentifier());
    }
}
