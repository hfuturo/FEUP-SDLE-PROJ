package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.message.Message;
import feup.sdle.message.Message.MessageFormat.MessageType;
import feup.sdle.utils.Color;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NodeReceiver {
    private Node node;
    private ZMQ.Socket socket;
    private GossipService gossipService;
    private HashRingDocumentsService hashRingDocumentsService;
    private HashMap<MessageType, MessagingService> msgTypeToServices;

    public NodeReceiver(Node node) {
        this.node = node;

        this.gossipService = node.getGossipService();
        this.hashRingDocumentsService = node.getHashRingDocumentsService();

        this.socket = node.getZmqContext().createSocket(SocketType.REP);
        this.socket.bind(String.format("tcp://:%d", node.getPort()));

        this.msgTypeToServices = new HashMap<>();
        this.populateServices();

        Thread.ofVirtual().start(this::startReceiver);
    }

    public void populateServices() {
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.HASHRING_LOG_HASH_CHECK,
                this.gossipService);
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.HASH_RING_LOG, this.gossipService);
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.HASHRING_JOIN, this.gossipService);
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.HASHRING_GET, this.gossipService);
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.DOCUMENT_REQUEST, this.hashRingDocumentsService);
        this.msgTypeToServices.put(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION, this.hashRingDocumentsService);
        this.msgTypeToServices.put(MessageType.ACK, this.gossipService);
    }

    public void startReceiver() {
        while (true) {
            System.out.println("Receiver started. Listening for messages!");
            byte[] reply = this.socket.recv(0);
            try {
                Message.MessageFormat msgFormat = Message.MessageFormat.parseFrom(reply);
                if (msgFormat.getMessageType() == MessageType.ACK) {
                    System.out.println(Color.yellow("RECEIVED ACK"));
                } else {
                    this.msgTypeToServices.get(msgFormat.getMessageType()).addToQueue(msgFormat);

                    this.socket.send(Message.MessageFormat.newBuilder().setMessageType(MessageType.ACK).setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier()).build().toByteArray());
                }
            } catch (InvalidProtocolBufferException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
