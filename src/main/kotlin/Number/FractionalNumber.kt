package Number

import CanBeInMatrix.CanBeInMatrix
import MRV.MRV
import Support.find_GCD
import java.lang.Math.abs


class FractionalNumber : CanBeInMatrix{
    var numerator : Long = 1;
    var denominator : Long = 1;
    constructor(value : Long){
        numerator = value;
    }
    constructor(_numerator : Long, _denominator : Long){
        val GCD : Long = find_GCD(_numerator, _denominator)
        numerator = _numerator/GCD;
        denominator = _denominator/GCD;
        if (denominator < 0) {
            numerator = -numerator
            denominator = -denominator
        }
    }
    constructor(number : Double){
        if (number != 0.0)
        {
            var temp : Double = number
            while (abs(temp) < 1) {
                temp*=10
                denominator*=10
            }
            numerator = temp.toLong()
        }
        else numerator = 0L

    }
    override operator fun plus(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber) {
            val left: FractionalNumber = this
            var GCD: Long = find_GCD(left.denominator, right.denominator)
            val new_numerator: Long = left.numerator * GCD / denominator + right.numerator * GCD / right.denominator
            return FractionalNumber(new_numerator, GCD)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override operator fun minus(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber){
            val left : FractionalNumber = this
            var GCD : Long = find_GCD(left.denominator, right.denominator)
            val new_numerator : Long = left.numerator * GCD / denominator - right.numerator * GCD / right.denominator
            return FractionalNumber(new_numerator, GCD)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override operator fun times(right : CanBeInMatrix) : CanBeInMatrix{
        if (right is FractionalNumber){
            val left : FractionalNumber = this
            var left_numerator = left.numerator
            var left_denominator = left.denominator
            var right_numerator = right.numerator
            var right_denominator = right.denominator
            var GCD = find_GCD(left_numerator, right_denominator)
            if (GCD!=1L){
                left_numerator/=GCD
                right_denominator/=GCD
            }
            GCD = find_GCD(right_numerator, left_denominator)
            if (GCD!=1L){
                left_denominator/=GCD
                right_numerator/=GCD
            }
            val new_numerator = left_numerator*right_numerator
            val new_denominator = left_denominator*right_denominator
            return FractionalNumber(new_numerator, new_denominator)
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override fun div(right: CanBeInMatrix): CanBeInMatrix {
        if (right is FractionalNumber) return this/right
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        else if (other is FractionalNumber) return denominator == other.denominator && numerator == other.numerator
        else if (other is Double) return this == FractionalNumber(other)
        else return false
    }

    override fun unaryMinus() : CanBeInMatrix{
        return FractionalNumber(-numerator, denominator)
    }

    operator fun div(b : FractionalNumber) : FractionalNumber {
        val left : FractionalNumber = this
        val right : FractionalNumber = b
        var left_numerator = left.numerator
        var left_denominator = left.denominator
        var right_numerator = right.numerator
        var right_denominator = right.denominator
        var GCD = find_GCD(left_numerator, right_denominator)
        if (GCD!=1L){
            left_numerator/=GCD
            right_denominator/=GCD
        }
        GCD = find_GCD(left_denominator, right_numerator)
        if (GCD!=1L){
            left_denominator/=GCD
            right_numerator/=GCD
        }
        val new_numerator = left_numerator*right_denominator
        val new_denominator = left.denominator*right.numerator
        return FractionalNumber(new_numerator, new_denominator)
    }
    override fun toString(): String {
        if (!Settings.numbers.use_improrer_fraction){
            val int_value = numerator/denominator
            val true_numerator = numerator%denominator
            if (denominator != 1L) return " $int_value $true_numerator/$denominator"
            else return " " + numerator + " "
        }
        else {
            if (denominator != 1L) return "  $numerator/$denominator"
            else return " " + numerator + " "
        }
    }
}