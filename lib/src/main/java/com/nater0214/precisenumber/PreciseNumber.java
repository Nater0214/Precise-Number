package com.nater0214.precisenumber;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;

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
     * The digits of the number such that the first number is the lowest power of 10
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
        }

        // Return the values
        return new SimpleImmutableEntry<>(newDigits, exponent);
    }

    /**
     * Carry the digits passed in so that all digits are in the range [0, 9]
     * @param digits The digits to perform the operation on
     * @return The carried digits
     * @apiNote This method does not change the exponent or sign
     */
    private static List<Integer> carryDigits(List<Integer> digits) {

        // Create digit lists
        List<Integer> newDigits = new ArrayList<>(digits);
        List<Integer> prevDigits = new ArrayList<>();

        // Loop carrying
        while (!newDigits.equals(prevDigits)) {
            prevDigits = new ArrayList<>(newDigits);
            for (int e = newDigits.size() - 1; e >= 0; e--) {
                if (newDigits.get(e) >= 10) {
                    if (e == 0) {
                        int digit = newDigits.get(0);
                        newDigits.set(0, digit % 10);
                        newDigits.add(0, digit / 10);
                    } else {
                        int digit = newDigits.get(e);
                        newDigits.set(e, digit % 10);
                        newDigits.set(e - 1, newDigits.get(e - 1) + digit / 10);
                    }
                } else if (newDigits.get(e) < 0) {
                    if (e == 0) {
                        int digit = newDigits.get(0);
                        newDigits.set(0, 10 + digit % 10);
                        newDigits.add(0, -(digit / -10));
                    } else {
                        int digit = newDigits.get(e);
                        newDigits.set(e, 10 + digit % 10);
                        newDigits.set(e - 1, newDigits.get(e - 1) - (digit / -10 + 1));
                    }
                }
            }
        }

        // Remove leading zeros
        while (newDigits.size() > 1 && newDigits.get(0) == 0)
            newDigits.remove(0);

        // Return new digits
        return newDigits;
    }

    /****************
     * Constructors *
     ****************/
    
    /**
     * Create a new precise number from an {@code int} value
     * @param value The {@code int} value to create from
     */
    public PreciseNumber(int value) {
        this(String.valueOf(value));
    }

    /**
     * Create a new precise number from a {@code long} value
     * @param value The {@code long} value to create from
     */
    public PreciseNumber(long value) {
        this(String.valueOf(value));
    }

    /**
     * Create a new precise number from a {@code double} value with a default round digit of 15
     * @param value The {@code double} value to create from
     * @see #PreciseNumber(double, int)
     */
    public PreciseNumber(double value) {
        this(String.valueOf(value));
    }

    /**
     * Create a new precise number from a {@code float} value with a default round digit of 7
     * @param value The {@code float} value to create from
     * @see #PreciseNumber(float, int)
     */
    public PreciseNumber(float value) {
        this(String.valueOf(value));
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

        // Get the exponent of the number
        int exponent = 0;
        if (value.contains("E")) {
            String[] split = value.split("E");
            value = split[0];
            exponent = Integer.parseInt(split[1]);
        } else if (value.contains("e")) {
            String[] split = value.split("e");
            value = split[0];
            exponent = Integer.parseInt(split[1]);
        }
        if (value.contains(".")) {
            String[] split = value.split("\\.");
            value = split[0] + split[1];
            exponent -= split[1].length();
        }

        // Loop adding digits
        try {
            for (int e = 0; e < value.length(); e++) {
                int digit = Integer.parseInt(value.substring(e, e + 1));
                digits.add(digit);
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("The number was unable to parse!");
        }

        // Remove leading zeros
        while (digits.get(0) == 0) {
            digits.remove(0);
            if (digits.size() == 0) {
                break;
            }
        }

        // Remove trailing zeroes and modify exponent
        SimpleImmutableEntry<List<Integer>, Integer> result = removeTrailing(digits);
        digits = result.getKey();
        exponent += result.getValue();

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
        out.addAll(this.getDigits());
        for (int e = this.getExponent(); e > 0; e--) {
            out.add(0);
        }
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
        if (this.compareTo(new PreciseNumber(Long.MAX_VALUE)) > 0)
            return Long.MAX_VALUE;
        else
            return Long.parseLong(this.toString());
    }

    /**
     * Get this number as an {@code int}; if the number is greater than {@code Integer.MAX_VALUE}, that value is returned
     * @return This number as an {@code int}
     */
    @Override
    public int intValue() {
        if (this.compareTo(new PreciseNumber(Integer.MAX_VALUE)) > 0)
            return Integer.MAX_VALUE;
        else
            return Integer.parseInt(this.toString());
    }

    /**
     * Get this number as a {@code double}
     * @return This number as a {@code double}
     */
    @Override
    public double doubleValue() {
        if (this.compareTo(new PreciseNumber(Double.MAX_VALUE)) > 0)
            return Double.MAX_VALUE;
        else
            return Double.parseDouble(this.toString());
    }

    /**
     * Get this number as a {@code float}
     * @return This number as a {@code float}
     */
    @Override
    public float floatValue() {
        if (this.compareTo(new PreciseNumber(Float.MAX_VALUE)) > 0)
            return Float.MAX_VALUE;
        else
            return Float.parseFloat(this.toString());
    }

    @Override
    public String toString() {

        // Create the string builder
        StringBuilder builder = new StringBuilder();

        // Add the sign
        builder.append(sign ? "" : "-");

        // Add the digits
        for (int e = 0; e < this.getDigits().size(); e++) {
            builder.append(this.getDigits().get(e));
        }

        // Add the exponent zeros
        for (int e = 0; e < this.getExponent(); e++) {
            builder.append("0");
        }

        // Add 0 if empty
        if (builder.length() == 0)
            builder.append("0");

        // Return the string
        return builder.toString();
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
     * @return If the other object is a {@code PreciseNumber} and is equal to this one
     * @param obj The other object to compare to
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PreciseNumber && this.compareTo((PreciseNumber) obj) == 0;
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
                newDigits.set(newDigits.size() - 1 - e, newDigits.get(newDigits.size() - 1 - e) + oDigits.get(oDigits.size() - 1 - e));
        } else {

            // Assign the new digits list
            newDigits = new ArrayList<>(oDigits);

            // Add the digits
            for (int e = 0; e < thisDigits.size(); e++)
                newDigits.set(newDigits.size() - 1 - e, newDigits.get(newDigits.size() - 1 - e) + thisDigits.get(thisDigits.size() - 1 - e));
        }

        // Carry digits
        newDigits = carryDigits(newDigits);

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
                newDigits.set(newDigits.size() - 1 - e, newDigits.get(newDigits.size() - 1 - e) - oDigits.get(oDigits.size() - 1 - e));
        } else {

            // Assign the new digits list
            newDigits = new ArrayList<>(oDigits);

            // Add the digits
            for (int e = 0; e < thisDigits.size(); e++)
                newDigits.set(newDigits.size() - 1 - e, newDigits.get(newDigits.size() - 1 - e) - thisDigits.get(thisDigits.size() - 1 - e));
        }

        // Carry digits
        newDigits = carryDigits(newDigits);

        // Return the new precise number (constructor handles exponent)
        return new PreciseNumber(newDigits, true);
    }
}
