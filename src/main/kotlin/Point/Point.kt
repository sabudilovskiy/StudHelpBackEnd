package Point

import MathObject.Division_Ring
import MathObject.MathObject.MathObject
import Number.createNumber
import Support.newSingleArrayList

class Point : MathObject{
    var coords = ArrayList<Division_Ring>(0)
    var n = 0

    internal constructor(n: Int) {
        coords = newSingleArrayList(createNumber(0.0), n)
        this.n = n
    }

    constructor(arr: MutableList<Double>) {
        coords = newSingleArrayList(createNumber(0.0),arr.size)
        for (i in 0 until arr.size) coords[i] = createNumber(arr[i])
    }
    constructor(_arr: ArrayList<Division_Ring>){
        coords = _arr
    }
    fun getCoord(i: Int): Division_Ring {
        return if (0 <= i && i < n) coords[i] else throw IllegalArgumentException()
    }
    override fun toString(): String {
        var temp : String = "("
        for (i in coords){
            temp += "$i , "
        }
        temp = temp.substring(0, temp.length-3)
        return temp
    }
}