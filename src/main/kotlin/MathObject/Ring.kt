package MathObject

import MathObject.MathObject.MathObject

abstract class Ring : MathObject() {
    abstract operator fun plus(right : Ring) : Ring
    abstract operator fun minus(right : Ring) : Ring
    abstract operator fun times(right : Ring) : Ring
    abstract operator fun div(right: Ring): Ring
    abstract override fun equals(other: Any?): Boolean
    abstract operator fun unaryMinus() : Ring
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}