package feup.sdle.crdts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CCounterTest {
    CCounter c1;
    CCounter c2;

    @BeforeEach
    public void helper() {
        this.c1 = new CCounter(1);
        this.c2 = new CCounter(2);
    }

    @Test
    public void testIncrement() {
        this.c1.update(1);
        Assertions.assertEquals(1, this.c1.getValue());

        this.c1.update(1);
        Assertions.assertEquals(2, this.c1.getValue());

        this.c1.update(5);
        Assertions.assertEquals(7, this.c1.getValue());

        this.c1.update(10);
        Assertions.assertEquals(17, this.c1.getValue());
    }

    @Test
    public void testDecrement() {

        // test if counter goes below zero
        this.c1.update(-1);
        Assertions.assertEquals(0, this.c1.getValue());

        this.c1.update(-10);
        Assertions.assertEquals(0, this.c1.getValue());

        this.c1.update(5);
        this.c2.update(10);

        this.c1.update(-1);
        Assertions.assertEquals(4, this.c1.getValue());

        this.c1.update(-1);
        Assertions.assertEquals(3, this.c1.getValue());

        this.c2.update(-2);
        Assertions.assertEquals(8, this.c2.getValue());

        this.c2.update(-4);
        Assertions.assertEquals(4, this.c2.getValue());
    }

    @Test
    public void testMerge() {
        CCounter c3 = new CCounter(3);

        this.c1.update(4);
        this.c1.update(-1);
        Assertions.assertEquals(3, this.c1.getValue());

        this.c2.update(10);
        Assertions.assertEquals(this.c2.getValue(), 10);

        this.c1.merge(this.c2); //(1,2,3) -> (1,2,3) (2,1,10)
        this.c2.merge(this.c1); //(2,1,10) -> (1,2,3) (2,1,10)

        Assertions.assertEquals(this.c1.getValue(), this.c2.getValue());
        Assertions.assertEquals(this.c1.getValue(), 13);

        // test if counter ignores dv with lower event number than the one he has
        c3.update(2);
        this.c2.merge(c3); //(1,2,3) (2,1,10) -> (1,2,3) (2,1,10) (3,1,2)

        c3.update(1);
        this.c1.merge(c3); //(1,2,3) (2,1,10) -> (1,2,3) (2,1,10) (3,2,3)

        this.c1.merge(this.c2); //(1,2,3) (2,1,10) (3,2,3) -> (1,2,3) (2,1,10) (3,2,3)
        this.c2.merge(this.c1); //(1,2,3) (2,1,10) (3,1,2) -> (1,2,3) (2,1,10) (3,2,3)

        Assertions.assertEquals(this.c1.getValue(), this.c2.getValue());
        Assertions.assertEquals(this.c1.getValue(), 16);
    }

    @Test
    public void testGetValue() {
        this.c1.update(10);
        this.c1.update(5);
        this.c1.update(2);

        Assertions.assertEquals(17, this.c1.getValue());

        this.c1.update(-2);
        this.c1.update(-10);

        Assertions.assertEquals(5, this.c1.getValue());
    }
}
