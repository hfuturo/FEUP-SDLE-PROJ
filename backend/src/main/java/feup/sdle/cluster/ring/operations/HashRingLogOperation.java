package feup.sdle.cluster.ring.operations;

import feup.sdle.message.HashRingOperationMessage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface HashRingLogOperation {
    public void reproduce();
    public HashRingLogOperation reverse();
    public HashRingOperationType getOperationType();
    public List<BigInteger> getHashes();
}
