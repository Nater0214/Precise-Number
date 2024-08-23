package com.nater0214.precisenumber;

import org.junit.jupiter.api.Test;

public class PreciseNumberArithmeticTests {

    @Test
    void addition() {
        PreciseNumber a = new PreciseNumber(1);
        PreciseNumber b = new PreciseNumber(2);
        PreciseNumber c = a.add(b);
        assert c.equals(new PreciseNumber(3));
    }

    @Test
    void subtraction() {
        PreciseNumber a = new PreciseNumber(1);
        PreciseNumber b = new PreciseNumber(2);
        PreciseNumber c = a.subtract(b);
        assert c.equals(new PreciseNumber(-1));
    }

    @Test
    void additionLarge() {
        PreciseNumber a = new PreciseNumber(110);
        PreciseNumber b = new PreciseNumber(89);
        PreciseNumber c = a.add(b);
        assert c.equals(new PreciseNumber(199));
    }

    @Test
    void subtractionLarge() {
        PreciseNumber a = new PreciseNumber(110);
        PreciseNumber b = new PreciseNumber(89);
        PreciseNumber c = a.subtract(b);
        assert c.equals(new PreciseNumber(21));
    }
}
