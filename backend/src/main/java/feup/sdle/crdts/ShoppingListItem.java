package feup.sdle.crdts;

public class ShoppingListItem implements CRDTSingleMergeable<ShoppingListItem> {
    private CCounter counter;

    public ShoppingListItem(int localIdentifier) {
        this.counter = new CCounter(localIdentifier);
    }

    public int getQuantity() {
        return this.counter.getValue();
    }

    @Override
    public void merge(ShoppingListItem other) {

    }
}
