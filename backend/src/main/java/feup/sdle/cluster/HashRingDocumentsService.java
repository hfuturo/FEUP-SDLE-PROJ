package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import feup.sdle.Document;
import feup.sdle.message.DocumentProto;
import feup.sdle.Document;
import feup.sdle.ShoppingList;
import feup.sdle.message.DocumentProto;
import feup.sdle.message.DocumentRequestProto;
import feup.sdle.message.Message;
import feup.sdle.message.NodeIdentifierMessage;
import feup.sdle.utils.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class HashRingDocumentsService extends MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashRingDocumentsService.class);
    private static final int CHUNK_SIZE = 600;
    private final Node node;
    private HashMap<Message.MessageFormat.MessageType, Consumer<Message.MessageFormat>> messageActions;

    HashRingDocumentsService(Node node) {
        super();
        this.node = node;

        this.messageActions = new HashMap<>();
        this.buildMessageActions();

        this.registerDocumentServiceListener();
    }

    private void buildMessageActions() {
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_REQUEST, this::processDocumentsRequest);
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION, this::processDocumentReplication);
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_LIST, this::processDocumentList);
    }

    private void registerDocumentServiceListener() {
        Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    Message.MessageFormat msgFormat = this.messages.take();

                    Consumer<Message.MessageFormat> consumer = this.messageActions.get(msgFormat.getMessageType());
                    if (consumer != null) {
                        Thread.ofVirtual().start(() -> consumer.accept(msgFormat));
                    }
                } catch (Exception e) {
                    LOGGER.error(Color.red(e.getMessage()));
                }
            }
        });
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

    public void processDocumentsRequest(Message.MessageFormat msgFormat) {
        try {
            if (msgFormat.getMessageType() != Message.MessageFormat.MessageType.DOCUMENT_REQUEST) {
                LOGGER.error(Color.red("Unexpected message type: " + msgFormat.getMessageType() + " in Documents Service"));
            }

            DocumentRequestProto.DocumentRangeRequest message = DocumentRequestProto.DocumentRangeRequest.parseFrom(msgFormat.getMessage());
                DocumentRequestProto.DocumentRangeRequest requestMessage = DocumentRequestProto.DocumentRangeRequest.parseFrom(msgFormat.getMessage());

                DocumentRequestProto.HashRange rangeMessage = requestMessage.getRange();

                HashRange range = new HashRange(rangeMessage);

                List<Document> documentList = this.node.getDocumentsFromRange(range);

                this.sendDocumentList(documentList, NodeIdentifier.fromMessageNodeIdentifier(msgFormat.getNodeIdentifier()));

            } catch (Exception e) {
                LOGGER.error(Color.red(e.toString()));
            }
        }

    private void sendDocumentList(List<Document> documentList, NodeIdentifier nodeIdentifier) {
        int totalSize = documentList.size();
        for (int i = 0; i <= totalSize; i += CHUNK_SIZE) {
            List<Document> chunk = documentList.subList(i, CHUNK_SIZE);
            DocumentProto.DocumentList documentListMessage = DocumentProto.DocumentList.newBuilder()
                    .addAllDocuments(chunk.stream().map(Document::toMessage).toList())
                    .build();
            nodeIdentifier.getSocket(this.node.getZmqContext()).send(documentListMessage.toByteArray());
        }
    }

    private void processDocumentList(Message.MessageFormat msgFormat) {
            try {

                if (msgFormat.getMessageType() != Message.MessageFormat.MessageType.DOCUMENT_LIST) {
                    LOGGER.error(Color.red("Unexpected message type: " + msgFormat.getMessageType() + " in Documents Service"));
                }

                DocumentProto.DocumentList documentListMessage = DocumentProto.DocumentList.parseFrom(msgFormat.getMessage());

                for (var documentMessage : documentListMessage.getDocumentsList()) {
                    this.node.storeDocument(documentMessage.getKey(), Document.fromMessage(documentMessage));
                }

            } catch (Exception e) {
                LOGGER.error(Color.red(e.toString()));
            }
    }

    private void processDocumentReplication(Message.MessageFormat msgFormat) {
        try {
            if (msgFormat.getMessageType() != Message.MessageFormat.MessageType.DOCUMENT_REPLICATION) {
                LOGGER.error(Color.red("Unexpected message type: " + msgFormat.getMessageType() + " in Documents Service REPLICATION"));
                return;
            }

            System.out.println(Color.green("RECEIVED DOCUMENT REPLICATION IN process"));
            DocumentProto.Document document = DocumentProto.Document.parseFrom(msgFormat.getMessage());
            this.node.storeDocument(document.getKey(), Document.fromMessage(document));
        } catch (Exception e) {
            LOGGER.error(Color.red(e.getMessage()));
        }
    }

    public void sendDocumentReplication(String key, Document document, List<NodeIdentifier> nodesToReplicate) {
        var messageTemplate = Message.MessageFormat.newBuilder()
                        .setMessageType(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION)
                        .setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier());

        var documentMessage = document.toMessage();
        var message = messageTemplate.setMessage(documentMessage.toByteString()).build().toByteArray();

        this.node.getTransmitter().sendMultipleWithOfflineDetection(message, nodesToReplicate, Node.REPLICATION_FACTOR);
    }
}
