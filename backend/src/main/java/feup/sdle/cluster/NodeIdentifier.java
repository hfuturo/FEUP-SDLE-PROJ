package feup.sdle.cluster;

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

    public int getId() {
        return this.id;
    }
}