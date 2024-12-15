package feup.sdle.storage;

import feup.sdle.cluster.NodeIdentifier;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorageProvider<K, V> implements StorageProvider<K, V> {
    private ConcurrentHashMap<K, V> documentMap;
    private StorageProvider<K, V> persistentStorageProvider;
    private NodeIdentifier localNode;

    public MemoryStorageProvider(StorageProvider<K, V> persistentStorageProvider) {
        this.documentMap = new ConcurrentHashMap<>();

        this.persistentStorageProvider = persistentStorageProvider;
    }

    @Override
    public void store(K key, V value) {
        this.documentMap.put(key, value);

        Thread.ofVirtual().start(() -> {
                this.persistentStorageProvider.store(key, value);
            }
        );
    }

    @Override
    public Optional<V> retrieve(K key) {
        V document = this.documentMap.get(key);

        // If it does not exist, try to retrieve from disk
        if(document == null) {
            Optional<V> opt = this.persistentStorageProvider.retrieve(key);

            if(opt.isPresent()) {
                this.documentMap.put(key, opt.get());
            }

            return opt;
        }

        return Optional.of(document);
    }

    public Map<K, V> retrieveAll() {
        return documentMap;
    }

    public void delete(K key) {
        this.documentMap.remove(key);
    }
}