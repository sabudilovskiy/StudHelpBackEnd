package Point

import MathObject.MathObject.MathObject
import Support.create_array_with_n_elements

class Point : MathObject{
    var coords = ArrayList<Double>(0)
    var n = 0

    internal constructor(n: Int) {
        coords = create_array_with_n_elements(0, n)
        this.n = n
    }

    internal constructor(arr: ArrayList<Double>) {
        coords = arr
        n = arr.size
    }

    fun getCoord(i: Int): Double {
        return if (0 <= i && i < n) coords[i] else throw IllegalArgumentException()
    }
    override fun decode_this(): String {
        var temp : String = "("
        for (i in coords){
            temp += "$i , "
        }
        temp = temp.substring(0, temp.length-3)
        return temp
    }
}