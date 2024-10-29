package feup.sdle.cluster;

import feup.sdle.Document;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private final NodeIdentifier identifier;
    private final List<NodeIdentifier> peerList;
    private HashRing ring;
    private ZContext zmqContext;
    private ZMQ.Socket sendGossipSocket;
    private ZMQ.Socket socket; // TODO change this to allow multiple sockets

    private MemoryStorageProvider storage;

    Node(int id, String hostname, int port, HashRing ring) {
        this.zmqContext = new ZContext();

        this.identifier = new NodeIdentifier(id, hostname, port);
        this.ring = ring;

        this.storage = new MemoryStorageProvider<Integer, Document>(new FileStorageProvider());

        this.peerList = new ArrayList<>();

        this.sendGossipSocket = this.createSendGossipSocket();
    }

    /**
     * Creates the socket to where the other nodes will be connecting in order to receive
     * gossip epidemic updates from this socket
     */
    private ZMQ.Socket createSendGossipSocket() {
        ZMQ.Socket socket = this.zmqContext.createSocket(SocketType.PUB);
        socket.bind(String.format("udp://*:%d", this.identifier.port()));

        return socket;
    }
}
