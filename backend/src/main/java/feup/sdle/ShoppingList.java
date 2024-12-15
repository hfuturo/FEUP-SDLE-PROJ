package feup.sdle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.protobuf.ByteString;
import com.google.protobuf.MapEntry;
import feup.sdle.Document;
import feup.sdle.cluster.NodeIdentifier;
import feup.sdle.crdts.AWMap;
import feup.sdle.crdts.DottedValue;
import feup.sdle.crdts.ShoppingListItem;
import feup.sdle.message.DocumentProto;
import feup.sdle.message.DottedValueProto;
import feup.sdle.message.NodeIdentifierMessage;
import feup.sdle.utils.Color;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)

/**
    * This is a CRDT set that will need to mantain a list of removed items, so in cases of a concurrent edit
    * the behaviour of the counters will not be wrong
*/
@JsonTypeName("shoppingList")
public class ShoppingList implements Document {
    @JsonProperty("id")
    private String id;
    // The String key will never change. This is an id of the shopping list and not the name of the list itself.
    @JsonProperty("items")
    private AWMap<String, ShoppingListItem> items;
    @JsonProperty("localIdentifier")
    private int localIdentifier;
    @JsonProperty("removedCounters")
    private HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters;

    @JsonCreator
    public ShoppingList(@JsonProperty("id") String id, @JsonProperty("items") AWMap<String, ShoppingListItem> items,
                        @JsonProperty("localIdentifier") int localIdentifier, @JsonProperty("removedCounters") HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters) {
        this.id = id;
        this.items = items;
        this.localIdentifier = localIdentifier;
        this.removedCounters = removedCounters;
    }

    public ShoppingList(int localIdentifier, String id) {
        this.items = new AWMap<>(localIdentifier);
        this.localIdentifier = localIdentifier;
        this.removedCounters = new HashMap<>();
        this.id = id;
    }

    public ShoppingList(int localIdentifier) {
        this.items = new AWMap<>(localIdentifier);
        this.localIdentifier = localIdentifier;
        this.removedCounters = new HashMap<>();
        this.id = UUID.randomUUID().toString();
    }

    public void setNodeIdentifier(int nodeIdentifier) {
        this.localIdentifier = nodeIdentifier;
        this.items.setNodeIdentifier(nodeIdentifier);
    }

    public HashMap<String, DottedValue<Integer, Integer, Integer>> getRemovedCounters() {
        return removedCounters;
    }

    public void setItems(AWMap<String, ShoppingListItem> items) {
        this.items = items;
    }

    public void setRemovedCounters(HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters) {
        this.removedCounters = removedCounters;
    }

    public void addItem(String key, String name, int quantity) {
        this.items.add(key, new ShoppingListItem(key, this.localIdentifier, name, quantity));
    }

    public void remove(String key) {
        DottedValue<Integer, Integer, ShoppingListItem> item = this.items.getValue(key);

        DottedValue<Integer, Integer, Integer> removed = this.removedCounters.get(key);
        if(removed != null) {
            this.removedCounters.put(key, new DottedValue<>(item.identifier(), item.event() + 1, item.value().getQuantity() + removed.value()));
            item.value().updateQuantity(-(item.value().getQuantity() + removed.value()));
        } else {
            this.removedCounters.put(key, new DottedValue<>(item.identifier(), item.event() + 1, item.value().getQuantity()));
            item.value().updateQuantity(-(item.value().getQuantity()));
        }

        this.items.remove(key);
    }

