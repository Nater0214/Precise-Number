package com.nater0214.precisenumber;

import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;

/**
 * This is a perfectly precise number class
 *
 * @author Nater0214
 */
public class PreciseNumber extends Number implements Comparable<PreciseNumber> {

    /******************
     * Private values *
     ******************/

    /**
     * The digits of the number
     */
    private final List<Integer> digits;

    /**
     * The exponent of the number as a power of 10
     */
    private final int exponent;

    /**
     * The sign of the number; true for positive, false for negative
     */
    private final boolean sign;

    /*******************
     * Utility Methods *
     *******************/

    private static SimpleImmutableEntry<List<Integer>, Integer> removeTrailing(List<Integer> digits) {

        // Create new digits
        List<Integer> newDigits = new ArrayList<>(digits);

        // Initialize the exponent
        int exponent = 0;

        // Remove trailing zeros and set exponent
        if (newDigits.size() > 0) {
            while (newDigits.get(newDigits.size() - 1) == 0) {
                newDigits.remove(newDigits.size() - 1);
                exponent++;
            }
        } else {
            exponent = 0;
        }

        // Return the values
        return new SimpleImmutableEntry<>(newDigits, exponent);
    }

    /****************
     * Constructors *
     ****************/

    /**
     * Create a new precise number from a {@code long} value
     * @param value The {@code long} value to create from; has to be greater than {@code Long.MIN_VALUE + 1}
     * @throws IllegalArgumentException If the number is too small
     */
    public PreciseNumber(long value) {

        // Check if number is too small
        if (value < Long.MIN_VALUE + 1)
            throw new IllegalArgumentException("Number is too small");

        // Get the sign
        boolean sign = value >= 0;

        // Create digits list
        List<Integer> digits = new ArrayList<>();

        // Initialize the exponent
        int exponent = 0;

        // If number is negative, make it positive
        value = sign ? value : -value;

        // Loop adding digits
        for (int e = (int) Math.log10(value); e >= 0; e--) {
            int digit = (int) (value / (int) Math.pow(10, e));
            digits.add(digit);
            value -= digit * Math.pow(10, e);
        }

        // Remove trailing zeros and set exponent
        SimpleImmutableEntry<List<Integer>, Integer> result = removeTrailing(digits);
        digits = result.getKey();
        exponent = result.getValue();

        // Set all object values
        this.digits = digits;
        this.exponent = exponent;
        this.sign = sign;
    }

    /**
     * Create a new precise number from a {@code double} value
     * @param value The {@code double} value to create from
     * @param roundDigit The number of decimal places to round the number to
     */
    public PreciseNumber(double value, int roundDigit) {

        // Get the sign
        boolean sign = value >= 0;

        // Create digits list
        List<Integer> digits = new ArrayList<>();

        // Initialize the exponent as the negative of roundDigit
        int exponent = roundDigit * -1;

        // If the number is negative, make it positive
        value = sign ? value : -value;

        // Loop adding digits
        for (int e = (int) Math.log10(value); e >= roundDigit * -1; e--) {
            int digit = (int) (value / (int) Math.pow(10, e));
            digits.add(digit);
            value -= digit * Math.pow(10, e);
        }

        // Remove trailing zeros and set exponent
        SimpleImmutableEntry<List<Integer>, Integer> result = removeTrailing(digits);
        digits = result.getKey();
        exponent = result.getValue();

        // Set all object values
        this.digits = digits;
        this.exponent = exponent;
        this.sign = sign;
    }

    /**
     * Create a new precise number from a {@code double} value with a default round digit of 15
     * @param value The {@code double} value to create from
     * @see #PreciseNumber(double, int)
     */
    public PreciseNumber(double value) {
        this(value, 15);
    }

    /**
     * Create a new precise number from a {@code float} value
     * @param value The {@code float} value to create from
     * @param roundDigit
     */
    public PreciseNumber(float value, int roundDigit) {
        this((double) value, roundDigit);
    }

    /**
     * Create a new precise number from a {@code float} value with a default round digit of 7
     * @param value The {@code float} value to create from
     * @see #PreciseNumber(float, int)
     */
    public PreciseNumber(float value) {
        this(value, 7);
    }

    /**
     * Create a new precise number from an {@code int} value
     * @param value The {@code int} value to create from
     */
    public PreciseNumber(int value) {
        this((long) value);
    }

