package feup.sdle.crdts;

import com.google.protobuf.ByteString;
import feup.sdle.ProtobufSerializable;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.message.HashRingOperationMessage;

import java.util.Objects;

public class HashRingLongTimestamp<V extends ProtobufSerializable<HashRingOperationMessage.HashRingOperation>> implements Comparable<HashRingLongTimestamp<V>>, ProtobufSerializable<HashRingOperationMessage.HashRingLogTimestamp> {
    private Integer sequence;
    private VersionStamp versionStamp;
    private NodeIdentifier identifier;
    private V value;

    public HashRingLongTimestamp(NodeIdentifier identifier, int dot, V value, Integer sequence) {
        this.versionStamp = new VersionStamp(identifier.getId(), dot);
        this.identifier = identifier;
        this.value = value;
        this.sequence = sequence;
    }

    public Integer getIdentifier() {
        return this.versionStamp.getIdentifier();
    }

    public VersionStamp getVersionStamp() {
        return this.versionStamp;
    }

    public int getSequenceNumber() {
        return this.sequence;
    }

    public void setSequenceNumber(int value) {
        this.sequence = value;
    }

    public Integer getDot() {
        return this.versionStamp.getDot();
    }

    public V getValue() {
        return this.value;
    }

    @Override
    public int compareTo(HashRingLongTimestamp other) {
        if(this.sequence.equals(other.sequence)) {
            return this.versionStamp.compareTo(other.versionStamp);
        }

        return this.sequence.compareTo(other.sequence);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof HashRingLongTimestamp timestamp)) return false;

        return this.versionStamp.equals(timestamp.versionStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.versionStamp);
    }

    @Override
    public HashRingOperationMessage.HashRingLogTimestamp toMessage() {
        HashRingOperationMessage.HashRingLogTimestamp.Builder builder = HashRingOperationMessage.HashRingLogTimestamp.newBuilder();

        builder.setDot(this.versionStamp.getDot());
        builder.setSequence(this.sequence);
        builder.setIdentifier(this.identifier.toMessageNodeIdentifier());

        HashRingOperationMessage.HashRingOperation operation = this.value.toMessage();
        builder.setOperation(operation);

        return builder.build();
    }

    @Override
    public ByteString toProtoBuf() {
        return null;
    }
}
