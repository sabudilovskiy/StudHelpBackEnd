package MathObject

import MathObject.MathObject.MathObject

abstract class Division_Ring : MathObject() {
    abstract operator fun plus(right : Division_Ring) : Division_Ring
    abstract operator fun minus(right : Division_Ring) : Division_Ring
    abstract operator fun times(right : Division_Ring) : Division_Ring
    abstract operator fun div(right: Division_Ring): Division_Ring
    abstract override fun equals(other: Any?): Boolean
    abstract operator fun unaryMinus() : Division_Ring
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}