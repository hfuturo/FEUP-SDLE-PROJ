package feup.sdle.storage;

public interface PersistentStorageProvider {
    public void store(String value);
    public String retrieve();
}