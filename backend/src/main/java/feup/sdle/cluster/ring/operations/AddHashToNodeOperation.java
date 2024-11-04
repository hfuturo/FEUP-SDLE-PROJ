package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.math.BigInteger;
import java.util.List;

/**
 * This operation is meant to indicate that some hashes were removed from some node
 */
public class AddHashToNodeOperation implements HashRingLogOperation {
    private List<BigInteger> hashesToAdd;
    private NodeIdentifier nodeIdentifier;
    private int mock;

    public AddHashToNodeOperation(int mock) {
        this.mock = mock;
    }

    public int getMock() {
        return this.mock;
    }

    public AddHashToNodeOperation(List<BigInteger> hashesToAdd, NodeIdentifier nodeIdentifier) {
        this.hashesToAdd = hashesToAdd;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public void reproduce() {

    }
}
