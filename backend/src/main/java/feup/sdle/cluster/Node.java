package feup.sdle.cluster;

import feup.sdle.Document;
import feup.sdle.crypto.MD5HashAlgorithm;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.util.*;

@Component
public class Node {
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);
    private final NodeIdentifier identifier;
    private final List<NodeIdentifier> preferenceList;
    private final HashRing ring;
    private ZContext zmqContext;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets
    private GossipService gossipService;
    private HashRingSyncService hashRingSyncService;
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

        this.preferenceList = new ArrayList<>();

        this.gossipService = new GossipService(this, this.zmqContext);
        this.hashRingSyncService = new HashRingSyncService(this.gossipService, 2000, 3);
    }

    public HashRing manageHashRing() {
        HashRing hashRing = new HashRing(new MD5HashAlgorithm(), this.identifier.getId());

        try { hashRing.addNode(this.identifier); }
        catch (Exception e) { LOGGER.error(e.getMessage()); }

        return hashRing;
    }

    public int getPort() {
        return this.identifier.getPort();
    }

    public ZContext getZmqContext() {
        return this.zmqContext;
    }

    public List<NodeIdentifier> getPreferenceList() {
        return this.preferenceList;
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
