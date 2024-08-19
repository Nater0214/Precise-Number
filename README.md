Precise number
==============

## What is this?
This is a library to provide a precise number in Java. This addresses the approximation errors of `double` and `float` numbers.

## The class diagram
```mermaid
classDiagram
class Number {
    +intValue() int
    +longValue() long
    +floatValue() float
    +doubleValue() double
}

class Comparable~T~ {
    +compareTo(T other) int
}

class PreciseNumber {
    -List~Integer~ digits
    -int exponent
    -boolean sign

    #getDigits() List~Integer~
    #getDigitsTrailing() List~Integer~
    #getExponent() int
    #getLength() int
    #getHighestExponent() int
    #getSign() boolean
}

PreciseNumber --|> Number
PreciseNumber ..|> Comparable
```