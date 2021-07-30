package Number

import MathObject.Ring
import MRV.MRV
import Matrix.Matrix

class Dec_Number : Ring {
    var value : Double = 0.0
    constructor(_value : Double){
        value = _value
    }

    override fun plus(right: Ring): Ring {
        if (right is Dec_Number){
            return createNumber(value+right.value)
        }
        else{
            throw MRV.NON_COMPLIANCE_TYPES()
        }
    }

    override fun minus(right: Ring): Ring {
        if (right is Dec_Number){
            return createNumber(value-right.value)
        }
        else{
            throw MRV.NON_COMPLIANCE_TYPES()
        }
    }

    override fun times(right: Ring): Ring {
        if (right is Dec_Number){
            return createNumber(value*right.value)
        }
        else if (right is Matrix){
            return right*this
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }
    override fun div(right: Ring): Ring {
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

    override fun unaryMinus() : Ring {
        return Dec_Number(-value)
    }

    override fun toString(): String {
        return value.toString()
    }
}