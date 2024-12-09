package feup.sdle.api;

import feup.sdle.Document;
import feup.sdle.ShoppingList;
import feup.sdle.cluster.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/cart/")
public class NodeHTTPController {
    private final Node node;

    // Constructor injection of Node
    @Autowired
    public NodeHTTPController(Node node) {
        this.node = node;
    }

    @GetMapping
    public ResponseEntity<Map<String, Document>> getAllDocuments() {
        return ResponseEntity.ok(node.retrieveAllDocuments());
    }

    @GetMapping("/{key}")
    public ResponseEntity<Document> getDocument(@PathVariable String key) {
        Optional<Document> document = node.retrieveDocument(key);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{key}")
    public ResponseEntity<Document> updateDocument(@PathVariable String key, @RequestBody Document document) {
        document.setNodeIdentifier(this.node.getNodeIdentifier());

        node.storeDocumentAndReplicate(key, document);
        return ResponseEntity.ok(document);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String key) {
        node.deleteDocument(key);
        return ResponseEntity.noContent().build();
    }
}
