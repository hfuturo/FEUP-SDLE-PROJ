package feup.sdle.cluster;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NodePeerDiscovery {
    private ZContext context;
    private ConcurrentHashMap<Integer, ZMQ.Socket> sockets;

    public NodePeerDiscovery(ZContext context) {
        this.sockets = new ConcurrentHashMap<>();
        this.context = context;
    }

    public void addPeer(Integer peerId, ZMQ.Socket socket) {
        this.sockets.put(peerId, socket);
    }

    /**
     * If the peer does not yet have a known socket, it creates one
     * Otherwise, it retrieves the already created socket
     */
    public ZMQ.Socket getPeerSocket(NodeIdentifier peer) {
        ZMQ.Socket socket = sockets.get(peer.getId());

        if(this.sockets.get(peer.getId()) == null) {
            ZMQ.Socket newSocket = peer.getSocket(this.context);
            this.sockets.put(peer.getId(), newSocket);

            return newSocket;
        }

        return socket;
    }
}
