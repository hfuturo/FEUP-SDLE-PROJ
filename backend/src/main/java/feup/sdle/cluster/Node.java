package feup.sdle.cluster;

import feup.sdle.Document;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private final NodeIdentifier identifier;
    private final List<NodeIdentifier> peerList;
    private HashRing ring;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets

    private Map<Integer, Document> documentMap; // TODO add mechanism to sync between RAM memory and file storage

    Node(int id, String hostname, int port, HashRing ring) {
        this.identifier = new NodeIdentifier(id, hostname, port);
        this.ring = ring;
        this.documentMap = new HashMap<>();
        this.peerList = new ArrayList<>();
    }
}
