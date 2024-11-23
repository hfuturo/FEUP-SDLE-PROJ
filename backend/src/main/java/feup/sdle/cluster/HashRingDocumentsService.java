package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import feup.sdle.ShoppingList;
import feup.sdle.message.DocumentProto;
import feup.sdle.message.DocumentRequestProto;
import feup.sdle.message.Message;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZContext;

import java.util.List;

public class HashRingDocumentsService extends MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashRingDocumentsService.class);
    private final Node node;

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

    public void processDocumentReplication(ZContext senderContext, ShoppingList list, List<NodeIdentifier> nodesToReplicate) {
        nodesToReplicate.forEach(node -> node.getSocket(senderContext).send(
            DocumentProto.Document.newBuilder()
                    .setShoppingList(list.toMessageShoppingList())
                    .setReplicate(false)
                    .build()
                    .toByteArray()
        ));
    }

}
