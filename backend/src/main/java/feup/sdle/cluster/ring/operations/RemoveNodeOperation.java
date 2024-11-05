package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.math.BigInteger;
import java.util.List;

/**
 * This operation is meant to indicate that hashes were removed from a node
 */
public class RemoveNodeOperation implements HashRingLogOperation {
    private List<BigInteger> hashesToRemove;
    private NodeIdentifier nodeIdentifier;

    public RemoveNodeOperation(List<BigInteger> hashesToRemove, NodeIdentifier nodeIdentifier) {
        this.hashesToRemove = hashesToRemove;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public void reproduce() {

    }

    @Override
    public HashRingLogOperation reverse() {
        return new AddNodeOperation(this.hashesToRemove, this.nodeIdentifier);
    }
}
