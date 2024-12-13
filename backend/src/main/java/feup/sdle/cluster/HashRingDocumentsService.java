package feup.sdle.cluster;

import com.google.protobuf.ByteString;
import feup.sdle.Document;
import feup.sdle.message.DocumentProto;
import feup.sdle.message.DocumentRequestProto;
import feup.sdle.message.Message;
import feup.sdle.utils.Color;
import feup.sdle.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
        Thread.ofVirtual().start(this::sendTemporaryDocumentsToOwners);
    }

    private void buildMessageActions() {
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_REQUEST, this::processDocumentsRequest);
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION, this::processDocumentReplicationRequest);
        this.messageActions.put(Message.MessageFormat.MessageType.DOCUMENT_LIST, this::processDocumentList);
        this.messageActions.put(Message.MessageFormat.MessageType.TEMPORARY_DOCUMENT, this::processTemporaryDocument);
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

    private void processDocumentReplicationRequest(Message.MessageFormat msgFormat) {
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

    public void sendDocumentReplication(Document document, List<NodeIdentifier> nodesToReplicate) {
        var messageTemplate = Message.MessageFormat.newBuilder()
                        .setMessageType(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION)
                        .setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier());

        var documentMessage = document.toMessage();
        var message = messageTemplate.setMessage(documentMessage.toByteString()).build().toByteArray();

        StringBuilder replication = new StringBuilder("Sending replication to:");

        for (int i = 0; i < Node.REPLICATION_FACTOR; i++) {
            replication.append(" ").append(nodesToReplicate.get(i).getPort());
        }

        System.out.println(Color.green(replication.toString()));

        Pair<List<NodeIdentifier>, List<NodeIdentifier>> nodes = this.node.getTransmitter().sendMultipleWithOfflineDetectionAndRet(message, nodesToReplicate, Node.REPLICATION_FACTOR);
        List<NodeIdentifier> offlineNodes = nodes.first();
        List<NodeIdentifier> substituteNodes = nodes.second();

        Iterator<NodeIdentifier> offlineIterator = offlineNodes.iterator();
        Iterator<NodeIdentifier> substituteIterator = substituteNodes.iterator();

        System.out.println(Color.yellow("OFFLINE NODES: " + offlineNodes));
        System.out.println(Color.yellow("SUBSTITUTE NODES: " + substituteNodes));

        System.out.println("CURRENT NODE");

        while (offlineIterator.hasNext() && substituteIterator.hasNext()) {
            var temporaryDocumentMessage = Message.MessageFormat.newBuilder()
                    .setMessageType(Message.MessageFormat.MessageType.TEMPORARY_DOCUMENT)
                    .setMessage(
                            DocumentProto.TemporaryDocument.newBuilder()
                                    .setDocument(document.toMessage())
                                    .setOriginalNode(offlineIterator.next().toMessageNodeIdentifier())
                                    .build()
                                    .toByteString()
                    )
                    .setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier())
                    .build();

            NodeIdentifier substituteNode = substituteIterator.next();
            substituteNode.getSocket(this.node.getZmqContext()).send(temporaryDocumentMessage.toByteArray());
        }

    }

    public void processTemporaryDocument(Message.MessageFormat msgFormat) {
        if (msgFormat.getMessageType() != Message.MessageFormat.MessageType.TEMPORARY_DOCUMENT) {
            LOGGER.error(Color.red("Unexpected message type: " + msgFormat.getMessageType() + " in processTEmporaryDocument"));
            return;
        }

        try {
            DocumentProto.TemporaryDocument message = DocumentProto.TemporaryDocument.parseFrom(msgFormat.getMessage());
            Document document = Document.fromMessage(message.getDocument());
            NodeIdentifier offlineNode = NodeIdentifier.fromMessageNodeIdentifier(message.getOriginalNode());
            System.out.println(Color.green("Received temporary document that was supposed to be stored in: " + offlineNode.getPort()));

            this.node.addDocumentsToOfflineNodes(document, offlineNode);
        } catch (Exception e) {
            LOGGER.error(Color.red(e.getMessage()));
        }
    }

    public void sendTemporaryDocumentsToOwners() {
        while (true) {
            try {

                ConcurrentHashMap<NodeIdentifier, List<Document>> map = this.node.getOfflineNodeDocuments();
                if (map == null) {
                    Thread.sleep(3000);
                    continue;
                }

                synchronized (this.node.getOfflineNodeDocuments()) {
                    Iterator<Map.Entry<NodeIdentifier, List<Document>>> iterator = map.entrySet().iterator();

                    while (iterator.hasNext()) {
                        Map.Entry<NodeIdentifier, List<Document>> entry = iterator.next();
                        NodeIdentifier owner = entry.getKey();
                        List<Document> documents = entry.getValue();

                        if (documents != null) {
                            for (Iterator<Document> documentIterator = documents.listIterator(); documentIterator.hasNext(); ) {
                                Document document = documentIterator.next();

                                var messageTemplate = Message.MessageFormat.newBuilder()
                                        .setMessageType(Message.MessageFormat.MessageType.DOCUMENT_REPLICATION)
                                        .setNodeIdentifier(this.node.getNodeIdentifier().toMessageNodeIdentifier());

                                var documentMessage = document.toMessage();
                                var message = messageTemplate.setMessage(documentMessage.toByteString()).build().toByteArray();

                                boolean received = this.node.getTransmitter().sendMessageWithOfflineDetection(message, owner);

                                if (received) {
                                    System.out.println(Color.green("Sent temporary document to owner (" + owner.getPort() + ")"));
                                    documentIterator.remove();
                                }
                            }

                            if (documents.isEmpty()) {
                                iterator.remove();
                            }
                        }
                    }
                }

                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
