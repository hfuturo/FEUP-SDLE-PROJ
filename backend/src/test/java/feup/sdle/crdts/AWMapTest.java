package feup.sdle.crdts;

import feup.sdle.ShoppingList;
import feup.sdle.cluster.NodeIdentifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AWMapTest {
    @Test
    public void addKey() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true, 1));

        map1.add("O", new ShoppingListItem(1, "ovos", 4));

        Assertions.assertEquals(map1.getValue("O").value().getQuantity(), 4);
        Assertions.assertEquals(map1.getValue("O").value().getName().iterator().next(), "ovos");
    }

    @Test
    public void simpleMerge() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true, 1));
        AWMap<String, ShoppingListItem> map2 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(2, "localhost", 2, true, 1));

        map1.add("O", new ShoppingListItem(1, "ovos", 4));
        map2.add("M", new ShoppingListItem(2, "milka", 5));

        map1.merge(map2);
        map2.merge(map1);

        Assertions.assertEquals(map1.getValue("M").value().getQuantity(), 5);
        Assertions.assertEquals(map1.getValue("M").value().getName().iterator().next(), "milka");
        Assertions.assertEquals(map2.getValue("M").value().getQuantity(), 5);
        Assertions.assertEquals(map2.getValue("M").value().getName().iterator().next(), "milka");

        Assertions.assertEquals(map1.getValue("O").value().getQuantity(), 4);
        Assertions.assertEquals(map1.getValue("O").value().getName().iterator().next(), "ovos");
        Assertions.assertEquals(map2.getValue("O").value().getQuantity(), 4);
        Assertions.assertEquals(map2.getValue("O").value().getName().iterator().next(), "ovos");
    }

    @Test
    public void complexMerge() {
        AWMap<String, ShoppingListItem> map1 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(1, "localhost", 1, true, 1));
        AWMap<String, ShoppingListItem> map2 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(2, "localhost", 2, true, 1));
        AWMap<String, ShoppingListItem> map3 = new AWMap<String, ShoppingListItem>(new NodeIdentifier(3, "localhost", 3, true, 1));

        map1.add("O", new ShoppingListItem(1, "ovos", 4));
        map2.add("O", new ShoppingListItem(2, "ovos", 1));
        map3.add("O", new ShoppingListItem(3, "ovos", 3));

        map1.merge(map2);
        map2.merge(map1);
        map2.merge(map3);
        map1.merge(map3);
        map3.merge(map2);
        map3.merge(map1);

        Assertions.assertEquals(map1.getValue("O").value().getQuantity(), 8);
        Assertions.assertEquals(map1.getValue("O").value().getName().iterator().next(), "ovos");
        Assertions.assertEquals(map2.getValue("O").value().getQuantity(), 8);
        Assertions.assertEquals(map2.getValue("O").value().getName().iterator().next(), "ovos");
        Assertions.assertEquals(map3.getValue("O").value().getQuantity(), 8);
        Assertions.assertEquals(map3.getValue("O").value().getName().iterator().next(), "ovos");
    }

    @Test
    public void resetCounters() {
        ShoppingList sl1 = new ShoppingList(new NodeIdentifier(1, "localhost", 1, true, 1));
        ShoppingList sl2 = new ShoppingList(new NodeIdentifier(2, "localhost", 1, true, 1));

        sl1.addItem("O","ovos", 4);

        sl2.merge(sl1);
        sl1.merge(sl2);

        Assertions.assertEquals(sl2.getItems().getValue("O").value().getQuantity(), 4);
        Assertions.assertEquals(sl2.getItems().getValue("O").value().getName().iterator().next(), "ovos");

        sl1.getItems().getValue("O").value().updateQuantity(4);
        Assertions.assertEquals(sl1.getItems().getValue("O").value().getQuantity(), 8);

        sl2.remove("O");
        sl1.merge(sl2);

        Assertions.assertEquals(sl1.getItems().getValue("O").value().getQuantity(), 4);
        /*map1.getValue("ovs").value().updateQuantity(4);
        Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 8);

        map2.remove("ovos");
        map1.merge(map2);*/

        //Assertions.assertEquals(map1.getValue("ovos").value().getQuantity(), 4);*/
    }
}
