package feup.sdle.crdts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AWSetTest {
    @Test
    public void add() {
        AWSet<Integer> set1 = new AWSet<>(1);
        set1.add(4);

        Assertions.assertEquals(set1.getValuesValue().iterator().next(), 4);
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

        Assertions.assertEquals(set1.getValuesValue().iterator().next(), 3);
        Assertions.assertEquals(set2.getValuesValue().iterator().next(), 3);
        Assertions.assertEquals(set1.getValuesValue(), set3.getValuesValue());
        Assertions.assertEquals(set2.getValuesValue(), set3.getValuesValue());
    }

    @Test
    public void mergeConcurrentRemove() {
        AWSet<Integer> set1 = new AWSet<>(1);
        AWSet<Integer> set2 = new AWSet<>(2);

        set1.add(4);
        set2.merge(set1);
        set1.merge(set2);
        Assertions.assertEquals(set2.getValuesValue().size(), 1);

        set2.remove(4);
        Assertions.assertEquals(set2.getValuesValue().size(), 0);

        set1.merge(set2);
        set2.merge(set1);

        Assertions.assertEquals(set1.getValuesValue().size(), 1);
        Assertions.assertEquals(set1.getValuesValue().size(), set2.getValuesValue().size());
        Assertions.assertEquals(set1.getValuesValue(), set2.getValuesValue());
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

        Assertions.assertEquals(set1.getValuesValue().size(), 0);
        Assertions.assertEquals(set1.getValuesValue().size(), set2.getValuesValue().size());
    }
}
