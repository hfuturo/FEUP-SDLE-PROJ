package feup.sdle.crdts;

import feup.sdle.message.ShoppingListItemProto;

import java.util.Set;

public class ShoppingListItem implements CRDTSingleMergeable<ShoppingListItem> {
    private MVRegister<String> nameRegister;
    private CCounter counter;

    public ShoppingListItem(int localIdentifier) {
        this.nameRegister = new MVRegister<>(localIdentifier);
        this.counter = new CCounter(localIdentifier);
    }

    public ShoppingListItem(int localIdentifier, String name, int value) {
        this.nameRegister = new MVRegister<>(localIdentifier);
        if(!name.isEmpty()) this.nameRegister.update(name);

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
    public void setNameRegister(MVRegister<String> nameRegister) {
        this.nameRegister = nameRegister;
    }

    public Set<String> getName() {
        return this.nameRegister.getValues();
    }
    public int getQuantity() {
        return this.counter.getValue();
    }

    public void updateQuantity(int quantity) {
        this.counter.update(quantity);
    }

    @Override
    public void merge(ShoppingListItem other) {
        this.nameRegister.merge(other.nameRegister);
        this.counter.merge(other.getCounter());
    }

    public ShoppingListItemProto.ShoppingListItem toMessageShoppingListItem() {
        return ShoppingListItemProto.ShoppingListItem.newBuilder()
                .setName(this.nameRegister.toMVRegisterMessage())
                .setCcounter(this.counter.toMessageCCounter())
                .build();
    }

    public static ShoppingListItem fromMessageShoppingListItem(Integer identifier, ShoppingListItemProto.ShoppingListItem msgSLItem) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(identifier);
        shoppingListItem.setNameRegister(MVRegister.fromMVRegisterMessageString(msgSLItem.getName()));
        shoppingListItem.setCounter(CCounter.fromMessageCCounter(msgSLItem.getCcounter()));
        return shoppingListItem;
    }
}
