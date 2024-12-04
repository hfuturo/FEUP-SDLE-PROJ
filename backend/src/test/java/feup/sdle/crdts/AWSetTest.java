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

    @Test
    public void mergeAdd() {
        AWSet<Integer> set1 = new AWSet<>(1);
        set1.add(4);
        AWSet<Integer> set2 = new AWSet<>(2);
        set2.add(3);

        set1.merge(set2);
        set2.merge(set1);

        AWSet<Integer> set3 = new AWSet<>(3);
        set3.add(4);
        set3.add(3);

        Assertions.assertEquals(set1.getValues().iterator().next(), 3);
        Assertions.assertEquals(set2.getValues().iterator().next(), 3);
        Assertions.assertEquals(set1.getValues(), set3.getValues());
        Assertions.assertEquals(set2.getValues(), set3.getValues());
    }

    @Test
    public void mergeConcurrentRemove() {
        AWSet<Integer> set1 = new AWSet<>(1);
        AWSet<Integer> set2 = new AWSet<>(2);

        set1.add(4);
        set2.merge(set1);
        set1.merge(set2);
        Assertions.assertEquals(set2.getValues().size(), 1);

        set2.remove(4);
        Assertions.assertEquals(set2.getValues().size(), 0);

        set1.merge(set2);
        set2.merge(set1);

        Assertions.assertEquals(set1.getValues().size(), 1);
        Assertions.assertEquals(set1.getValues().size(), set2.getValues().size());
        Assertions.assertEquals(set1.getValues(), set2.getValues());
    }

    @Test
    public void mergeNonConcurrentRemove() {
        AWSet<Integer> set1 = new AWSet<>(1);
        AWSet<Integer> set2 = new AWSet<>(2);

        set1.add(4);
        set2.merge(set1);
        set1.merge(set2);

        set1.remove(4);
        set2.remove(4);

        set1.merge(set2);
        set2.merge(set1);

        Assertions.assertEquals(set1.getValues().size(), 0);
        Assertions.assertEquals(set1.getValues().size(), set2.getValues().size());
    }
}
