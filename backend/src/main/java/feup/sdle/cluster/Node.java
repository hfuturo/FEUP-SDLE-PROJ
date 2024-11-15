package feup.sdle.cluster;

import feup.sdle.Document;
import feup.sdle.crypto.MD5HashAlgorithm;
import feup.sdle.message.Message;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class Node {
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);
    private final NodeIdentifier identifier;
    private final HashRing ring;
    private ZContext zmqContext;
    private static final int REPLICATION_FACTOR = 2;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets
    private GossipService gossipService;
    private HashRingSyncService hashRingSyncService;
    private HashRingDocumentsService hashRingDataService;
    private MemoryStorageProvider<String, Document> storage;

    /**
    * The HashRing can already be populated, which is useful for start bootstraping
    * purposes as well as testing
    */
    public Node(@Value("${node.id}") int id,
                @Value("${node.hostname}") String hostname,
                @Value("${node.port}") int port) {

        this.zmqContext = new ZContext();

        this.identifier = new NodeIdentifier(id, hostname, port, true);

        this.ring = this.manageHashRing();

        this.storage = new MemoryStorageProvider<>(new FileStorageProvider());

        this.gossipService = new GossipService(this, this.zmqContext);
        this.hashRingSyncService = new HashRingSyncService(this, this.ring, this.gossipService, 10000, 3);
    }

    public HashRing manageHashRing() {
        HashRing hashRing = new HashRing(new MD5HashAlgorithm(), this.identifier.getId());

        try {
            hashRing.addNode(this.identifier);
            this.retrieveDocumentsFromRing();
        }
        catch (Exception e) { LOGGER.error(Color.red(e.getMessage())); }

        return hashRing;
    }

    private void retrieveDocumentsFromRing() throws NoSuchAlgorithmException {
        // get positions of all the identifiers in the ring
        for (int i = 0; i < HashRing.VIRTUAL_NODES; i++) {
            Set<HashRange> ranges = new HashSet<>();

            BigInteger nodeHash = this.ring.hashAlgorithm.getHash(this.identifier.toString() + i);
            BigInteger nextNodeHash = this.ring.ceilingKey(nodeHash);
            if (nextNodeHash.equals(nodeHash)) {
                nextNodeHash = this.ring.higherKey(nextNodeHash);
            }

            BigInteger previousNodeHash = this.ring.floorKey(nodeHash);
            if (previousNodeHash.equals(nodeHash)) {
                previousNodeHash = this.ring.lowerKey(previousNodeHash);
            }
            ranges.add(new HashRange(previousNodeHash, nodeHash));

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

            Map<HashRange, List<NodeIdentifier>> rangeSourcesMap = new HashMap<>();
            for (var range : ranges) {
                rangeSourcesMap.put(range, this.getOtherResponsibleNodes(range).reversed()); // reverse to prioritize the node that will lost the documents
            }

            //get every document between ranges from respective nodes
            for (var item : rangeSourcesMap.entrySet()) {
                HashRange range = item.getKey();
                List<NodeIdentifier> sources = item.getValue();

                for (var source : sources) {
                    Boolean ok = this.hashRingDataService.requestDocumentsFromRange(source, range);
                    if (ok) {
                        break;
                    }
                }
            }
        }
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

    public void processHashRingSyncMessage(Message.MessageFormat msgFormat, NodeIdentifier senderNode) {
        try {
            this.hashRingSyncService.processMessage(msgFormat.getMessage(), senderNode);
        } catch (Exception e) {
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
