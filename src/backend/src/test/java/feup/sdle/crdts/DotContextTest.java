package feup.sdle.crdts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class DotContextTest {
    @Test
    public void concurrent() {
        DotContext context1 = new DotContext(0);
        context1.getDots().put(1, 2);
        context1.getDots().put(2, 3);

        DotContext context2 = new DotContext(0);
        context2.getDots().put(1, 3);
        context2.getDots().put(2, 2);

        Assertions.assertTrue(context1.isConcurrent(context2));
        Assertions.assertFalse(context2.isMoreRecentThan(context1));
        Assertions.assertFalse(context1.isMoreRecentThan(context2));
    }

    @Test
    public void notConcurrent() {
        DotContext context1 = new DotContext(0);
        context1.getDots().put(1, 2);
        context1.getDots().put(2, 3);

        DotContext context2 = new DotContext(0);
        context2.getDots().put(1, 3);
        context2.getDots().put(2, 4);

        Assertions.assertFalse(context1.isConcurrent(context2));
        Assertions.assertFalse(context1.isMoreRecentThan(context2));
        Assertions.assertTrue(context2.isMoreRecentThan(context1));
    }

}
