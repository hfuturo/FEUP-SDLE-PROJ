package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.message.AWSetProto;
import feup.sdle.message.DottedValueProto;

import java.util.HashSet;
import java.util.stream.Collectors;

public class AWSet<V> implements CRDTSingleMergeable<AWSet<V>> {
    private HashSet<DottedValue<Integer, Integer, V>> values;
    private DotContext dotContext;
    private int localIdentifier;

    @JsonCreator
    public AWSet(@JsonProperty("localIdentifier") int localIdentifier, @JsonProperty("values") HashSet<DottedValue<Integer, Integer, V>> values, @JsonProperty("dotContext") DotContext dotContext) {
        this.localIdentifier = localIdentifier;
        this.values = values;
        this.dotContext = dotContext;
    }
   
    public AWSet(int localIdentifier) {
        this.localIdentifier = localIdentifier;
        this.values = new HashSet<>();
        this.dotContext = new DotContext(localIdentifier);
    }

    public void setLocalIdentifier(int localIdentifier) {
        this.localIdentifier = localIdentifier;
    }

    public void add(V element) {
        this.values.add(new DottedValue<>(localIdentifier, this.dotContext.nextOfReplica(this.localIdentifier), element));
    }

    public void remove(V element) {
        this.values = (HashSet<DottedValue<Integer, Integer, V>>) this.values.stream().filter(el -> el.value() != element).collect(Collectors.toSet());
    }

    public HashSet<DottedValue<Integer, Integer, V>> getDiffElements(HashSet<DottedValue<Integer, Integer, V>> elements, DotContext dc) {
        return (HashSet<DottedValue<Integer, Integer, V>>) elements.stream().filter(el -> !dc.has(el.identifier(), el.event())).collect(Collectors.toSet());
    }

    @Override
    public void merge(AWSet<V> other) {
        HashSet<DottedValue<Integer, Integer, V>> newSet = new HashSet<>(this.values);
        newSet.retainAll(other.values);

        newSet.addAll(this.getDiffElements(this.values, other.dotContext));
        newSet.addAll(this.getDiffElements(other.values, this.dotContext));

        this.values = newSet;
    }

    public HashSet<V> getValuesValue() {
        return (HashSet<V>) this.values.stream().map(DottedValue::value).collect(Collectors.toSet());
    }

    public HashSet<DottedValue<Integer, Integer, V>> getValues() {
        return this.values;
    }

    public AWSetProto.AWSet toMessageAWSet() {
        var builder = AWSetProto.AWSet.newBuilder()
                .setDotContext(this.dotContext.toMessageDotContext())
                .setLocalIdentifier(this.localIdentifier);

        HashSet<DottedValueProto.DottedValue> protoSet = new HashSet<>();

        this.values.forEach(value -> protoSet.add(value.toMessageDottedValue()));

        return builder.addAllValues(protoSet).build();
    }

    public DotContext getDotContext() {
        return dotContext;
    }

    public int getLocalIdentifier() {
        return localIdentifier;
    }
}