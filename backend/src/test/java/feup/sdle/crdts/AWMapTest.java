package feup.sdle.crdts;

import feup.sdle.ShoppingList;
import feup.sdle.cluster.NodeIdentifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AWMapTest {
    @Test
    public void addKey() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true));

        map1.add("ovos", new ShoppingListItem(1, 4));

        Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 4);
    }

    @Test
    public void simpleMerge() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true));
        AWMap<String, ShoppingListItem> map2 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(2, "localhost", 2, true));

        map1.add("ovos", new ShoppingListItem(1, 4));
        map2.add("milka", new ShoppingListItem(2, 5));

        map1.merge(map2);
        map2.merge(map1);

        Assertions.assertEquals(map1.getValue("milka").value().getQuantity(), 5);
        Assertions.assertEquals(map2.getValue("milka").value().getQuantity(), 5);

        Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 4);
        Assertions.assertEquals(map2.getValue("ovos").value().getQuantity(), 4);
    }

    @Test
    public void complexMerge() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true));
        AWMap<String, ShoppingListItem> map2 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(2, "localhost", 2, true));
        AWMap<String, ShoppingListItem> map3 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(3, "localhost", 3, true));

        map1.add("ovos", new ShoppingListItem(1, 4));
        map2.add("ovos", new ShoppingListItem(2, 1));
        map3.add("ovos", new ShoppingListItem(3, 3));

        map1.merge(map2);
        map2.merge(map1);
        map2.merge(map3);
        map1.merge(map3);
        map3.merge(map2);
        map3.merge(map1);

        Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 8);
        Assertions.assertEquals(map2.getValue("ovos").value().getQuantity(), 8);
    }

    @Test
    public void resetCounters() {
        ShoppingList sl1 = new ShoppingList(new NodeIdentifier(1, "localhost", 1, true));
        ShoppingList sl2 = new ShoppingList(new NodeIdentifier(2, "localhost", 1, true));

        sl1.addItem("ovos", 4);

        sl2.merge(sl1);
        sl1.merge(sl2);

        Assertions.assertEquals(sl2.getItems().getValue("ovos").value().getQuantity(), 4);

        sl1.getItems().getValue("ovos").value().updateQuantity(4);
        Assertions.assertEquals(sl1.getItems().getValue("ovos").value().getQuantity(), 8);

        sl2.remove("ovos");
        sl1.merge(sl2);

        Assertions.assertEquals(sl1.getItems().getValue("ovos").value().getQuantity(), 4);
        /*map1.getValue("ovs").value().updateQuantity(4);
        Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 8);

        map2.remove("ovos");
        map1.merge(map2);*/

        //Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 4);*/
    }
}
