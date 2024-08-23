Precise number
==============

## What is this?
This is a library to provide a precise number in Java. This addresses the approximation errors of `double` and `float` numbers.

## The class diagram
```mermaid
classDiagram
class Object {
    +equals() boolean
    +toString String
}

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

    -removeTrailing(List~Integer~) List~Integer~
    -carryDigits(List~Integer~) List~Integer~
    #getDigits() List~Integer~
    #getDigitsTrailing() List~Integer~
    #getExponent() int
    #getSign() boolean
    +negate() PreciseNumber
    +add() PreciseNumber
    +subtract() PreciseNumber
}

PreciseNumber --|> Object
PreciseNumber --|> Number
PreciseNumber ..|> Comparable
Number --|> Object
```