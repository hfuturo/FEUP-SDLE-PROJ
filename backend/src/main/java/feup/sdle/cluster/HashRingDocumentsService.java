package feup.sdle.cluster;

import com.google.protobuf.ByteString;

public class HashRingDocumentsService {
    public Boolean requestDocumentsFromRange(NodeIdentifier source, HashRange range) {
        /*DocumentRequestProto.HashRange hashRangeMessage = DocumentRequestProto.HashRange.newBuilder()
                .setStart(ByteString.copyFrom(range.start().toByteArray()))
                .setEnd(ByteString.copyFrom(range.end().toByteArray()))
                .build();
        DocumentRequestProto.DocumentRangeRequest message = DocumentRequestProto.DocumentRangeRequest.newBuilder()
                .setRange(hashRangeMessage)
                .build();*/



        return false;
    }
}
