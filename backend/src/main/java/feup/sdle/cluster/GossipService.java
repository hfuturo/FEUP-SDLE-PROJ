package feup.sdle.cluster;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

/**
 * This takes care of everything the node needs to have in order to be able to
 * setup the gossip protocol between the node and the cluster it is in
 *
 * The goal is to use the PUB-SUB pattern, where each node has a socket where they publish messages
 * and then the other nodes receive those messages
 *
 * Steps:
 *
 * 1. Create PUB socket
 */
public class GossipService {
    private Node node;
    private ZContext zContext;
    private ZMQ.Socket receiveGossipSocket;
    public GossipService(Node node, ZContext zContext) {
        this.node = node;
        this.zContext = zContext;

        this.registerGossipListener();
    }

    public Node getNode() {
        return this.node;
    }

    /**
     * When we receive a message on the socket destined to serve gossip
     * we need to check and process our message and then delegate it into our node
     * for further action
     */
    private void registerGossipListener() {
        this.receiveGossipSocket = zContext.createSocket(SocketType.REP);
        this.receiveGossipSocket.bind(String.format("tcp://*:%d", this.node.getPort()));

        Thread.ofVirtual().start(() -> {
            System.out.println("Listening for a message");
            while(true) {
                zmq.Msg reply = this.receiveGossipSocket.recvMsg(0);

                System.out.println(reply.toString());
                // 1. Add processing work for message

                // 2. Delegate work to the node

            }
        });
    }

    /**
     * From the preference list based on the hash ring, we send to
     * fanout number of nodes the message we want to gossip
     */
    public void publish(int fanout, String message) {
        List<NodeIdentifier> preferenceList = this.node.getPreferenceList();

        // List out of bounds guard
        if(preferenceList.size() < fanout) {
            fanout = preferenceList.size();
        }

        for(int n = 0; n < fanout; n++) {
            NodeIdentifier otherPeer = this.node.getPreferenceList().get(n);

            ZMQ.Socket otherPeerSocket = otherPeer.getSocket(this.node.getZmqContext());
            otherPeerSocket.send("hello");
        }
    }
}