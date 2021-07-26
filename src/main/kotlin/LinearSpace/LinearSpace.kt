package LinearSpace

import MRV.MRV
import MathObject.MathObject.MathObject
import Matrix.Matrix

class LinearSpace : MathObject{
    lateinit var base : ArrayList<Matrix>
    constructor (arr: ArrayList <Matrix>){
        var m : Int = arr[0].m;
        for (matrix in arr){
            if (matrix.m != m || matrix.n != 1) throw MRV.MATRIX_ERROR()
        }
        base = arr;
    }
    override fun toString(): String {
        var temp : String = ""
        for (vector in base){
            temp += vector.toString()+"\n"
        }
        return temp
    }
}