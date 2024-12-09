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
    private NodeIdentifier localIdentifier;
    private HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters;

    public ShoppingList(NodeIdentifier localIdentifier, String id) {
        this.items = new AWMap<>(localIdentifier);
        this.localIdentifier = localIdentifier;
        this.removedCounters = new HashMap<>();
        this.id = id;
    }

    public ShoppingList(NodeIdentifier localIdentifier) {
        System.out.println("IDK SHOPPING LIST NODE IDENTIFIER: " + localIdentifier);
        this.items = new AWMap<>(localIdentifier);
        this.localIdentifier = localIdentifier;
        this.removedCounters = new HashMap<>();
        this.id = UUID.randomUUID().toString();
    }

    public void setNodeIdentifier(NodeIdentifier nodeIdentifier) {
        this.localIdentifier = nodeIdentifier;
        this.items.setNodeIdentifier(nodeIdentifier);
    }

    public void setItems(AWMap<String, ShoppingListItem> items) {
        this.items = items;
    }

    public void setRemovedCounters(HashMap<String, DottedValue<Integer, Integer, Integer>> removedCounters) {
        this.removedCounters = removedCounters;
    }

    public void addItem(String key, String name, int quantity) {
        this.items.add(key, new ShoppingListItem(this.localIdentifier.getId(), name, quantity));
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
     @Override
     public void merge(Document otherDocument) {
        if(!(otherDocument instanceof ShoppingList other)) {
            return;
        }

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
                .setLocalIdentifier(this.localIdentifier.toMessageNodeIdentifier());

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
        ShoppingList shoppingList = new ShoppingList(NodeIdentifier.fromMessageNodeIdentifier(msgShoppingList.getLocalIdentifier()));
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
