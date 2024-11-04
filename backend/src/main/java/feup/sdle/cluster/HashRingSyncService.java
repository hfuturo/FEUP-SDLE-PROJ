package feup.sdle.cluster;

/**
 * This is the service responsible to sync the hash ring state by publishing the state
 * in the specified gossip service
 */
public class HashRingSyncService {
    private GossipService gossipService;
    private int timeoutMs;
    private int fanout;

    public HashRingSyncService(GossipService gossipService, int timeoutMs, int fanout) {
        this.gossipService = gossipService;
        this.timeoutMs = timeoutMs;
        this.fanout = fanout;

        this.initSyncService();
    }

    private void initSyncService() {
        Thread.ofVirtual().start(() -> {
            while(true) {
                try {
                    Thread.sleep(this.timeoutMs);
                    System.out.println("Running");

                    this.gossipService.publish(this.fanout, "hash");
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

}
