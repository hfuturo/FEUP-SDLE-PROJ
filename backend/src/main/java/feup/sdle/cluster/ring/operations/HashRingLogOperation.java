package feup.sdle.cluster.ring.operations;

import com.google.protobuf.ByteString;
import feup.sdle.ProtobufSerializable;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.message.HashRingOperationMessage;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public interface HashRingLogOperation extends ProtobufSerializable<HashRingOperationMessage.HashRingOperation> {
    void reproduce();
    HashRingLogOperation reverse();
    HashRingOperationType getOperationType();
    List<BigInteger> getHashes();
    NodeIdentifier getNodeIdentifier();

    static HashRingLogOperation fromHashRingOperationMessage(HashRingOperationMessage.HashRingOperation operation, NodeIdentifier senderNode) {
        List<BigInteger> hashes = operation.getHashesList().stream().map(el -> new BigInteger(el.toByteArray())).toList();

        switch(operation.getOperationType()) {
            case ADD -> {
                return new AddNodeOperation(hashes, senderNode);
            }
            case REMOVE -> {
                return new RemoveNodeOperation(hashes, senderNode);
            }
        }

        return null;
    }



    @Override
    default HashRingOperationMessage.HashRingOperation toMessage() {
        return HashRingOperationMessage.HashRingOperation.newBuilder()
                .setOperationTypeValue(this.getOperationType().ordinal())
                .addAllHashes(this.getHashes().stream().map(el -> ByteString.copyFrom(el.toByteArray())).toList())
                .build();
    }

    @Override
    default ByteString toProtoBuf() {
        return null;
    }
}
