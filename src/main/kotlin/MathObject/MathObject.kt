package MathObject.MathObject

import Logger.Log.add

abstract class MathObject {
    public abstract override fun toString(): String
    constructor()
    fun log_this(commit: String) {
        add(toString(), commit)
    }
}