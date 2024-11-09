package feup.sdle.cluster;

import feup.sdle.message.Message;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class NodeIdentifier {
    private int id;
    private String hostname;
    private int port;
    private boolean alive;
    private ZMQ.Socket socket;

    public NodeIdentifier(int id, String hostname, int port, boolean alive) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
        this.alive = alive;
    }

    public NodeIdentifier(int id, String hostname, int port, boolean alive, ZMQ.Socket socket) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
        this.alive = alive;
        this.socket = socket;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getPort() {
        return this.port;
    }

    public ZMQ.Socket getSocket(ZContext context) {
        if(this.socket == null) {
            ZMQ.Socket socket = context.createSocket(ZMQ.REQ);
            socket.connect("tcp://" + this.hostname + ":" + this.port);
            return socket;
        }

        return this.socket;
    }

    public int getId() {
        return this.id;
    }

    public String getHostName() {
        return this.hostname;
    }

    public static NodeIdentifier fromMessageNodeIdentifier(Message.NodeIdentifier msgNodeIdentifier) {
        return new NodeIdentifier(
                msgNodeIdentifier.getId(),
                msgNodeIdentifier.getHostname(),
                msgNodeIdentifier.getPort(),
                true
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof NodeIdentifier nodeIdentifier)) return false;

        return this.id == nodeIdentifier.getId() &&
                this.hostname.equals(nodeIdentifier.getHostName()) &&
                this.port == nodeIdentifier.getPort();
    }

    @Override
    public String toString() {
        return "{ id: " + this.getId() + " | " +
                "host: " + this.getHostName() + " | " +
                "port: " + this.getPort() + " }";
    }
}