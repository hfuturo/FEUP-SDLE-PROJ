package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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
    public List<BigInteger> getHashes() {
        return this.hashesToRemove;
    }

    @Override
    public NodeIdentifier getNodeIdentifier() {
        return this.nodeIdentifier;
    }

    @Override
    public HashRingOperationType getOperationType() {
        return HashRingOperationType.REMOVE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hashesToRemove, this.nodeIdentifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoveNodeOperation otherOperation = (RemoveNodeOperation) o;
        return this.hashesToRemove.equals(otherOperation.hashesToRemove) && this.nodeIdentifier.equals(otherOperation.nodeIdentifier);
    }

    @Override
    public void reproduce() {

    }

    @Override
    public HashRingLogOperation reverse() {
        return new AddNodeOperation(this.hashesToRemove, this.nodeIdentifier);
    }
}
