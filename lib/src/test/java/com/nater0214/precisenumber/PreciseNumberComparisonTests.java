package com.nater0214.precisenumber;

import org.junit.jupiter.api.Test;

public class PreciseNumberComparisonTests {

    @Test
    void greaterComparison() {
        PreciseNumber number1 = new PreciseNumber(2);
        PreciseNumber number2 = new PreciseNumber(1);
        assert number1.compareTo(number2) > 0;
    }

    @Test
    void lesserComparison() {
        PreciseNumber number1 = new PreciseNumber(1);
        PreciseNumber number2 = new PreciseNumber(2);
        assert number1.compareTo(number2) < 0;
    }

    @Test
    void equalComparison() {
        PreciseNumber number1 = new PreciseNumber(1);
        PreciseNumber number2 = new PreciseNumber(1);
        assert number1.compareTo(number2) == 0;
    }

    @Test
    void longLesserComparisonMin() {
        PreciseNumber number1 = new PreciseNumber(Long.MIN_VALUE + 1);
        PreciseNumber number2 = new PreciseNumber(1L);
        assert number1.compareTo(number2) < 0;
    }

    @Test
    void doubleLesserComparisonMin() {
        PreciseNumber number1 = new PreciseNumber(-Double.MAX_VALUE);
        PreciseNumber number2 = new PreciseNumber(1.0);
        assert number1.compareTo(number2) < 0;
    }
}
