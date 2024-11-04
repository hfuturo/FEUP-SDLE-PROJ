package feup.sdle.cluster.ring.operations;

import feup.sdle.cluster.NodeIdentifier;

import java.util.List;

public class RemoveNodeOperation implements HashRingLogOperation {

    private final List<NodeIdentifier> nodesToRemove;

    public RemoveNodeOperation(List<NodeIdentifier> nodesToRemove) {
        this.nodesToRemove = nodesToRemove;
    }

    @Override
    public void reproduce() {

    }
}
