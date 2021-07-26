package DecNumber

import CanBeInMatrix.CanBeInMatrix
import FractionalNumber.FractionalNumber
import java.lang.Math.pow

class DecNumber() : CanBeInMatrix() {
    var main : Double = 0.0
    var period : Double? = null
    constructor(number : Double): this(){
        main = number 
    }
    constructor(number : FractionalNumber) : this(number.numerator, number.denominator){
    }
    constructor(a: Int, b : Int): this(){
        var remainders : ArrayList <Int> = arrayListOf()
        var remainder = a
        var count_after_comma = 0
        while (remainder != 0 && remainders.indexOf(remainder) == -1)
        {
            var c : Int = 0
            while (c*b <= remainder) c++
            if (count_after_comma == 0){
                main = main*10 + c*b
            }
            else{
                main += c * b * pow(10.0, count_after_comma.toDouble())
            }
            remainder-= c*b
            if (remainder<b && remainder != 0) {
                count_after_comma++
                remainder*=10
            }
            remainders.add(remainder)
        }

    }
    override fun plus(right: CanBeInMatrix): CanBeInMatrix {
        TODO("Not yet implemented")
    }

    override fun minus(right: CanBeInMatrix): CanBeInMatrix {
        TODO("Not yet implemented")
    }

    override fun times(right: CanBeInMatrix): CanBeInMatrix {
        TODO("Not yet implemented")
    }

    override fun decode_this(): String {
        TODO("Not yet implemented")
    }

}