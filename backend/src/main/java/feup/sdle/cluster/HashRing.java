package feup.sdle.cluster;

import feup.sdle.crypto.HashAlgorithm;
import feup.sdle.crdts.DotContext;

import java.math.BigInteger;
import java.util.TreeMap;

public class HashRing {
    private final TreeMap<BigInteger, NodeIdentifier> ring;
    private final HashAlgorithm hashAlgorithm;
    private DotContext dotContext;
    private HashRingLog hashRingLog;

    public HashRing(HashAlgorithm hashAlgorithm) {
        this.ring = new TreeMap<>();
        this.hashAlgorithm = hashAlgorithm;

        this.hashRingLog = new HashRingLog(1);
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

    public void addNode(Node node) {
    }

    public void removeNode(Node node) {
    }
}