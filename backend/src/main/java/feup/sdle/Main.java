package feup.sdle;

import feup.sdle.cluster.HashRing;
import feup.sdle.cluster.Node;
import feup.sdle.crypto.MD5HashAlgorithm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Node node = new Node(2, "localhost", 5000,
                             new HashRing(new MD5HashAlgorithm())
        );

        Node node2 = new Node(1, "localhost", 5001, new HashRing(new MD5HashAlgorithm()));

        while(true) {}
    }
}
