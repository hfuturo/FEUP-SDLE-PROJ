package feup.sdle.api;

import feup.sdle.cluster.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ring")
public class HashRingHTTPController {
    private final Node node;

    @Autowired
    public HashRingHTTPController(Node node) {
        this.node = node;
    }

    @GetMapping()
    public ResponseEntity<String> getHashRing() {
        return ResponseEntity.ok().build();
    }
}
