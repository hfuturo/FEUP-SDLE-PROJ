package feup.sdle.storage;

import java.util.Optional;

public class FileStorageProvider<K, V> implements StorageProvider<K, V> {
    StorageBucket<K, V> bucket;

    public FileStorageProvider(StorageBucket<K, V> bucket) {
        this.bucket = bucket;
        this.bucket.create();
    }

    @Override
    public void store(K key, V value) {
        this.bucket.store(key, value);
    }

    @Override
    public Optional<V> retrieve(K key) {
        return Optional.empty();
    }
}