package FractionalNumber

import CanBeInMatrix.CanBeInMatrix
import MRV.MRV
import Support.find_GCD
import java.lang.Math.abs


class FractionalNumber : CanBeInMatrix{
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
    constructor(number : Double){
        var temp : Double = number
        while (abs(temp) < 1) {
            temp*=10
            denominator*=10
        }
        numerator = temp.toInt()
    }
    override fun plus(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber) {
            val left: FractionalNumber = this
            var GCD: Int = find_GCD(left.denominator, right.denominator)
            val new_numerator: Int = left.numerator * GCD / denominator + right.numerator * GCD / right.denominator
            return FractionalNumber(new_numerator, GCD)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override fun minus(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber){
            val left : FractionalNumber = this
            var GCD : Int = find_GCD(left.denominator, right.denominator)
            val new_numerator : Int = left.numerator * GCD / denominator - right.numerator * GCD / right.denominator
            return FractionalNumber(new_numerator, GCD)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override fun times(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber){
            val left : FractionalNumber = this
            val new_numerator = left.numerator*right.numerator
            val new_denominator = left.denominator*right.denominator
            return FractionalNumber(new_numerator, new_denominator)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    fun div(b : FractionalNumber) : FractionalNumber{
        val left : FractionalNumber = this
        val right : FractionalNumber = b
        val new_numerator = left.numerator*right.denominator
        val new_denominator = left.denominator*right.numerator
        return FractionalNumber(new_numerator, new_denominator)
    }
    override fun decode_this(): String {
        if (denominator != 1) return " " + numerator + "/" + denominator + " "
        else return " " + numerator + " "
    }
}