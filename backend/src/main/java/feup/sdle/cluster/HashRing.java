package feup.sdle.cluster;

import feup.sdle.cluster.ring.operations.AddNodeOperation;
import feup.sdle.cluster.ring.operations.RemoveNodeOperation;
import feup.sdle.crypto.HashAlgorithm;
import feup.sdle.crdts.DotContext;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HashRing {
    private static final Logger LOGGER = LoggerFactory.getLogger(HashRing.class);
    private static final int REPLICATION_FACTOR = 2;
    protected static final int VIRTUAL_NODES = 3;
    private final TreeMap<BigInteger, NodeIdentifier> ring;
    protected final HashAlgorithm hashAlgorithm;
    private DotContext dotContext;
    private HashRingLog hashRingLog;

    public HashRing(HashAlgorithm hashAlgorithm, Integer nodeIdentifier) {
        this.ring = new TreeMap<>();
        this.hashAlgorithm = hashAlgorithm;

        this.hashRingLog = new HashRingLog(nodeIdentifier);
        this.generateSeedNodes();
    }

    public TreeMap<BigInteger, NodeIdentifier> getRing() {
        return this.ring;
    }

    public HashRingLog getHashRingLog() {
        return this.hashRingLog;
    }

    private void generateSeedNodes() {
        NodeIdentifier seed1 = new NodeIdentifier(100001, "localhost", 4321, true);
        NodeIdentifier seed2 = new NodeIdentifier(100002, "localhost", 4322, true);
        NodeIdentifier seed3 = new NodeIdentifier(100003, "localhost", 4323, true);

        try {
            this.addNode(seed1);
            this.addNode(seed2);
            this.addNode(seed3);

            LOGGER.info(Color.green("Seed Nodes generated"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        for(Map.Entry<BigInteger, NodeIdentifier> entry : ring.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public NodeIdentifier get(BigInteger hash) {
        return this.ring.get(hash);
    }

    protected BigInteger lowerKey(BigInteger hash) {
        BigInteger previousKey = this.ring.lowerKey(hash);

        if(previousKey == null) {
            previousKey = this.ring.lastKey();
        }

        return previousKey;
    }

    protected BigInteger floorKey(BigInteger hash) {
        BigInteger floorKey = this.ring.floorKey(hash);

        if(floorKey == null) {
            floorKey = this.ring.lastKey();
        }

        return floorKey;
    }

    protected BigInteger higherKey(BigInteger hash) {
        BigInteger nextKey = this.ring.higherKey(hash);

        if(nextKey == null) {
            nextKey = this.ring.firstKey();
        }

        return nextKey;
    }

    protected BigInteger ceilingKey(BigInteger hash) {
        BigInteger ceilingKey = this.ring.ceilingKey(hash);

        if(ceilingKey == null) {
            ceilingKey = this.ring.firstKey();
        }

        return ceilingKey;
    }

    protected NodeIdentifier findKeyMasterNode(BigInteger hash) {
        BigInteger masterNodeKey = this.ceilingKey(hash);

        return this.ring.get(masterNodeKey);
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

        for (int i = 0; i < VIRTUAL_NODES; i++) {
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