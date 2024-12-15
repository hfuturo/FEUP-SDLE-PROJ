package feup.sdle.crdts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class MVRegisterTest {
    @Test
    public void updateInteger() {
        MVRegister<Integer> mv1 = new MVRegister<>(1);
        mv1.update(4);

        Assertions.assertEquals(4, mv1.getValues().iterator().next());
    }

    @Test
    public void updateString() {
        MVRegister<String> mv1 = new MVRegister<>(1);
        mv1.update("hello");

        Assertions.assertEquals("hello", mv1.getValues().iterator().next());
    }

    @Test
    public void merge() {
        MVRegister<Integer> set1 = new MVRegister<>(1);
        MVRegister<Integer> set2 = new MVRegister<>(2);

        set1.update(3);

        set2.merge(set1);
        set1.merge(set2);

        set2.update(4);

        set2.merge(set1);
        set1.merge(set2);

        Set<Integer> testSet = new HashSet<>();
        testSet.add(4);

        Assertions.assertEquals(testSet, set1.getValues());
        Assertions.assertEquals(testSet, set2.getValues());
    }

    @Test
    public void mergeConcurrent() {
        MVRegister<Integer> set1 = new MVRegister<>(1);
        MVRegister<Integer> set2 = new MVRegister<>(2);

        set1.update(3);
        set2.update(4);

        set2.merge(set1);
        set1.merge(set2);

        Set<Integer> testSet = new HashSet<>();
        testSet.add(3);
        testSet.add(4);

        Assertions.assertEquals(testSet, set1.getValues());
        Assertions.assertEquals(testSet, set2.getValues());
    }
    
    @Test
    public void mergeConcurrentResolution() {
        MVRegister<Integer> set1 = new MVRegister<>(1);
        MVRegister<Integer> set2 = new MVRegister<>(2);

        set1.update(3);
        set2.update(4);

        set1.merge(set2);
        set2.merge(set1);

        set1.update(3);

        set1.merge(set2);
        set2.merge(set1);

        Set<Integer> testSet = new HashSet<>();
        testSet.add(3);

        Assertions.assertEquals(testSet, set1.getValues());
        Assertions.assertEquals(testSet, set2.getValues());
    }
}
