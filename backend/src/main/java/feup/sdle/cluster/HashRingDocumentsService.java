package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.message.DocumentRequestProto;
import feup.sdle.message.Message;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZContext;

public class HashRingDocumentsService extends MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashRingDocumentsService.class);
    private Node node;

    HashRingDocumentsService(Node node) {
        super();
        this.node = node;

        Thread.ofVirtual().start(this::processDocumentsRequest);
    }

    public Boolean requestDocumentsFromRange(NodeIdentifier source, HashRange range) {
        DocumentRequestProto.HashRange hashRangeMessage = DocumentRequestProto.HashRange.newBuilder()
                .setStart(ByteString.copyFrom(range.start().toByteArray()))
                .setEnd(ByteString.copyFrom(range.end().toByteArray()))
                .build();
        DocumentRequestProto.DocumentRangeRequest message = DocumentRequestProto.DocumentRangeRequest.newBuilder()
                .setRange(hashRangeMessage)
                .build();

        return source.getSocket(this.node.getZmqContext()).send(message.toByteArray());

    }

    public void processDocumentsRequest() {
        while(true) {
            try {
                Message.MessageFormat msgFormat = this.messages.take();

                if (msgFormat.getMessageType() != Message.MessageFormat.MessageType.DOCUMENT_REQUEST) {
                    LOGGER.error(Color.red("Unexpected message type: " + msgFormat.getMessageType() + " in Documents Service"));
                }

                DocumentRequestProto.DocumentRangeRequest message = DocumentRequestProto.DocumentRangeRequest.parseFrom(msgFormat.getMessage());

                DocumentRequestProto.HashRange range = message.getRange();

                

            } catch (Exception e) {
                LOGGER.error(Color.red(e.toString()));
            }
        }
    }

}
