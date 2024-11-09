package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.message.Hashcheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Arrays;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(GossipService.class);
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
                byte[] reply = this.receiveGossipSocket.recv(0);
                try {
                    // 1. Add processing work for message

                    // 2. Delegate work to the node

                    System.out.println(Hashcheck.HashCheck.parseFrom(reply).toString());
                } catch (InvalidProtocolBufferException e) {
                    LOGGER.error(e.toString());
                }
            }
        });
    }

    /**
     * From the preference list based on the hash ring, we send to
     * fanout number of nodes the message we want to gossip
     */
    public void publish(int fanout, byte[] msg) {
        List<NodeIdentifier> preferenceList = this.node.getPreferenceList();

        System.out.println("Preference list: " + preferenceList.toString());

        // List out of bounds guard
        if(preferenceList.size() < fanout) {
            fanout = preferenceList.size();
        }

        for(int n = 0; n < fanout; n++) {
            NodeIdentifier otherPeer = preferenceList.get(n);

            ZMQ.Socket otherPeerSocket = otherPeer.getSocket(this.node.getZmqContext());
            otherPeerSocket.send(msg);
        }
    }
}