package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;
import feup.sdle.message.DottedValueProto;
import feup.sdle.message.ShoppingListItemProto;

public record DottedValue<I, E, V>(
        @JsonProperty("identifier") I identifier,
        @JsonProperty("event") E event,
        @JsonProperty("value") V value
) {


    public DottedValueProto.DottedValue toMessageDottedValue() {
        var builder = DottedValueProto.DottedValue.newBuilder()
                .setIdentifier((Integer) identifier)
                .setEvent((Integer) event);

        switch (value) {
            case Integer i -> builder.setValueInt(i);
            case String s -> builder.setValueStr(s);
            case ShoppingListItem shoppingListItem ->
                    builder.setValueStrBytes(shoppingListItem.toMessageShoppingListItem().toByteString());
            case null, default -> builder.setValueStrBytes(((ShoppingListItem) value).toMessageShoppingListItem().toByteString());
        }

        return builder.build();
    }


    public static DottedValue<Integer, Integer, ShoppingListItem> fromMessageDottedValueSLI(DottedValueProto.DottedValue msgDottedValue) {
        try {
            return new DottedValue<>(
                    msgDottedValue.getIdentifier(),
                    msgDottedValue.getEvent(),
                    ShoppingListItem.fromMessageShoppingListItem(
                            msgDottedValue.getIdentifier(),
                            ShoppingListItemProto.ShoppingListItem.parseFrom(msgDottedValue.getValueStrBytes()
                        ))
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static DottedValue<Integer, Integer, Integer> fromMessageDottedValueInt(DottedValueProto.DottedValue msgDottedValue) {
        return new DottedValue<>(msgDottedValue.getIdentifier(), msgDottedValue.getEvent(), msgDottedValue.getValueInt());
    }

    public static DottedValue<Integer, Integer, String> fromMessageDottedValueStr(DottedValueProto.DottedValue msgDottedValue) {
        return new DottedValue<>(msgDottedValue.getIdentifier(), msgDottedValue.getEvent(), msgDottedValue.getValueStr());
    }
}
