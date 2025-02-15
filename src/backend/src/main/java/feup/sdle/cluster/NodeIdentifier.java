package feup.sdle.cluster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.message.Message;
import feup.sdle.message.NodeIdentifierMessage;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Objects;

public class NodeIdentifier {
    private int id;
    private String hostname;
    private int port;
    @JsonProperty("httpPort")
    private int httpPort;
    private boolean alive;
    private ZMQ.Socket socket;

    public NodeIdentifier(int id) {
        this.id = id;
    }

    /**
     * This constructor is to be used to create documents to store inside our file storage, so we do not need
     * some node identifier information such as hostname or port
     */
    @JsonCreator
    public static NodeIdentifier fromId(@JsonProperty("localIdentifier") int id) {
        return new NodeIdentifier(id);
    }

    public NodeIdentifier(int id, String hostname, int port, boolean alive, int httpPort) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
        this.alive = alive;
        this.httpPort = httpPort;
    }

    public NodeIdentifier(int id, String hostname, int port, boolean alive, ZMQ.Socket socket, int httpPort) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
        this.alive = alive;
        this.socket = socket;
        this.httpPort = httpPort;
    }

    public int getHttpPort() {
        return this.httpPort;
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

    public static NodeIdentifier fromMessageNodeIdentifier(NodeIdentifierMessage.NodeIdentifier msgNodeIdentifier) {
        return new NodeIdentifier(
                msgNodeIdentifier.getId(),
                msgNodeIdentifier.getHostname(),
                msgNodeIdentifier.getPort(),
                true,
                msgNodeIdentifier.getHttpPort()
        );
    }

    public NodeIdentifierMessage.NodeIdentifier toMessageNodeIdentifier() {
        return NodeIdentifierMessage.NodeIdentifier.newBuilder()
                .setId(this.id)
                .setHostname(this.hostname)
                .setPort(this.port)
                .setHttpPort(this.httpPort)
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.hostname, this.port);
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