package Point

import MathObject.Ring
import MathObject.MathObject.MathObject
import Number.createNumber
import Support.createSingleArrayList

class Point : MathObject{
    var coords = ArrayList<Ring>(0)
    var n = 0

    internal constructor(n: Int) {
        coords = createSingleArrayList(createNumber(0.0), n)
        this.n = n
    }

    constructor(arr: MutableList<Double>) {
        coords = createSingleArrayList(createNumber(0.0),arr.size)
        for (i in 0 until arr.size) coords[i] = createNumber(arr[i])
    }
    constructor(_arr: ArrayList<Ring>){
        coords = _arr
    }
    fun getCoord(i: Int): Ring {
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