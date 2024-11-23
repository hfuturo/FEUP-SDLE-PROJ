package feup.sdle.crdts;

import com.google.protobuf.ByteString;
import feup.sdle.ShoppingList;
import feup.sdle.message.DottedValueProto;

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
}
