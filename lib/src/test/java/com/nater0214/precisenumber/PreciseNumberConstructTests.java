package com.nater0214.precisenumber;

import org.junit.jupiter.api.Test;

public class PreciseNumberConstructTests {

    @SuppressWarnings("unused")
    @Test
    void longConstruct() {
        PreciseNumber number = new PreciseNumber(1L);
    }

    @SuppressWarnings("unused")
    @Test
    void intConstruct() {
        PreciseNumber number = new PreciseNumber(1);
    }

    @SuppressWarnings("unused")
    @Test
    void doubleConstruct() {
        PreciseNumber number = new PreciseNumber(1.0);
    }

    @SuppressWarnings("unused")
    @Test
    void floatConstruct() {
        PreciseNumber number = new PreciseNumber(1.0f);
    }

    @SuppressWarnings("unused")
    @Test
    void stringConstruct() {
        PreciseNumber number = new PreciseNumber("1");
    }

    @SuppressWarnings("unused")
    @Test
    void stringConstructDecimal() {
        PreciseNumber number = new PreciseNumber("1.0");
    }

    @SuppressWarnings("unused")
    @Test
    void longConstructMax() {
        PreciseNumber number = new PreciseNumber(Long.MAX_VALUE);
    }

    @SuppressWarnings("unused")
    @Test
    void longConstructMin() {
        PreciseNumber number = new PreciseNumber(Long.MIN_VALUE);
    }

    @SuppressWarnings("unused")
    @Test
    void longConstructZero() {
        PreciseNumber number = new PreciseNumber(0L);
    }

    @SuppressWarnings("unused")
    @Test
    void longConstructMinPlusOne() {
        PreciseNumber number = new PreciseNumber(Long.MIN_VALUE + 1);
    }

    @SuppressWarnings("unused")
    @Test
    void doubleConstructMax() {
        PreciseNumber number = new PreciseNumber(Double.MAX_VALUE);
    }

    @SuppressWarnings("unused")
    @Test
    void doubleConstructMin() {
        PreciseNumber number = new PreciseNumber(-Double.MAX_VALUE);
    }

    @SuppressWarnings("unused")
    @Test
    void doubleConstructZero() {
        PreciseNumber number = new PreciseNumber(0.0);
    }

    @SuppressWarnings("unused")
    @Test
    void doubleConstructMinNonZero() {
        PreciseNumber number = new PreciseNumber(Double.MIN_VALUE);
    }
}
