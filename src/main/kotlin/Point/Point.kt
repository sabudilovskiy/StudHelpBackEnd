package Point

import CanBeInMatrix.CanBeInMatrix
import MathObject.MathObject.MathObject
import Number.newNumber
import Support.newSingleArrayList

class Point : MathObject{
    var coords = ArrayList<CanBeInMatrix>(0)
    var n = 0

    internal constructor(n: Int) {
        coords = newSingleArrayList(newNumber(0.0), n)
        this.n = n
    }

    constructor(arr: MutableList<Double>) {
        coords = newSingleArrayList(newNumber(0.0),arr.size)
        for (i in 0 until arr.size) coords[i] = newNumber(arr[i])
    }
    constructor(_arr: ArrayList<CanBeInMatrix>){
        coords = _arr
    }
    fun getCoord(i: Int): CanBeInMatrix {
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