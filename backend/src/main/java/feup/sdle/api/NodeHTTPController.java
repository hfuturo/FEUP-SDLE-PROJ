package feup.sdle.api;

import feup.sdle.Document;
import feup.sdle.cluster.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart/")
public class NodeHTTPController {
    private final Node node;

    // Constructor injection of Node
    @Autowired
    public NodeHTTPController(Node node) {
        this.node = node;
    }
    @GetMapping("/{key}")
    public ResponseEntity<Document> getDocument(@PathVariable String key) {
        Optional<Document> document = node.getStorage().retrieve(key);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        String key = UUID.randomUUID().toString();
        node.getStorage().store(key, document);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    @PutMapping("/{key}")
    public ResponseEntity<Document> updateDocument(@PathVariable String key, @RequestBody Document document) {
        node.getStorage().store(key, document);
        return ResponseEntity.ok(document);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String key) {
        node.getStorage().delete(key);
        return ResponseEntity.noContent().build();
    }
}
