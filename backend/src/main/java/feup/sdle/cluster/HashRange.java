package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import feup.sdle.message.DocumentRequestProto;

import java.math.BigInteger;

public record HashRange(BigInteger start, BigInteger end) {

    public HashRange(DocumentRequestProto.HashRange range) {
        this(new BigInteger(range.getStart().toByteArray()), new BigInteger(range.getEnd().toByteArray()));
    }

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

