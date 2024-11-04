package feup.sdle;

import feup.sdle.cluster.HashRing;
import feup.sdle.cluster.HashRingLog;
import feup.sdle.cluster.Node;
import feup.sdle.cluster.ring.operations.HashRingLogOperation;
import feup.sdle.crypto.MD5HashAlgorithm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Node node = new Node(2, "localhost", 5000,
                             new HashRing(new MD5HashAlgorithm(), 2)
        );

        Node node2 = new Node(1, "localhost", 5001, new HashRing(new MD5HashAlgorithm(), 1));

        HashRingLog log1 = new HashRingLog(1);
        HashRingLog log2 = new HashRingLog(2);
        HashRingLog log3 = new HashRingLog(3);

        log1.add(new HashRingLogOperation(2));
        log1.add(new HashRingLogOperation(4));
        log3.add(new HashRingLogOperation(20));

        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);

        log1.merge(log3);
        log1.merge(log2);

        log2.merge(log3);
        log2.merge(log1);

        log3.merge(log1);
        log3.merge(log2);

        log1.getOperationsStr();
        log2.getOperationsStr();
        log3.getOperationsStr();

    }
}
