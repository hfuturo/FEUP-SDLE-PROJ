package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.message.MVRegisterProto;

import java.util.HashSet;
import java.util.Set;

public class MVRegister<T> implements CRDTSingleMergeable<MVRegister<T>> {

    private Set<T> values;
    private DotContext dotContext;
    private final int localIdentifier;

    public MVRegister(Set<T> values, DotContext dotContext, int localIdentifier) {
        this.values = values;
        this.dotContext = dotContext;
        this.localIdentifier = localIdentifier;
    }

    @JsonCreator
    public static MVRegister<String> fromJson(@JsonProperty("values") Set<String> values, @JsonProperty("dotContext") DotContext dotContext, @JsonProperty("localIdentifier") int localIdentifier) {
        return new MVRegister<String>(values, dotContext, localIdentifier);
    }

    public MVRegister(int localIdentifier) {
        this.localIdentifier = localIdentifier;
        this.dotContext = new DotContext(localIdentifier);
        this.values = new HashSet<T>();
    }

    @Override
    public void merge(MVRegister<T> other) {
        if (this.dotContext.isConcurrent(other.dotContext)) {
            this.values.addAll(other.values);
        } else if (other.dotContext.isMoreRecentThan(this.dotContext)) {
            this.values.clear();
            this.values.addAll(other.values);
        }
        this.dotContext.merge(other.dotContext);
    }

    public void update(T value) {
        this.values.clear();
        this.values.add(value);
        this.dotContext.getDots().put(this.localIdentifier, this.dotContext.nextOfReplica(this.localIdentifier));
    }

    public Set<T> getValues() {
        return this.values;
    }

    public void setValues(Set<T> values) {
        this.values = values;
    }

    public DotContext getDotContext() {
        return this.dotContext;
    }

    public void setDotContext(DotContext dotContext) {
        this.dotContext = dotContext;
    }

    public MVRegisterProto.MVRegister toMVRegisterMessage() {
        MVRegisterProto.MVRegister.Builder messageBuilder = MVRegisterProto.MVRegister.newBuilder()
                .setId(this.localIdentifier)
                .setDotContext(this.dotContext.toMessageDotContext());

        MVRegisterProto.ValueType.Builder valueTypeBuilder = MVRegisterProto.ValueType.newBuilder();
        if (this.values.isEmpty()) {
            return messageBuilder.build();
        }

        if (this.values.iterator().next() instanceof Integer) {
            MVRegisterProto.IntSet.Builder intSetBuilder = MVRegisterProto.IntSet.newBuilder();
            for (T value : this.values) {
                intSetBuilder.addInts((Integer) value);
            }
            valueTypeBuilder.setIntSet(intSetBuilder.build());
        } else if (this.values.iterator().next() instanceof String) {
            MVRegisterProto.StrSet.Builder strSetBuilder = MVRegisterProto.StrSet.newBuilder();
            for (T value : values) {
                strSetBuilder.addStrings((String) value);
            }
            valueTypeBuilder.setStrSet(strSetBuilder.build());
        } else if (this.values.iterator().next() instanceof byte[]) {
            MVRegisterProto.ByteSet.Builder byteSetBuilder = MVRegisterProto.ByteSet.newBuilder();
            for (T value : values) {
                byteSetBuilder.addBytes(ByteString.copyFrom((byte[]) value));
            }
            valueTypeBuilder.setByteSet(byteSetBuilder.build());
        }

        messageBuilder.setValues(valueTypeBuilder.build());
        return messageBuilder.build();
    }

    public static MVRegister<Integer> fromMVRegisterMessageInt(MVRegisterProto.MVRegister mvRegisterMessage) {
        MVRegister<Integer> mvRegister = new MVRegister<>(mvRegisterMessage.getId());

        MVRegisterProto.ValueType valueType = mvRegisterMessage.getValues();
        if (valueType.hasIntSet()) {
            mvRegister.values.addAll(valueType.getIntSet().getIntsList());
        }

        mvRegister.dotContext = DotContext.fromMessageDotContext(mvRegisterMessage.getDotContext());
        return mvRegister;
    }

    public static MVRegister<String> fromMVRegisterMessageString(MVRegisterProto.MVRegister mvRegisterMessage) {
        MVRegister<String> mvRegister = new MVRegister<>(mvRegisterMessage.getId());

        MVRegisterProto.ValueType valueType = mvRegisterMessage.getValues();
        if (valueType.hasStrSet()) {
            mvRegister.values.addAll(valueType.getStrSet().getStringsList());
        }

        mvRegister.dotContext = DotContext.fromMessageDotContext(mvRegisterMessage.getDotContext());
        return mvRegister;
    }

    public static MVRegister<byte[]> fromMVRegisterMessageBytes(MVRegisterProto.MVRegister mvRegisterMessage) {
        MVRegister<byte[]> mvRegister = new MVRegister<>(mvRegisterMessage.getId());

        MVRegisterProto.ValueType valueType = mvRegisterMessage.getValues();
        if (valueType.hasByteSet()) {
            for (ByteString value : valueType.getByteSet().getBytesList()) {
                mvRegister.values.add(value.toByteArray());
            }
        }

        mvRegister.dotContext = DotContext.fromMessageDotContext(mvRegisterMessage.getDotContext());
        return mvRegister;
    }

}
