package feup.sdle.cluster;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Optional;

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
}