     /**
      * When merging we have to see if there is a dotted value with a dot of a remove event that we have after the remove.
      * If so, we have to do change the value of the current counter of the key by decrementing its value.
      *
      * For example, if someone removes the item with the original value 1 but the other one adds 2 to the quantity, the item should remain with value 1.vgit
      */
     @Override
     public void merge(Document otherDocument) {
        if(!(otherDocument instanceof ShoppingList other)) {
            return;
        }

        var modifiedItems = this.getModifiedItems(other.getItems().getValues());

        Optional<Integer> latestOtherDot = this.items.getDotContext().latestReplicaDot(other.localIdentifier);

        this.items.merge(other.items);

        for(Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry: other.removedCounters.entrySet()) {
            if(entry.getValue().identifier() == other.localIdentifier
                    && entry.getValue().event() > latestOtherDot.orElse(0)
                    && this.items.getValue(entry.getKey()) != null) {
                this.removedCounters.put(entry.getKey(), entry.getValue());
                var thisValue = this.items.getValue(entry.getKey());
                var otherValue = other.getItems().getValue(entry.getKey());

                if(thisValue != null && otherValue != null && (!thisValue.value().getCounter().isConcurrent(otherValue.value().getCounter()))) {
                    this.items.remove(entry.getKey());
                    this.removedCounters.put(entry.getKey(), entry.getValue());
                } else {
                    this.items.getValue(entry.getKey()).value().updateQuantity(-entry.getValue().value());
                }
            }
        }

         /*for(Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry: this.removedCounters.entrySet()) {
             if(this.items.getValue(entry.getKey()).value().getCounter().isConcurrent(other.getItems().getValue(entry.getKey()).value().getCounter())) {
                 this.removedCounters.remove(entry.getKey());
             }
         }*/


        /*for (Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry : this.removedCounters.entrySet()) {
            if (this.getItems().getValue(entry.getKey()) != null && !modifiedItems.contains(entry.getKey())) {
                this.getItems().remove(entry.getKey());
            }
        }*/
    }

    private List<String> getModifiedItems(HashMap<String, DottedValue<Integer, Integer, ShoppingListItem>> otherListValues) {
         List<String> modifiedItems = new ArrayList<>();

         for (Map.Entry<String, DottedValue<Integer, Integer, ShoppingListItem>> entry : otherListValues.entrySet()) {
             var localValue = this.items.getValue(entry.getKey());

             if (localValue != null && localValue.value().getQuantity() < entry.getValue().value().getQuantity()) {
                 modifiedItems.add(entry.getKey());
             }
         }

         return modifiedItems;
    }

    public AWMap<String, ShoppingListItem> getItems() {
        return this.items;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ByteString toProtoBuf() {
        return this.toMessage().toByteString();
    }

    @Override
    public DocumentProto.Document toMessage() {
        var builder = DocumentProto.ShoppingList.newBuilder()
                .setItems(this.items.toMessageProto())
                .setLocalIdentifier(this.localIdentifier);

        HashMap<String, DottedValueProto.DottedValue> protoEntries = new HashMap<>();

        for (Map.Entry<String, DottedValue<Integer, Integer, Integer>> entry : this.removedCounters.entrySet()) {
            protoEntries.put(entry.getKey(), entry.getValue().toMessageDottedValue());
        }

        var shoppingList = builder.putAllRemovedCounters(protoEntries).build();

        var documentMessage = DocumentProto.Document.newBuilder()
                .setShoppingList(shoppingList)
                .setKey(this.id)
                .build();

        return documentMessage;
    }

    public static ShoppingList fromMessage(DocumentProto.Document document) {
        var msgShoppingList = document.getShoppingList();
        ShoppingList shoppingList = new ShoppingList(msgShoppingList.getLocalIdentifier());
        shoppingList.setId(document.getKey());
        shoppingList.setItems(AWMap.fromMessageAWMap(msgShoppingList.getItems()));

        HashMap<String , DottedValue<Integer, Integer, Integer>> removedCounters = new HashMap<>();
        for (Map.Entry<String, DottedValueProto.DottedValue> entry : msgShoppingList.getRemovedCountersMap().entrySet()) {
            removedCounters.put(entry.getKey(), DottedValue.fromMessageDottedValueInt(entry.getValue()));
        }

        shoppingList.setRemovedCounters(removedCounters);
        return shoppingList;
    }
}
