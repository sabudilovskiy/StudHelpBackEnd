package Number

import MathObject.Division_Ring
import MRV.MRV
import Matrix.Matrix

class Dec_Number : Division_Ring {
    var value : Double = 0.0
    constructor(_value : Double){
        value = _value
    }

    override fun plus(right: Division_Ring): Division_Ring {
        if (right is Dec_Number){
            return createNumber(value+right.value)
        }
        else{
            throw MRV.NON_COMPLIANCE_TYPES()
        }
    }

    override fun minus(right: Division_Ring): Division_Ring {
        if (right is Dec_Number){
            return createNumber(value-right.value)
        }
        else{
            throw MRV.NON_COMPLIANCE_TYPES()
        }
    }

    override fun times(right: Division_Ring): Division_Ring {
        if (right is Dec_Number){
            return createNumber(value*right.value)
        }
        else if (right is Matrix){
            return right*this
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override fun div(right: Division_Ring): Division_Ring {
        if (right is Dec_Number){
            return createNumber(value/right.value)
        }
        else if (right is Matrix){
            return right*this
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        else if (other is Dec_Number) return value == other.value
        else if (other is Double) return value == other
        else return false
    }

    override fun unaryMinus() : Division_Ring {
        return Dec_Number(-value)
    }

    override fun toString(): String {
        return value.toString()
    }
}