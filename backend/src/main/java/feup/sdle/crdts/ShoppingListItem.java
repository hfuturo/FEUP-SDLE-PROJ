package feup.sdle.crdts;

import feup.sdle.message.ShoppingListItemProto;

public class ShoppingListItem implements CRDTSingleMergeable<ShoppingListItem> {
    private CCounter counter;

    public ShoppingListItem(int localIdentifier) {
        this.counter = new CCounter(localIdentifier);
    }

    public ShoppingListItem(int localIdentifier, int value) {
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

    public int getQuantity() {
        return this.counter.getValue();
    }

    public void updateQuantity(int quantity) {
        this.counter.update(quantity);
    }

    @Override
    public void merge(ShoppingListItem other) {
        this.counter.merge(other.getCounter());
    }

    public ShoppingListItemProto.ShoppingListItem toMessageShoppingListItem() {
        return ShoppingListItemProto.ShoppingListItem.newBuilder()
                .setCcounter(this.counter.toMessageCCounter()).build();
    }

    public static ShoppingListItem fromMessageShoppingListItem(Integer identifier, ShoppingListItemProto.ShoppingListItem msgSLItem) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(identifier);
        shoppingListItem.setCounter(CCounter.fromMessageCCounter(msgSLItem.getCcounter()));
        return shoppingListItem;
    }
}
