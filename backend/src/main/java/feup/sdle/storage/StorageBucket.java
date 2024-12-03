package feup.sdle.storage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;

public class StorageBucket<K, V> {
    String bucketName;

    public StorageBucket(String bucketName) {
        this.bucketName = bucketName;
    }

    public void create() {
        File folder = new File("data/" + bucketName);

        if(folder.mkdir()) {
            System.out.println("Folder created: " + bucketName);
        } else {
            System.out.println("Folder not created: " + bucketName);
        }
    }

    public void store(K key, V value) {
        File file = new File("data/" + bucketName + "/" + key + ".json");

        if(file.exists()) {
            // update file
        }

        try {
            if (file.createNewFile()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(file, value);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
