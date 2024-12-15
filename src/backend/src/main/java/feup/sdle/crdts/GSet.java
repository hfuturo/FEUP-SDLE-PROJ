package feup.sdle.crdts;

import java.util.HashSet;

public class GSet<V> {
    private HashSet<V> set;

    public GSet() {
        this.set = new HashSet<>();
    }

    public void add(V value) {
        this.set.add(value);
    }

    public void merge(GSet<V> other) {
        this.set.addAll(other.set);
    }
}
