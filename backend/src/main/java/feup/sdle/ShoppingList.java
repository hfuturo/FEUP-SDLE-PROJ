package feup.sdle;

import feup.sdle.Document;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.crdts.AWMap;
import feup.sdle.crdts.DottedValue;
import feup.sdle.crdts.ShoppingListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 /**
    * This is a CRDT set that will need to mantain a list of removed items, so in cases of a concurrent edit
    * the behaviour of the counters will not be wrong
*/
public class ShoppingList implements Document {
    // The String key will never change. This is an id of the shopping list and not the name of the list itself.
    private AWMap<String, ShoppingListItem> items;
    private HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters;
    private NodeIdentifier localIdentifier;

    public ShoppingList(NodeIdentifier localIdentifier) {
        this.items = new AWMap<>(localIdentifier);
        this.removedCounters = new HashMap<>();
        this.localIdentifier = localIdentifier;
    }

     /**
      * The goal is to keep track of the resetted values so that when merging we can just decrement the reset values so that
      * the awmap remove anomaly is minimized
      */
    public void remove(String key) {
        DottedValue<Integer, Integer, ShoppingListItem> item = this.items.getValue(key);

        DottedValue<Integer, Integer, Integer> removed = this.removedCounters.get(key);
        if(removed != null) {
            this.removedCounters.put(key, new DottedValue<>(item.identifier(), item.event(), item.value().getQuantity() + removed.value()));
        } else {
            this.removedCounters.put(key, new DottedValue<>(item.identifier(), item.event(), item.value().getQuantity()));
        }

        this.items.remove(key);
    }

     /**
      * When merging we have to see if there is a dotted value with a dot of a remove event that we have after the remove.
      * If so, we have to do change the value of the current counter of the key by decrementing its value.
      *
      * For example, if someone removes the item with the original value 1 but the other one adds 2 to the quantity, the item should remain with value 1.vgit
      */
    public void merge(ShoppingList other) {
        // 1. Have to check if there were removed items with dots that we do not have. If so, update the counter value with that after merging the items
        // While merging the items, since this is an add wins semantic, the concurrent remove will be obfuscated by the add
        Integer otherLocalDot = this.items.latestDot(other.localIdentifier.getId());

        this.items.merge(other.items);
    }

    public AWMap<String, ShoppingListItem> getItems() {
        return this.items;
    }
}
