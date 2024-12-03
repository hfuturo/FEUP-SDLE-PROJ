package feup.sdle.cluster;

import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.message.Message;
import feup.sdle.utils.Color;
import feup.sdle.utils.Pair;
import org.zeromq.ZMQ;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is a class to wrap the message sending logic so features like offline detection do not need to be
 * copy pasted in other pieces of the code
 */
public class NodeTransmitter {
    private Node node;
    private int receiveTimeout;
    private int sendRetries;
    public NodeTransmitter(Node node, int receiveTimeout, int sendRetries) {
        this.node = node;
        this.receiveTimeout = receiveTimeout;
        this.sendRetries = sendRetries;
    }

    /**
     * This sends to nodes from a preference list and detects offline, passing through the nodes that do not
     * respond and mark them as such
     * @param preferenceList Ordered list of nodes
     * @param fanout Number of nodes to send
     */
    public void sendMultipleWithOfflineDetection(byte[] msg, List<NodeIdentifier> preferenceList, int fanout) {
        AtomicInteger offlineOffset = new AtomicInteger(0);

        for(int i = 0; i < fanout; i++) {
            AtomicReference<NodeIdentifier> peer = new AtomicReference<>(preferenceList.get(i));

            Thread.ofVirtual().start(() -> {
                boolean successfullySent = false;
                boolean ranOutOfNodesToSend = false;

                while(!successfullySent && !ranOutOfNodesToSend) {

                    boolean online = false;
                    for (int j = 0; j < this.sendRetries; j++) {
                        ZMQ.Socket peerSocket = peer.get().getSocket(this.node.getZmqContext());
                        peerSocket.setReceiveTimeOut(this.receiveTimeout);
                        peerSocket.send(msg);

                        byte[] reply = peerSocket.recv(0);
                        boolean receivedAck = false;

                        if(reply != null) {
                            Message.MessageFormat msgFormat = null;
                            try {
                                msgFormat = Message.MessageFormat.parseFrom(reply);
                            } catch (InvalidProtocolBufferException e) {
                                throw new RuntimeException(e);
                            }

                            if (msgFormat.getMessageType() == Message.MessageFormat.MessageType.ACK) {
                                successfullySent = true;
                                online = true;
                                receivedAck = true;
                            }
                        }

                        peerSocket.close();

                        if (receivedAck) break;
                    }

                    if (!online) {
                        peer.get().setAlive(false);

                        int nextIdx = offlineOffset.incrementAndGet();

                        if (nextIdx >= preferenceList.size()) {
                            ranOutOfNodesToSend = true;
                        } else {
                            peer.set(preferenceList.get(nextIdx));
                        }
                    } else {
                        peer.get().setAlive(true);
                    }
                }
            });
        }
    }

    /**
     * This sends to nodes from a preference list and detects offline, passing through the nodes that do not
     * respond and mark them as such and returns a pair with two lists, the first one containing the offline nodes
     * and the second one containing the substitute nodes
     *
     * @param msg Message to send
     * @param preferenceList Ordered list of nodes
     * @param fanout Number of nodes to send
     * @return Pair with two lists, the first one containing the offline nodes and the second one containing the substitute nodes
     */
    public Pair<List<NodeIdentifier>, List<NodeIdentifier>> sendMultipleWithOfflineDetectionAndRet(byte[] msg, List<NodeIdentifier> preferenceList, int fanout) {
        AtomicInteger offlineOffset = new AtomicInteger(0);
        Deque<NodeIdentifier> offlineNodes = new ConcurrentLinkedDeque<>();
        Deque<NodeIdentifier> substituteNodes = new ConcurrentLinkedDeque<>();
        CountDownLatch latch = new CountDownLatch(fanout);

        for(int i = 0; i < fanout; i++) {
            AtomicReference<NodeIdentifier> peer = new AtomicReference<>(preferenceList.get(i));

            Thread.ofVirtual().start(() -> {
                try {
                    boolean successfullySent = false;
                    boolean ranOutOfNodesToSend = false;

                    while (!successfullySent && !ranOutOfNodesToSend) {
                        boolean online = false;
                        for (int j = 0; j < this.sendRetries; j++) {
                            ZMQ.Socket peerSocket = peer.get().getSocket(this.node.getZmqContext());
                            peerSocket.setReceiveTimeOut(this.receiveTimeout);
                            peerSocket.send(msg);

                            byte[] reply = peerSocket.recv(0);
                            boolean receivedAck = false;

                            if (reply != null) {
                                Message.MessageFormat msgFormat = null;
                                try {
                                    msgFormat = Message.MessageFormat.parseFrom(reply);
                                } catch (InvalidProtocolBufferException e) {
                                    throw new RuntimeException(e);
                                }

                                if (msgFormat.getMessageType() == Message.MessageFormat.MessageType.ACK) {
                                    successfullySent = true;
                                    online = true;
                                    receivedAck = true;
                                }
                            }

                            peerSocket.close();

                            if (receivedAck) break;
                        }

                        if (!online) {
                            peer.get().setAlive(false);
                            offlineNodes.add(peer.get());
                            int nextIdx = offlineOffset.incrementAndGet() + fanout - 1;
                            System.out.println(Color.red("NextID: " + nextIdx));
                            if (nextIdx >= preferenceList.size()) {
                                ranOutOfNodesToSend = true;
                            } else {
                                peer.set(preferenceList.get(nextIdx));
                            }
                        } else {
                            peer.get().setAlive(true);

                            if (offlineOffset.get() > substituteNodes.size()) {
                                substituteNodes.add(peer.get());
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Color.green("" + offlineNodes.size()));
        System.out.println(Color.green("" + substituteNodes.size()));

        return new Pair<>(offlineNodes.stream().toList(), substituteNodes.stream().toList());
    }
}
