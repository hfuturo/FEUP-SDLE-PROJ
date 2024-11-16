package feup.sdle.crdts;

public interface CRDTSingleMergeable<V> {
    void merge(V other);
}
