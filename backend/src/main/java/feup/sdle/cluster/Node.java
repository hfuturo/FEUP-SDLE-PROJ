package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.Document;
import feup.sdle.ShoppingList;
import feup.sdle.crypto.MD5HashAlgorithm;
import feup.sdle.message.HashRingMessage;
import feup.sdle.message.Message;
import feup.sdle.message.NodeIdentifierMessage;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import feup.sdle.storage.StorageBucket;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.css.DocumentCSS;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import javax.print.Doc;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Node {
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);
    private final NodeIdentifier identifier;
    private HashRing ring;
    private ZContext zmqContext;
    public static final int REPLICATION_FACTOR = 2;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets
    private GossipService gossipService;
    private HashRingSyncService hashRingSyncService;
    private HashRingDocumentsService hashRingDocumentsService;
    private MemoryStorageProvider<String, Document> storage;
    private boolean starter;
    private NodeReceiver receiver;
    private NodeTransmitter transmitter;
    private int httpPort;
    private final ConcurrentHashMap<NodeIdentifier, List<Document>> offlineNodeDocuments;

    /**
     * The HashRing can already be populated, which is useful for start bootstraping
     * purposes as well as testing
     */
    public Node(@Value("${node.id}") int id,
            @Value("${node.hostname}") String hostname,
            @Value("${node.port}") int port,
            @Value("${node.starter}") boolean starter,
            @Value("${api.port}") int httpPort
    ) {
        this.httpPort = httpPort;


        this.zmqContext = new ZContext();

        this.transmitter = new NodeTransmitter(this, 2000, 3);

        this.identifier = new NodeIdentifier(id, hostname, port, true, httpPort);

        this.starter = starter;

        this.ring = this.manageHashRing();

        this.storage = new MemoryStorageProvider<String, Document>(new FileStorageProvider<String, Document>(new StorageBucket(String.format("%d", this.identifier.getId()))));

        this.gossipService = new GossipService(this, this.zmqContext);

        this.hashRingSyncService = new HashRingSyncService(this, this.ring, this.gossipService, 10000, 3);

        this.hashRingDocumentsService = new HashRingDocumentsService(this);

        this.receiver = new NodeReceiver(this);

        this.offlineNodeDocuments = new ConcurrentHashMap<>();

        if (!this.starter)
            this.tryToJoinRing();
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public ConcurrentHashMap<NodeIdentifier, List<Document>> getOfflineNodeDocuments() {
        return this.offlineNodeDocuments;
    }

    public HashRing getRing() {
        return this.ring;
    }

    public NodeTransmitter getTransmitter() {
        return this.transmitter;
    }

    public HashRing manageHashRing() {
        HashRing hashRing = new HashRing(new MD5HashAlgorithm(), this.identifier.getId(), this);

        try {
            hashRing.addNode(this.identifier);
            this.retrieveDocumentsFromRing();
        }
        catch (Exception e) { LOGGER.error(Color.red(e.getMessage())); }

        return hashRing;
    }

    /**
     * This should be executed by a node that wants to try to join the ring
     */
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

    private void retrieveDocumentsFromRing() throws NoSuchAlgorithmException {
        for (int i = 0; i < HashRing.VIRTUAL_NODES; i++) {

            List<HashRange> ranges = getOwnedRanges(i);
            Map<HashRange, List<NodeIdentifier>> rangeSourcesMap = new HashMap<>();
            for (var range : ranges) {
                rangeSourcesMap.put(range, this.getOtherResponsibleNodes(range).reversed()); // reverse to prioritize the node that will lost the documents
            }

            //get every document between ranges from respective nodes
            for (var item : rangeSourcesMap.entrySet()) {
                HashRange range = item.getKey();
                List<NodeIdentifier> sources = item.getValue();

                for (var source : sources) {
                    Boolean ok = this.hashRingDocumentsService.requestDocumentsFromRange(source, range);
                    if (ok) {
                        break;
                    }
                }
            }
        }
    }
    private List<HashRange> getOwnedRanges(int i) throws NoSuchAlgorithmException {

        List<HashRange> ranges = new LinkedList<>();

        // getting the "main" range
        BigInteger nodeHash = this.ring.hashAlgorithm.getHash(this.identifier.toString() + i);
        BigInteger nextNodeHash = this.ring.ceilingKey(nodeHash);

        BigInteger previousNodeHash = this.ring.floorKey(nodeHash);
        ranges.add(new HashRange(previousNodeHash, nodeHash));

        // getting other previous ranges
        int counter = 0;
        BigInteger previousPreviousNodeHash = this.ring.lowerKey(previousNodeHash);
        for (int j = 0; j < this.ring.getRing().size(); j++) { // avoiding infinite loops
            // otimization: if finds a range that virtual node is responsible, stop retrieving ranges
            if (counter >= REPLICATION_FACTOR || this.ring.get(previousNodeHash).equals(this.identifier)) {
                break;
            }
            ranges.add(new HashRange(previousPreviousNodeHash, previousNodeHash));
            previousNodeHash = previousPreviousNodeHash;
            counter++;
        }
        return ranges;
    }
    private List<NodeIdentifier> getOtherResponsibleNodes(HashRange range) {
        List<NodeIdentifier> sources = new LinkedList<>();
        sources.add(this.ring.get(range.end()));
        int counter = 0;
        BigInteger nextHash = this.ring.higherKey(range.end());
        for (int i = 0; i < this.ring.getRing().size(); i++) {
            if (counter >= REPLICATION_FACTOR) {
                break;
            }
            NodeIdentifier nextNode = this.ring.get(nextHash);
            if (!nextNode.equals(this.identifier)) {
                sources.add(nextNode);
                counter++;
            }
        }
        return sources;
    }

    protected List<Document> getDocumentsFromRange(HashRange range) throws NoSuchAlgorithmException {
        List<Document> documentList = new ArrayList<>();
        for (var entry : this.retrieveAllDocuments().entrySet()) {
            if (range.inRange(this.ring.hashAlgorithm.getHash(entry.getKey()))) {
                documentList.add(entry.getValue());
            }
        }
        return documentList;
    }

    public int getPort() {
        return this.identifier.getPort();
    }

    public ZContext getZmqContext() {
        return this.zmqContext;
    }

    /**
     * This is used to retrieve the nodes to send a message to.
     * It skips the unalive nodes and marks others as such.
     * 
     * @return
     */
    public List<NodeIdentifier> getPreferenceList() {
        List<NodeIdentifier> result = new ArrayList<>();
        boolean foundLocalNode = false;
        int count = 0;

        for (Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
            boolean nodeIsLocalNode = entry.getValue().getId() == this.identifier.getId();
            if (nodeIsLocalNode) {
                foundLocalNode = true;
            }

            if (count >= REPLICATION_FACTOR) {
                break;
            }

            if (!nodeIsLocalNode && foundLocalNode) {
                result.add(entry.getValue());
                count++;
            }
        }

        return result;
    }

    public void processHashRingView(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        try {
            HashRingMessage.HashRing hashringMessage = HashRingMessage.HashRing.parseFrom(msgFormat.getMessage());

            for (Map.Entry<String, NodeIdentifierMessage.NodeIdentifier> entry : hashringMessage.getNodesMap()
                    .entrySet()) {
                this.ring.getRing().put(new BigInteger(entry.getKey()),
                        NodeIdentifier.fromMessageNodeIdentifier(entry.getValue()));
            }
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public void processAddNodeRequest(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        System.out.println("Processing add node request from: " + senderNode.getId());
        try {
            // 1. Add node to ring
            this.ring.addNode(senderNode);

            // 2. Send the current view of the ring to the sender node
            HashRingMessage.HashRing.Builder hashRingBuilder = HashRingMessage.HashRing.newBuilder();
            for (Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
                hashRingBuilder.putNodes(entry.getKey().toString(), entry.getValue().toMessageNodeIdentifier());
            }

            senderNode.getSocket(this.zmqContext).send(
                    Message.MessageFormat.newBuilder()
                            .setMessageType(Message.MessageFormat.MessageType.HASHRING_GET)
                            .setNodeIdentifier(senderNode.toMessageNodeIdentifier())
                            .setMessage(
                                    hashRingBuilder.build().toByteString())
                            .build()
                            .toByteArray());

            for (Map.Entry<BigInteger, NodeIdentifier> entry : this.ring.getRing().entrySet()) {
                if (entry.getValue().getId() == senderNode.getId()) {
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
    public HashRingDocumentsService getHashRingDocumentsService() {
        return this.hashRingDocumentsService;
    }
    public Optional<Document> retrieveDocument(String key) {
        return this.storage.retrieve(key);
    }

    public Map<String, Document> retrieveAllDocuments() {
        return this.storage.retrieveAll();
    }

    public void storeDocumentAndReplicate(String key, Document document) {
        this.storeDocument(key, document);

        document.setNodeIdentifier(this.identifier); // We mask the node identifier of the client
        Thread.ofVirtual().start(() -> this.replicateDocument(key, document));
    }

    public void storeDocument(String key, Document document) {
        Optional<Document> localDocumentOpt = this.storage.retrieve(key);
        Document doc;

        if (localDocumentOpt.isEmpty()) {
            doc = document;
        }
        else {
            Document localDocument = localDocumentOpt.get();
            localDocument.merge(document);
            doc = localDocument;
        }

        this.storage.store(key, doc);
        System.out.println(Color.green("Stored document with key: " + key));
    }

    private void replicateDocument(String key, Document document) {
        List<NodeIdentifier> nodesToReplicate = this.ring.getPreferenceNodes(key, this.identifier);
        if (nodesToReplicate == null) return;

        this.hashRingDocumentsService.sendDocumentReplication(
                document,
                nodesToReplicate
        );
    }

    public void deleteDocument(String key) {
        this.storage.delete(key);
    }

    public void addDocumentsToOfflineNodes(Document document, NodeIdentifier offlineNode) {
        synchronized (this.offlineNodeDocuments) {
            if (this.offlineNodeDocuments.containsKey(offlineNode)) {
                this.offlineNodeDocuments.get(offlineNode).add(document);
            } else {
                List<Document> documents = new ArrayList<>();
                documents.add(document);
                this.offlineNodeDocuments.put(offlineNode, documents);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof Node node))
            return false;

        return this.identifier.equals(node.getNodeIdentifier());
    }

}
