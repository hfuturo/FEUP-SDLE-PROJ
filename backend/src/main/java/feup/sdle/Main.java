package feup.sdle;

import feup.sdle.cluster.HashRing;
import feup.sdle.cluster.HashRingLog;
import feup.sdle.cluster.Node;
import feup.sdle.cluster.ring.operations.AddNodeOperation;
import feup.sdle.crypto.MD5HashAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        /*HashRingLog log1 = new HashRingLog(1);
        /*Node node = new Node(2, "localhost", 5000,
                             new HashRing(new MD5HashAlgorithm(), 2)
        );

        Node node2 = new Node(1, "localhost", 5001, new HashRing(new MD5HashAlgorithm(), 1));

        HashRingLog log1 = new HashRingLog(1);
        HashRingLog log2 = new HashRingLog(2);
        HashRingLog log3 = new HashRingLog(3);

        log3.add(new AddNodeOperation(9));
        log1.add(new AddNodeOperation(2));
        log2.merge(log1);
        log1.add(new AddNodeOperation(3));
        log2.add(new AddNodeOperation(4));

        log1.merge(log3);
        log2.merge(log3);

        log1.merge(log2);
        log2.merge(log1);

        log1.add(new AddNodeOperation(2));
        log1.add(new AddNodeOperation(4));
        log3.add(new AddNodeOperation(20));

        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);
        log2.merge(log1);

        log1.merge(log3);
        log1.merge(log2);

        log2.merge(log1);
        log2.merge(log3);

        log3.merge(log1);
        log3.merge(log2);

        System.out.println("Log1: ");
        log1.getOperationsStr();

        System.out.println("Log2: ");
        log2.getOperationsStr();

        System.out.println("Log3: ");
        log3.getOperationsStr();*/

    }
}
