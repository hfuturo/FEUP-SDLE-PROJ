package feup.sdle.storage;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorageProvider<K, V> {
    private ConcurrentHashMap<K, V> documentMap;
    private PersistentStorageProvider persistentStorageProvider;

    public MemoryStorageProvider(PersistentStorageProvider persistentStorageProvider) {
        this.documentMap = new ConcurrentHashMap<>();

        this.persistentStorageProvider = persistentStorageProvider;
    }

    public void store(K key, V value) {
        this.documentMap.put(key, value);
    }

    public Optional<V> retrieve(K key) {
        return Optional.of(this.documentMap.get(key));
    }
}