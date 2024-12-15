package feup.sdle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.crdts.CRDTSingleMergeable;
import feup.sdle.message.DocumentProto;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShoppingList.class, name = "shoppingList")
})

public interface Document extends ProtobufSerializable {
    String getId();
    void setId(String id);
    void merge(Document document);
    void setNodeIdentifier(int nodeIdentifier);
    @Override
    DocumentProto.Document toMessage();

    static Document fromMessage(DocumentProto.Document message) {
        switch (message.getDocumentTypeCase()) {
            case SHOPPING_LIST:
                return ShoppingList.fromMessage(message);
            default:
                throw new IllegalArgumentException("Unsupported document type: " + message.getDocumentTypeCase());
        }
    }

}