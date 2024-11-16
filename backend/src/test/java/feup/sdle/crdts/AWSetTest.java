package feup.sdle.crdts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AWSetTest {
    @Test
    public void add() {
        AWSet<Integer> set1 = new AWSet<>(1);
        set1.add(4);

        Assertions.assertEquals(set1.getValues().iterator().next(), 4);
    }
}
