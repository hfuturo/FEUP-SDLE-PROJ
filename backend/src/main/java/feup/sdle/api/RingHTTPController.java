package feup.sdle.api;

import feup.sdle.Document;
import feup.sdle.cluster.HashRing;
import feup.sdle.cluster.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/ring/")
public class RingHTTPController {
    private final Node node;

    @Autowired
    public RingHTTPController(Node node) {
        this.node = node;
    }

    @GetMapping
    public ResponseEntity<HashRing> getRing() {
        return ResponseEntity.ok(node.getRing());
    }
}