    /**
     * Create a new precise number from a {@code String} value containing a number
     * @param value The {@code String} value to create from
     * @throws NumberFormatException If the number is unable to parse
     */
    public PreciseNumber(String value) {

        // Get the sign of the number
        boolean sign = value.charAt(0) != '-';

        // Create digits list
        List<Integer> digits = new ArrayList<>();

        // If the number is negative, make it positive
        value = sign ? value : value.substring(1);

        // Initialize the exponent based on the position of the decimal point
        int exponent;
        int indexOfPoint = value.indexOf('.');
        if (indexOfPoint == -1) {
            exponent = 0;
        } else {
            exponent = value.length() - indexOfPoint - 1;
            value = value.replace(".", "");
        }

        // Loop adding digits
        try {
            for (int e = value.length() - 1; e >= 0; e--) {
                int digit = Integer.parseInt(value.substring(e, e + 1));
                digits.add(digit);
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("The number was unable to parse!");
        }

        // Remove leading zeros
        while (digits.get(0) == 0)
            digits.remove(0);

        // Set all object values
        this.digits = digits;
        this.exponent = exponent;
        this.sign = sign;
    }

    /**
     * Create a new precise number from its components
     * @param digits The digits
     * @param exponent The exponent
     * @param sign The sign
     */
    public PreciseNumber(List<Integer> digits, int exponent, boolean sign) {

        // Remove trailing zeros and add to exponent
        SimpleImmutableEntry<List<Integer>, Integer> result = removeTrailing(digits);
        digits = result.getKey();
        exponent += result.getValue();

        // Set all object values
        this.digits = digits;
        this.exponent = exponent;
        this.sign = sign;
    }

    /**
     * Create a new precise number from its components with an exponent of {@code 0}
     * @param digits The digits
     * @param sign The sign
     */
    public PreciseNumber(List<Integer> digits, boolean sign) {
        this(digits, 0, sign);
    }

    /**
     * Create a new precise number with the value of zero
     */
    public PreciseNumber() {
        this(0);
    }

    /***********
     * Getters *
     ***********/

    /**
     * @return The digits of the number
     */
    protected List<Integer> getDigits() {
        return digits;
    }

    /**
     * @return The digits of the number with trailing zeroes
     */
    protected List<Integer> getDigitsTrailing() {
        List<Integer> out = new ArrayList<>();
        for (int e = exponent; e >= 0; e--) {
            out.add(0);
        }
        out.addAll(digits);
        return out;
    }

    /**
     * @return The exponent of the number
     */
    protected int getExponent() {
        return exponent;
    }

    /**
     * @return The sign of the number
     */
    protected boolean getSign() {
        return sign;
    }

    /**************
     * Converters *
     **************/

    /**
     * Get this number as an {@code long}; if the number is greater than {@code Long.MAX_VALUE}, that value is returned
     * @return This number as an {@code long}
     */
    @Override
    public long longValue() {

        // Initialize value
        long value = 0;

        // If the number is greater than the long limit then return it
        if (this.compareTo(new PreciseNumber(Long.MAX_VALUE)) > 0)
            return Long.MAX_VALUE;

        // If the number if less than the long limit then return it
        if (this.compareTo(new PreciseNumber(Long.MIN_VALUE + 1)) < 0)
            return Long.MIN_VALUE;

        // Add digits to the value
        for (Integer digit : digits) {
            value *= 10;
            value += digit;
        }

        // Multiply the value by the power of the exponent
        value *= Math.pow(10, exponent);

        // If the number is negative, make it negative
        value *= sign ? 1 : -1;

        // Return the value
        return value;
    }

    /**
     * Get this number as an {@code int}; if the number is greater than {@code Integer.MAX_VALUE}, that value is returned
     * @return This number as an {@code int}
     */
    @Override
    public int intValue() {
        return (int) longValue();
    }

    /**
     * Get this number as a {@code double}
     * @return This number as a {@code double}
     */
    @Override
    public double doubleValue() {

        // Initialize value
        double value = 0;

        // If the number is greater than the double limit then return it
        if (this.compareTo(new PreciseNumber(Double.MAX_VALUE)) > 0)
            return Double.MAX_VALUE;

        // If the number if less than the double limit then return it
        if (this.compareTo(new PreciseNumber(-Double.MAX_VALUE)) < 0)
            return Double.MIN_VALUE;

        // Add digits to the value
        for (Integer digit : digits) {
            value *= 10;
            value += digit;
        }

        // Multiply the value by the power of the exponent
        value *= Math.pow(10, exponent);

        // If the number is negative, make it negative
        value *= sign ? 1 : -1;

        // Return the value
        return value;
    }

    /**
     * Get this number as a {@code float}
     * @return This number as a {@code float}
     */
    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    /**************
     * Operations *
     **************/

    /**
     * Compare this number to another {@code PreciseNumber}
     * @param o The other {@code PreciseNumber} to compare to
     * @return A negative, zero, or positive integer depending on the comparison results
     */
    @Override
    public int compareTo(PreciseNumber o) {

        // If signs are different, the numbers are different
        if (this.getSign() != o.getSign())
            return this.getSign() ? 1 : -1;

        // Get the numbers with trailing zeroes
        List<Integer> thisDigits = this.getDigitsTrailing();
        List<Integer> oDigits = o.getDigitsTrailing();

        // If the lengths are different, the numbers are different
        if (thisDigits.size() != oDigits.size())
            return thisDigits.size() > oDigits.size() ? 1 : -1;

        // Compare each digit
        for (int e = 0; e < thisDigits.size(); e++)
            if (thisDigits.get(e) != oDigits.get(e))
                return thisDigits.get(e) > oDigits.get(e) ? 1 : -1;

        // If they are equal, return 0
        return 0;
    }

    /**
     * Negate this precise number
     * @return The negated number
     */
    public PreciseNumber negate() {
        return new PreciseNumber(digits, exponent, !sign);
    }

    /**
     * Return the addition result of this {@code PreciseNumber} and another one
     * @param o The other {@code PreciseNumber}
     * @return The result of the addition
     */
    public PreciseNumber add(PreciseNumber o) {

        // If the signs are different, subtract the smaller from the larger
        if (this.getSign() != o.getSign())
            return this.getSign() ? this.subtract(o.negate()) : o.subtract(this.negate());

        // Get the numbers with trailing zeroes
        List<Integer> thisDigits = this.getDigitsTrailing();
        List<Integer> oDigits = o.getDigitsTrailing();

        // Declare the new digits list
        List<Integer> newDigits;

        // Add the smaller number to the larger one
        if (thisDigits.size() >= oDigits.size()) {

            // Assign the new digits list
            newDigits = new ArrayList<>(thisDigits);

            // Add the digits
            for (int e = 0; e < oDigits.size(); e++)
                newDigits.set(e, newDigits.get(e) + oDigits.get(e));
        } else {

            // Assign the new digits list
            newDigits = new ArrayList<>(oDigits);

            // Add the digits
            for (int e = 0; e < thisDigits.size(); e++)
                newDigits.set(e, newDigits.get(e) + thisDigits.get(e));
        }

        // Carry values larger than 10
        for (int e = 0; e < newDigits.size(); e++) {
            if (newDigits.get(e) >= 10) {
                newDigits.set(e, newDigits.get(e) % 10);
                if (e == newDigits.size() - 1)
                    newDigits.add(1);
                else
                    newDigits.set(e + 1, newDigits.get(e + 1) + 1);
            }
        }

        // Return the new precise number (constructor handles exponent)
        return new PreciseNumber(newDigits, sign);
    }

    public PreciseNumber subtract(PreciseNumber o) {

        // If other is negative, negate other and add instead
        if (!o.getSign())
            return this.add(o.negate());

        // If this is negative, negate the result of other add this
        if (!this.getSign())
            return o.add(this.negate()).negate();

        // If other is greater than this, negate the result of this subtract other
        if (this.compareTo(o) < 0)
            return o.subtract(this).negate();

        // Get the numbers with trailing zeroes
        List<Integer> thisDigits = this.getDigitsTrailing();
        List<Integer> oDigits = o.getDigitsTrailing();

        // Declare the new digits list
        List<Integer> newDigits;

        // Subtract the smaller number from the larger one
        if (thisDigits.size() >= oDigits.size()) {

            // Assign the new digits list
            newDigits = new ArrayList<>(thisDigits);

            // Add the digits
            for (int e = 0; e < oDigits.size(); e++)
                newDigits.set(e, newDigits.get(e) - oDigits.get(e));
        } else {

            // Assign the new digits list
            newDigits = new ArrayList<>(oDigits);

            // Add the digits
            for (int e = 0; e < thisDigits.size(); e++)
                newDigits.set(e, newDigits.get(e) - thisDigits.get(e));
        }

        // Borrow for values smaller than 0
        for (int e = 0; e < newDigits.size(); e++) {
            if (newDigits.get(e) < 0) {
                newDigits.set(e, newDigits.get(e) + 10);
                newDigits.set(e + 1, newDigits.get(e + 1) - 1);
            }
        }

        // Return the new precise number (constructor handles exponent)
        return new PreciseNumber(newDigits, true);
    }
}
