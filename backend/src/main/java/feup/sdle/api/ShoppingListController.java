package feup.sdle.api;

import feup.sdle.ShoppingList;
import feup.sdle.cluster.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/")
public class ShoppingListController {
    private final Node node;

    // Constructor injection of Node
    @Autowired
    public ShoppingListController(Node node) {
        this.node = node;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable int id) {
        ShoppingList list = new ShoppingList(); // TODO get list
        //System.out.println(node.getNodeIdentifier());
        if (list != null) {
            return ResponseEntity.ok(list);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        ShoppingList createdList = shoppingList; // TODO create list
        return ResponseEntity.status(HttpStatus.CREATED).body(createdList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(@PathVariable int id, @RequestBody ShoppingList shoppingList) {
        ShoppingList updatedList = shoppingList; // TODO update list
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable int id) {
        // TODO delete list
        return ResponseEntity.noContent().build();
    }
}
