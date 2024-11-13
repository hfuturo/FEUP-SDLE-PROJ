package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.message.Message.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BiConsumer;

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
public class GossipService extends MessagingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GossipService.class);
    private Node node;
    private ZContext zContext;
    private ZMQ.Socket receiveGossipSocket;
    private HashMap<MessageFormat.MessageType, BiConsumer<MessageFormat, NodeIdentifier>> messageActions;

    public GossipService(Node node, ZContext zContext) {
        super();
        this.node = node;
        this.zContext = zContext;

        this.messageActions = new HashMap<>();
        this.buildMessageActions();

        this.registerGossipListener();
    }

    public Node getNode() {
        return this.node;
    }

    public void buildMessageActions() {
        this.messageActions.put(MessageFormat.MessageType.HASHRING_LOG_HASH_CHECK, this.node::processRingLogHashCheck);
        this.messageActions.put(MessageFormat.MessageType.HASH_RING_LOG, this.node::processHashRingSyncMessage);
        this.messageActions.put(MessageFormat.MessageType.HASHRING_JOIN, this.node::processAddNodeRequest);
        this.messageActions.put(MessageFormat.MessageType.HASHRING_GET, this.node::processHashRingView);
    }

    /**
     * When we receive a message on the socket destined to serve gossip
     * we need to check and process our message and then delegate it into our node
     * for further action
     */
    private void registerGossipListener() {
        Thread.ofVirtual().start(() -> {
            System.out.println("Checking for message existence");
            while(true) {
                try {
                    MessageFormat msgFormat = this.messages.take();
                    System.out.println("MsgFormat: " + msgFormat.getMessageType());

                    BiConsumer<MessageFormat, NodeIdentifier> biconsumer = this.messageActions.get(msgFormat.getMessageType());
                    if(biconsumer != null) {
                        Thread.ofVirtual().start(() -> biconsumer.accept(msgFormat, NodeIdentifier.fromMessageNodeIdentifier(msgFormat.getNodeIdentifier())));
                    }
                } catch (InterruptedException e) {
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