package feup.sdle.api;

import feup.sdle.ShoppingList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RequestMapping("/api/cart/")
public class ShoppingListController {
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable int id) {
        ShoppingList list = new ShoppingList(); // TODO get list
        if (list != null) {
            return ResponseEntity.ok(list);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

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
