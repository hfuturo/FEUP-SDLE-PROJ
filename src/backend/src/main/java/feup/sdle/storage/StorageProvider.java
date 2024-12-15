package feup.sdle.storage;

import java.util.Optional;

public interface StorageProvider<K, V> {
    public void store(K key, V value);
    public Optional<V> retrieve(K key);
}