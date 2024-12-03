package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.ShoppingList;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.message.AWMapProto;
import feup.sdle.message.DottedValueProto;

import java.util.*;

public class AWMap<K, V extends CRDTSingleMergeable<V>> {
    private DotContext dotContext;
    private NodeIdentifier localIdentifier;
    private AWSet<K> keys;
    @JsonProperty("values")
    private HashMap<K, DottedValue<Integer, Integer, V>> values;

    public AWMap(NodeIdentifier localIdentifier) {
        this.localIdentifier = localIdentifier;
        this.dotContext = new DotContext(this.localIdentifier.getId());
        this.values = new HashMap<>();
        this.keys = new AWSet<>(this.localIdentifier.getId());
    }

    public Optional<Optional<Integer>> latestDot(Integer id) {
        return Optional.of(this.dotContext.latestReplicaDot(id));
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
            Integer dot = this.dotContext.nextOfReplica(localIdentifier.getId());
            this.values.put(id, new DottedValue<>(this.localIdentifier.getId(), dot, value));
        } else {
            item.value().merge(value);
        }
    }

    public void remove(K id) {
        DottedValue<Integer, Integer, V> item = this.values.get(id);
        if(item == null) return;

        this.values.remove(id);
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

        this.dotContext.merge(other.dotContext);
    }

    public AWMapProto.AWMap toMessageProto() {
        var builder = AWMapProto.AWMap.newBuilder()
                .setDotContext(this.dotContext.toMessageDotContext())
                .setLocalIdentifier(this.localIdentifier.toMessageNodeIdentifier())
                .setKeys(this.keys.toMessageAWSet());

        HashMap<String, DottedValueProto.DottedValue> protoEntries = new HashMap<>();

        for (Map.Entry<K, DottedValue<Integer, Integer, V>> entry : this.values.entrySet()) {
            protoEntries.put((String) entry.getKey(), entry.getValue().toMessageDottedValue());
        }

        return builder.putAllValues(protoEntries).build();
    }

    public static AWMap<String, ShoppingListItem> fromMessageAWMap(AWMapProto.AWMap msgAWMap) {
        AWMap<String, ShoppingListItem> awMap = new AWMap<>(NodeIdentifier.fromMessageNodeIdentifier(msgAWMap.getLocalIdentifier()));
        awMap.setDotContext(DotContext.fromMessageDotContext(msgAWMap.getDotContext()));

        HashMap<String, DottedValue<Integer, Integer, ShoppingListItem>> values = new HashMap<>();
        for (Map.Entry<String, DottedValueProto.DottedValue> entry : msgAWMap.getValuesMap().entrySet()) {
            values.put(entry.getKey(), DottedValue.fromMessageDottedValueSLI(entry.getValue()));
        }

        awMap.setValues(values);
        return awMap;
    }
}
