package feup.sdle.cluster;

import feup.sdle.message.Hashcheck;
import feup.sdle.message.Hashcheck.HashCheck;

/**
 * This is the service responsible to sync the hash ring state by publishing the state
 * in the specified gossip service
 */
public class HashRingSyncService {
    private GossipService gossipService;
    private int timeoutMs;
    private int fanout;
    private HashRing hashRing;

    public HashRingSyncService(HashRing hashRing, GossipService gossipService, int timeoutMs, int fanout) {
        this.gossipService = gossipService;
        this.timeoutMs = timeoutMs;
        this.fanout = fanout;
        this.hashRing = hashRing;

        this.initSyncService();
    }

    private void initSyncService() {
        Thread.ofVirtual().start(() -> {
            while(true) {
                try {
                    Thread.sleep(this.timeoutMs);
                    HashCheck hashCheck = HashCheck.newBuilder()
                            .setHash(String.valueOf(this.hashRing.getHashRingLog().hashCode()))
                            .setType(String.valueOf(HashCheck.ContextType.HASHRINGLOG_VALUE))
                            .build();

                    this.gossipService.publish(this.fanout, hashCheck.toByteArray());
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

}
