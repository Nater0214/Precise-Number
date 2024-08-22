package com.nater0214.precisenumber;

import org.junit.jupiter.api.Test;

public class PreciseNumberArithmeticTests {

    @Test
    void addition() {
        PreciseNumber a = new PreciseNumber(1);
        PreciseNumber b = new PreciseNumber(2);
        PreciseNumber c = a.add(b);
        assert c.compareTo(new PreciseNumber(3)) == 0;
    }

    @Test
    void subtraction() {
        PreciseNumber a = new PreciseNumber(1);
        PreciseNumber b = new PreciseNumber(2);
        PreciseNumber c = a.subtract(b);
        assert c.compareTo(new PreciseNumber(-1)) == 0;
    }
}
