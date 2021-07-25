package Fractional_number

import MathObject.MathObject.MathObject
import Support.find_GCD


class Fractional_number : MathObject{
    var numerator : Int = 1;
    var denominator : Int = 1;
    constructor(value : Int){
        numerator = 1;
    }
    constructor(_numerator : Int, _denominator : Int){
        val GCD : Int = find_GCD(_numerator, _denominator)
        numerator = _numerator/GCD;
        denominator = _denominator/GCD;
    }
    fun plus(b : Fractional_number) : Fractional_number{
        val left : Fractional_number = this
        val right : Fractional_number = b
        var GCD : Int = find_GCD(left.denominator, right.denominator)
        val new_numerator : Int = left.numerator * GCD / denominator + right.numerator * GCD / right.denominator
        return Fractional_number(new_numerator, GCD)
    }
    fun minus(b : Fractional_number) : Fractional_number{
        val left : Fractional_number = this
        val right : Fractional_number = b
        var GCD : Int = find_GCD(left.denominator, right.denominator)
        val new_numerator : Int = left.numerator * GCD / denominator - right.numerator * GCD / right.denominator
        return Fractional_number(new_numerator, GCD)
    }
    fun mult(b : Fractional_number) : Fractional_number{
        val left : Fractional_number = this
        val right : Fractional_number = b
        val new_numerator = left.numerator*right.numerator
        val new_denominator = left.denominator*right.denominator
        return Fractional_number(new_numerator, new_denominator)
    }
    fun div(b : Fractional_number) : Fractional_number{
        val left : Fractional_number = this
        val right : Fractional_number = b
        val new_numerator = left.numerator*right.denominator
        val new_denominator = left.denominator*right.numerator
        return Fractional_number(new_numerator, new_denominator)
    }
    override fun decode_this(): String {
        if (denominator != 1) return " " + numerator + "/" + denominator + " "
        else return " " + numerator + " "
    }
}