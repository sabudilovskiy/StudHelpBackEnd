package Not_Used

import MathObject.Ring
import Number.FractionalNumber
import java.lang.Math.pow

class Not_Used_DecNumber() {
    var main : Double = 0.0
    var period : Double = 0.0
    var chars = 0
    constructor(number : Double): this(){
        main = number 
    }
    constructor(number : FractionalNumber) : this(number.numerator, number.denominator){
    }
    constructor(a: Long, b : Long): this(){
        var remainders : ArrayList <Long> = arrayListOf()
        var remainder = a
        var count_after_comma = 0
        while (remainder != 0L && remainders.indexOf(remainder) == -1)
        {
            remainders.add(remainder)
            var c : Long = 0
            while (c*b <= remainder) c++
            c--;
            if (count_after_comma == 0){
                main = main*10 + c
            }
            else{
                main += c / pow(10.0, count_after_comma.toDouble())
            }
            remainder-= c*b
            if (remainder<b && remainder != 0L) {
                count_after_comma++
                remainder*=10
            }
        }
        count_after_comma--
        val length_period = remainders.size - remainders.indexOf(remainder)
        var temp : Double = (main * pow(10.0, count_after_comma.toDouble())).toDouble()
        temp = (temp%pow(10.0,count_after_comma.toDouble())).toDouble()
        period = temp.toDouble()
    }
     fun plus(right: Ring): Ring {
        TODO("Not yet implemented")
    }

     fun minus(right: Ring): Ring {
        TODO("Not yet implemented")
    }

     fun times(right: Ring): Ring {
        TODO("Not yet implemented")
    }

     fun times(right: Double): Ring {
        TODO("Not yet implemented")
    }

     fun decode_this(): String {
        TODO("Not yet implemented")
    }

}