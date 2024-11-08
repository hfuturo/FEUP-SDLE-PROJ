package feup.sdle;

import feup.sdle.Document;

import java.util.Map;

public class ShoppingList implements Document {
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
}
