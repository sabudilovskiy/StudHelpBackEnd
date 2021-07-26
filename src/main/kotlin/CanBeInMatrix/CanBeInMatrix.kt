package CanBeInMatrix

import MathObject.MathObject.MathObject

abstract class CanBeInMatrix : MathObject() {
    abstract fun plus(right : CanBeInMatrix) : CanBeInMatrix
    abstract fun minus(right : CanBeInMatrix) : CanBeInMatrix
    abstract fun times(right : CanBeInMatrix) : CanBeInMatrix
}