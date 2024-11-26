package feup.sdle.crdts;

import com.google.protobuf.ByteString;
import feup.sdle.message.DottedValueProto;
import feup.sdle.message.ShoppingListItemProto;

public record DottedValue<I, E, V>(I identifier, E event, V value) {
    public DottedValueProto.DottedValue toMessageDottedValue() {
        var builder = DottedValueProto.DottedValue.newBuilder()
                .setIdentifier((Integer) identifier)
                .setEvent((Integer) event);

        if (value instanceof Integer) {
            builder.setValueInt((Integer) value);
        }
        else if (value instanceof String) {
            builder.setValueStr((String) value);
        }
        else {
            builder.setValueStrBytes((ByteString) value);
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
