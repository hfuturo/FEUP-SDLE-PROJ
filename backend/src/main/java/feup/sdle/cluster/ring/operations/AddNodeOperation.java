package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.util.List;

public class AddNodeOperation implements HashRingLogOperation {

    private final List<NodeIdentifier> nodesToAdd;

    public AddNodeOperation(List<NodeIdentifier> nodesToAdd) {
        this.nodesToAdd = nodesToAdd;
    }

    @Override
    public void reproduce() {

    }
}
