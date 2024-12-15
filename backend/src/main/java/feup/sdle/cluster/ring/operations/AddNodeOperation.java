package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.crdts.VersionStamp;
import feup.sdle.message.HashRingOperationMessage;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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
    public List<BigInteger> getHashes() {
        return this.hashesToAdd;
    }

    @Override
    public NodeIdentifier getNodeIdentifier() {
        return this.nodeIdentifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hashesToAdd, this.nodeIdentifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddNodeOperation otherOperation = (AddNodeOperation) o;
        return this.hashesToAdd.equals(otherOperation.hashesToAdd) && this.nodeIdentifier.equals(otherOperation.nodeIdentifier);
    }

    @Override
    public HashRingOperationType getOperationType() {
        return HashRingOperationType.ADD;
    }

    @Override
    public void reproduce() {

    }

    @Override
    public HashRingLogOperation reverse() {
        return new RemoveNodeOperation(this.hashesToAdd, this.nodeIdentifier);
    }
}
