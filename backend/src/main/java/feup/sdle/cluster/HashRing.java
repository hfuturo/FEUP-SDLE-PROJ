package feup.sdle.cluster;

import feup.sdle.cluster.ring.operations.AddNodeOperation;
import feup.sdle.cluster.ring.operations.RemoveNodeOperation;
import feup.sdle.crypto.HashAlgorithm;
import feup.sdle.crdts.DotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HashRing {
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);
    private static final int REPLICATION_FACTOR = 2;
    private static final int NODE_REPLICAS = 3;
    private final TreeMap<BigInteger, NodeIdentifier> ring;
    private final HashAlgorithm hashAlgorithm;
    private DotContext dotContext;
    private HashRingLog hashRingLog;

    public HashRing(HashAlgorithm hashAlgorithm, Integer nodeIdentifier) {
        this.ring = new TreeMap<>();
        this.hashAlgorithm = hashAlgorithm;

        this.hashRingLog = new HashRingLog(nodeIdentifier);
        this.generateSeedNodes();
    }

    private void generateSeedNodes() {
        NodeIdentifier seed1 = new NodeIdentifier(100001, "localhost", 4321, true);
        NodeIdentifier seed2 = new NodeIdentifier(100002, "localhost", 4322, true);
        NodeIdentifier seed3 = new NodeIdentifier(100003, "localhost", 4323, true);

        try {
            this.addNode(seed1);
            this.addNode(seed2);
            this.addNode(seed3);

            LOGGER.info("Seed Nodes generated");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private NodeIdentifier findKeyMasterNode(BigInteger hash) {
        BigInteger possibleKey = this.ring.ceilingKey(hash);

        if(possibleKey == null) {
            possibleKey = this.ring.firstKey();
        }

        return this.ring.get(possibleKey);
    }

    /**
     * It is meant to be used by the holders of this ring (e.g. the Node class) to determine if they are the masters
     * of this key or not. This is needed because the behaviour of the node receiving the request will be different
     * depending on whether the node is the master of that key (e.g. if it is not the master, it will not
     * store the key);
     *
     */
    public boolean isNodeKeyMaster(String key, NodeIdentifier nodeIdentifier) {
        try {
            NodeIdentifier masterNode = this.findKeyMasterNode(this.hashAlgorithm.getHash(key));

            return masterNode.getId() == nodeIdentifier.getId();
        } catch(Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Chooses the next n nodes higher than the hashed key
     * clockwise in the ring
     *
     * This should be used when the node that receives the request for the key is the node
     * that is the main responsible for that key
    */
    public NodeIdentifier getClosestNodes(String key, int n) {
        try {
            BigInteger hashedKey = this.hashAlgorithm.getHash(key);
            return null;
        } catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Chooses the main node for this key and the next n nodes higher than the hashed key
     * clockwise in the ring
     *
     * This should be used when the node that receives the request for the key is not the node
     * that is the main responsible for that key
    */
    public NodeIdentifier getPreferenceNodes(String key, int n) {
       try {
           BigInteger hashedKey = this.hashAlgorithm.getHash(key);
           return null;
       } catch(Exception e) {
           System.out.println(e.toString());
           return null;
       }
    }

    public void addNode(NodeIdentifier nodeIdentifier) throws Exception {
        if (this.ring.containsValue(nodeIdentifier))
            throw new Exception("Node with identifier " + nodeIdentifier.toString() + " already exists.");

        List<BigInteger> nodesToAdd = new ArrayList<>();

        for (int i = 0; i < NODE_REPLICAS; i++) {
            BigInteger nodeHash = this.hashAlgorithm.getHash(nodeIdentifier.toString() + i);
            this.ring.put(nodeHash, nodeIdentifier);
            nodesToAdd.add(nodeHash);
        }

        this.hashRingLog.add(new AddNodeOperation(nodesToAdd, nodeIdentifier));
    }

    public void removeNode(NodeIdentifier nodeIdentifier) throws Exception {
        if (!this.ring.containsValue(nodeIdentifier))
            throw new Exception("Node with identifier " + nodeIdentifier.toString() + " does not exit.");

        var entries = this.ring.entrySet();
        List<BigInteger> nodesToRemove = new ArrayList<>();

        for (Map.Entry<BigInteger, NodeIdentifier> entry : entries) {
            if (nodeIdentifier.equals(entry.getValue())) {
                this.ring.remove(entry.getKey());
                nodesToRemove.add(entry.getKey());
            }
        }

        this.hashRingLog.add(new RemoveNodeOperation(nodesToRemove, nodeIdentifier));
    }

}