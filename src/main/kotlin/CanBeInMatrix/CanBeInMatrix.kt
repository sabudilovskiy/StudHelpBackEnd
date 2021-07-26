package CanBeInMatrix

import MathObject.MathObject.MathObject

abstract class CanBeInMatrix : MathObject() {
    abstract operator fun plus(right : CanBeInMatrix) : CanBeInMatrix
    abstract operator fun minus(right : CanBeInMatrix) : CanBeInMatrix
    abstract operator fun times(right : CanBeInMatrix) : CanBeInMatrix
    abstract operator fun div(right: CanBeInMatrix): CanBeInMatrix
    abstract override fun equals(other: Any?): Boolean
    abstract operator fun unaryMinus() : CanBeInMatrix
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}