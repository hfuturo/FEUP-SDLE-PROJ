package feup.sdle;

import com.google.protobuf.ByteString;

public interface ProtobufSerializable<M> {
    public ByteString toProtoBuf();
    public M toMessage();
}
