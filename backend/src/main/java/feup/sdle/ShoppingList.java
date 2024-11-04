package feup.sdle;

import feup.sdle.Document;

public class ShoppingList implements Document {
    private String id;
    public ShoppingList() {}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
