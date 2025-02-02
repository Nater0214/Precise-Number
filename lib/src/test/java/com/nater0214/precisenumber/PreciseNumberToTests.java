package com.nater0214.precisenumber;

import org.junit.jupiter.api.Test;

public class PreciseNumberToTests {

    @Test
    void toLong() {
        PreciseNumber number = new PreciseNumber(1);
        assert number.longValue() == 1L;
    }

    @Test
    void toInt() {
        PreciseNumber number = new PreciseNumber(1);
        assert number.intValue() == 1;
    }

    @Test
    void toDouble() {
        PreciseNumber number = new PreciseNumber(1);
        assert number.doubleValue() == 1.0;
    }

    @Test
    void toFloat() {
        PreciseNumber number = new PreciseNumber(1);
        assert number.floatValue() == 1.0f;
    }

    @Test
    void toLongZero() {
        PreciseNumber number = new PreciseNumber(0);
        assert number.longValue() == 0L;
    }

    @Test
    void toDoubleZero() {
        PreciseNumber number = new PreciseNumber(0);
        assert number.doubleValue() == 0.0;
    }
}
