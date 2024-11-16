package feup.sdle.crdts;

import feup.sdle.cluster.NodeIdentifier;

import java.util.HashMap;
import java.util.Set;

public class AWMap<K, V extends CRDTSingleMergeable<V>> {
    private DotContext dotContext;
    private NodeIdentifier localIdentifier;
    private HashMap<K, DottedValue<Integer, Integer, V>> values;

    public AWMap(NodeIdentifier localIdentifier) {
        this.localIdentifier = localIdentifier;
        this.dotContext = new DotContext(this.localIdentifier.getId());
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
    public void merge(AWMap<K, V> other, DotContext otherContext) {
        Set<K> localKeys = this.values.keySet();
        Set<K> otherKeys = other.values.keySet();

        localKeys.forEach(el -> {
            if(!otherKeys.contains(el)) {
                this.values.remove(el);
            }
        });

        otherKeys.removeAll(localKeys);
        for(K key: otherKeys) {
           this.values.put(key, other.values.get(key));
        }

        this.dotContext.merge(otherContext);
    }
}
