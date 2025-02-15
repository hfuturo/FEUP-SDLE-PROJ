package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.ShoppingList;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.message.AWMapProto;
import feup.sdle.message.DottedValueProto;

import java.util.*;

public class AWMap<K, V extends CRDTSingleMergeable<V>> {
    @JsonProperty("dotContext")
    private DotContext dotContext;
    @JsonProperty("localIdentifier")
    private int localIdentifier;
    @JsonProperty("keys")
    private AWSet<K> keys;
    @JsonProperty("values")
    private HashMap<K, DottedValue<Integer, Integer, V>> values;

    @JsonCreator
    public AWMap(int localIdentifier, DotContext dotContext, HashMap<K, DottedValue<Integer, Integer, V>> values, AWSet<K> keys) {
        this.localIdentifier = localIdentifier;
        this.dotContext = dotContext;
        this.values = values;
        this.keys = keys;
    }

    public int getLocalIdentifier() {
        return localIdentifier;
    }

    public void setLocalIdentifier(int localIdentifier) {
        this.localIdentifier = localIdentifier;
    }

    public AWMap(int localIdentifier) {
        this.localIdentifier = localIdentifier;
        this.dotContext = new DotContext(this.localIdentifier);
        this.values = new HashMap<>();
        this.keys = new AWSet<>(this.localIdentifier);
    }

    public HashMap<K, DottedValue<Integer, Integer, V>> getValues() {
        return this.values;
    }

    public Optional<Optional<Integer>> latestDot(Integer id) {
        return Optional.of(this.dotContext.latestReplicaDot(id));
    }

    public void setNodeIdentifier(int nodeIdentifier) {
        this.localIdentifier = nodeIdentifier;
        this.keys.setLocalIdentifier(this.localIdentifier);
    }

    public void setDotContext(DotContext dotContext) {
        this.dotContext = dotContext;
    }

    public void setKeys(AWSet<K> keys) {
        this.keys = keys;
    }

    public void setValues(HashMap<K, DottedValue<Integer, Integer, V>> values) {
        this.values = values;
    }

    public DotContext getDotContext() {
        return this.dotContext;
    }

    public DottedValue<Integer, Integer, V> getValue(K key) {
        return this.values.get(key);
    }

    public AWSet<K> getKeys() {
        return this.keys;
    }

    public void add(K id, V value) {
        DottedValue<Integer, Integer, V> item = this.values.get(id);

        if(item == null) {
            Integer dot = this.dotContext.nextOfReplica(localIdentifier);
            this.dotContext.getDots().put(localIdentifier, dot);
            this.values.put(id, new DottedValue<>(this.localIdentifier, dot, value));
            this.keys.add(id);
        } else {
            item.value().merge(value);
        }
    }

    public void remove(K id) {
        DottedValue<Integer, Integer, V> item = this.values.get(id);
        if(item == null) return;

        this.values.remove(id);
        this.keys.remove(id);
    }

    // (𝑚, 𝑐) ⊔ (𝑚′, 𝑐′) = ({𝑘 ↦ → v(𝑘) | 𝑘 ∈ dom 𝑚 ∪ dom 𝑚′ ∧ v(𝑘) ≠ ⊥}, 𝑐 ∪ 𝑐′)
    // where v(𝑘) = fst ((𝑚[𝑘], 𝑐) ⊔ (𝑚′ [𝑘], 𝑐′))
    public void merge(AWMap<K, V> other) {
        Set<K> localKeys = this.values.keySet();
        Set<K> otherKeys = new HashSet<>(other.values.keySet());

        Set<K> commonKeys = new HashSet<>(localKeys);
        commonKeys.retainAll(otherKeys);

        for(K key : commonKeys) {
            this.values.get(key).value().merge(other.values.get(key).value());
        }

        otherKeys.removeAll(localKeys);
        for(K key: otherKeys) {
           this.values.put(key, other.values.get(key));
        }

        this.keys.merge(other.keys);
        this.dotContext.merge(other.dotContext);
    }

    public AWMapProto.AWMap toMessageProto() {
        var builder = AWMapProto.AWMap.newBuilder()
                .setDotContext(this.dotContext.toMessageDotContext())
                .setLocalIdentifier(this.localIdentifier)
                .setKeys(this.keys.toMessageAWSet());

        HashMap<String, DottedValueProto.DottedValue> protoEntries = new HashMap<>();

        for (Map.Entry<K, DottedValue<Integer, Integer, V>> entry : this.values.entrySet()) {
            protoEntries.put((String) entry.getKey(), entry.getValue().toMessageDottedValue());
        }

        return builder.putAllValues(protoEntries).build();
    }

    public static AWMap<String, ShoppingListItem> fromMessageAWMap(AWMapProto.AWMap msgAWMap) {
        AWMap<String, ShoppingListItem> awMap = new AWMap<>(msgAWMap.getLocalIdentifier());
        awMap.setDotContext(DotContext.fromMessageDotContext(msgAWMap.getDotContext()));

        HashMap<String, DottedValue<Integer, Integer, ShoppingListItem>> values = new HashMap<>();
        for (Map.Entry<String, DottedValueProto.DottedValue> entry : msgAWMap.getValuesMap().entrySet()) {
            values.put(entry.getKey(), DottedValue.fromMessageDottedValueSLI(entry.getValue()));
        }

        awMap.setValues(values);
        return awMap;
    }
}
