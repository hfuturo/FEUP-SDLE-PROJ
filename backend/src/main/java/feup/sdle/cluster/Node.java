package feup.sdle.cluster;

import feup.sdle.Document;
import feup.sdle.storage.FileStorageProvider;
import feup.sdle.storage.MemoryStorageProvider;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
  private final NodeIdentifier identifier;
  private final List<NodeIdentifier> preferenceList;
  private HashRing ring;
  private ZContext zmqContext;
  private ZMQ.Socket socket; // TODO change this to allow multiple sockets
  private GossipService gossipService;
  private HashRingSyncService hashRingSyncService;
  private MemoryStorageProvider storage;

  /**
   * The HashRing can already be populated, which is useful for start bootstraping
   * purposes as well as testing
   */
  public Node(int id, String hostname, int port, HashRing ring) {
    this.zmqContext = new ZContext();

    this.identifier = new NodeIdentifier(id, hostname, port, true);
    this.ring = ring;
    this.storage = new MemoryStorageProvider<Integer, Document>(new FileStorageProvider());

    this.preferenceList = new ArrayList<>();

    this.gossipService = new GossipService(this, this.zmqContext);
    this.hashRingSyncService = new HashRingSyncService(this.gossipService, 2000, 3);
  }

  public int getPort() {
    return this.identifier.getPort();
  }

  public ZContext getZmqContext() {
      return this.zmqContext;
  }

  public List<NodeIdentifier> getPreferenceList() {
    return this.preferenceList;
  }
}
