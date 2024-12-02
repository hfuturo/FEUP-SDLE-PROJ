package feup.sdle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    @Override
    DocumentProto.Document toMessage();

    static Document fromMessage(DocumentProto.Document message) {
        switch (message.getDocumentTypeCase()) {
            case SHOPPING_LIST:
                return ShoppingList.fromMessageShoppingList(message.getShoppingList());
            default:
                throw new IllegalArgumentException("Unsupported document type: " + message.getDocumentTypeCase());
        }
    }
}