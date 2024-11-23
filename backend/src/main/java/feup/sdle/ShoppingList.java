package feup.sdle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.protobuf.MapEntry;
import feup.sdle.Document;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.crdts.AWMap;
import feup.sdle.crdts.DottedValue;
import feup.sdle.crdts.ShoppingListItem;
import feup.sdle.message.DocumentProto;
import feup.sdle.message.DottedValueProto;
import feup.sdle.message.NodeIdentifierMessage;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.*;

/**
    * This is a CRDT set that will need to mantain a list of removed items, so in cases of a concurrent edit
    * the behaviour of the counters will not be wrong
*/
public class ShoppingList implements Document {
    private String id;
    // The String key will never change. This is an id of the shopping list and not the name of the list itself.
    private AWMap<String, ShoppingListItem> items;
    private NodeIdentifier localIdentifier;
    private HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters;

    public ShoppingList(NodeIdentifier localIdentifier) {
        this.items = new AWMap<>(localIdentifier);
        this.localIdentifier = localIdentifier;
        this.removedCounters = new HashMap<>();
    }

    public void setNodeIdentifier(NodeIdentifier nodeIdentifier) {
        this.localIdentifier = nodeIdentifier;
    }

    public void setItems(AWMap<String, ShoppingListItem> items) {
        this.items = items;
    }

    public void addItem(String key, int quantity) {
        this.items.add(key, new ShoppingListItem(this.localIdentifier.getId(), quantity));
    }

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
        Optional<Integer> latestOtherDot = this.items.getDotContext().latestReplicaDot(other.localIdentifier.getId());

        this.items.merge(other.items);

        if(latestOtherDot.isEmpty()) return;

        for(Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry: other.removedCounters.entrySet()) {
            if(entry.getValue().identifier() == other.localIdentifier.getId() && entry.getValue().event() > latestOtherDot.get()) {
                this.items.getValue(entry.getKey()).value().updateQuantity(-entry.getValue().value());
            }
        }
    }

    public AWMap<String, ShoppingListItem> getItems() {
        return this.items;
    }

    public DocumentProto.ShoppingList toMessageShoppingList() {
        var builder = DocumentProto.ShoppingList.newBuilder()
                .setItems(this.items.toMessageProto())
                .setLocalIdentifier(this.localIdentifier.toMessageNodeIdentifier());

        HashMap<String, DottedValueProto.DottedValue> protoEntries = new HashMap<>();

        for (Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry : this.removedCounters.entrySet()) {
            protoEntries.put(entry.getKey(), entry.getValue().toMessageDottedValue());
        }

        return builder.putAllRemovedCounters(protoEntries).build();
    }
}
