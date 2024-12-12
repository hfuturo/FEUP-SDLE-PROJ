package feup.sdle.crdts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feup.sdle.message.ShoppingListItemProto;

import java.util.Set;

public class ShoppingListItem implements CRDTSingleMergeable<ShoppingListItem> {
    private MVRegister<String> name;
    private CCounter counter;

    public ShoppingListItem(int localIdentifier) {
        this.name = new MVRegister<>(localIdentifier);
        this.counter = new CCounter(localIdentifier);
    }

    public ShoppingListItem(MVRegister<String> name, CCounter counter) {
        this.name = name;
        this.counter = counter;
    }

    @JsonCreator
    public static ShoppingListItem fromJson(@JsonProperty("counter") CCounter counter, @JsonProperty("name") MVRegister<String> name) {
        return new ShoppingListItem(name, counter);
    }

    public ShoppingListItem(int localIdentifier, String name, int value) {
        this.name = new MVRegister<>(localIdentifier);
        if(!name.isEmpty()) this.name.update(name);

        this.counter = new CCounter(localIdentifier);

        if(value < 0) value = 0;
        this.counter.update(value);
    }

    public CCounter getCounter() {
        return this.counter;
    }

    public void setCounter(CCounter counter) {
        this.counter = counter;
    }
    public void setName(MVRegister<String> name) {
        this.name = name;
    }

    public MVRegister<String> getName() {
        return this.name;
    }
    public Set<String> getNameValue() {
        return this.name.getValues();
    }
    public int getQuantity() {
        return this.counter.getValue();
    }

    public void updateQuantity(int quantity) {
        this.counter.update(quantity);
    }

    @Override
    public void merge(ShoppingListItem other) {
        this.name.merge(other.name);
        this.counter.merge(other.getCounter());
    }

    public ShoppingListItemProto.ShoppingListItem toMessageShoppingListItem() {
        return ShoppingListItemProto.ShoppingListItem.newBuilder()
                .setName(this.name.toMVRegisterMessage())
                .setCcounter(this.counter.toMessageCCounter())
                .build();
    }

    public static ShoppingListItem fromMessageShoppingListItem(Integer identifier, ShoppingListItemProto.ShoppingListItem msgSLItem) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(identifier);
        shoppingListItem.setName(MVRegister.fromMVRegisterMessageString(msgSLItem.getName()));
        shoppingListItem.setCounter(CCounter.fromMessageCCounter(msgSLItem.getCcounter()));
        return shoppingListItem;
    }
}
