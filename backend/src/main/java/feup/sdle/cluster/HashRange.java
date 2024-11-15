package feup.sdle.cluster;

import java.math.BigInteger;

public record HashRange(BigInteger start, BigInteger end) {

    public boolean inRange(BigInteger hash) {
        // Check for normal range (no wrap-around)
        if (start.compareTo(end) <= 0) {
            return hash.compareTo(start) >= 0 && hash.compareTo(end) <= 0;
        }
        // Check for wrap-around range
        else {
            return hash.compareTo(start) >= 0 || hash.compareTo(end) <= 0;
        }
    }
}

