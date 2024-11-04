package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This operation is meant to indicate that hashes were removed from a node
 */
public class RemoveHashFromNodeOperation implements HashRingLogOperation {
    private List<BigInteger> hashesToRemove;
    private NodeIdentifier nodeIdentifier;

    public RemoveHashFromNodeOperation(List<BigInteger> hashesToRemove, NodeIdentifier nodeIdentifier) {
        this.hashesToRemove = hashesToRemove;
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public void reproduce() {

    }
}
