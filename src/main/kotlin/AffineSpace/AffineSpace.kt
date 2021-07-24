package AffineSpace

import MRV.MRV
import MathObject.MathObject.MathObject
import Matrix.Matrix

class AffineSpace(_v0: Matrix, arr: ArrayList<Matrix>) : MathObject() {
    var v0 : Matrix = _v0
    var base : ArrayList<Matrix> = arr
    init {
        var m : Int = v0.m
        for (vector in arr){
            if (vector.m != m || vector.n != 1) throw MRV.MATRIX_ERROR()
        }
    }
    override fun decode_this(): String {
        var temp : String = ""
        temp+=v0.decode_this()
        for (vector in base) {
            temp+=vector.decode_this()+"\n"
        }
        return temp
    }
}