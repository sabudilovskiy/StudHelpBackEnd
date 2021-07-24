package MathObject.MathObject

import Logger.Log.add

abstract class MathObject {
    public abstract fun decode_this(): String
    fun log_this(commit: String) {
        add(decode_this(), commit)
    }
}