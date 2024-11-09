package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.math.BigInteger;
import java.util.List;

/**
 * This operation is meant to indicate that some hashes were removed from some node
 */
public class AddNodeOperation implements HashRingLogOperation {
    private List<BigInteger> hashesToAdd;
    private NodeIdentifier nodeIdentifier;
    private int mock;

    public AddNodeOperation(int mock) {
        this.mock = mock;
    }

    public int getMock() {
        return this.mock;
    }

    public AddNodeOperation(List<BigInteger> hashesToAdd, NodeIdentifier nodeIdentifier) {
        this.hashesToAdd = hashesToAdd;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for(BigInteger o: hashesToAdd) {
            hash += o.hashCode();
        }

        return hash;
    }

    @Override
    public void reproduce() {

    }

    @Override
    public HashRingLogOperation reverse() {
        return new RemoveNodeOperation(this.hashesToAdd, this.nodeIdentifier);
    }
}
