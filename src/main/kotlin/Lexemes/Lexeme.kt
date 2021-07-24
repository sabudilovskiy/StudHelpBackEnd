package Lexemes

class Lexeme {
    var key: String = ""
        protected set
    protected var id: Id_lexemes = Id_lexemes.NULL
    protected var values = ArrayList<Double>()
    var begin = 0
    var end = 0

    constructor() {
        values.add(3.0)
    }

    constructor(id: Id_lexemes, values: ArrayList<Double>) {
        this.values = values
        this.id = id
        key = code()
    }

    constructor(id: Id_lexemes) {
        this.id = id
        key = code()
    }

    constructor(key: String) {
        id = Id_lexemes.VARIABLE
        this.key = key
    }

    fun get_id(): Id_lexemes {
        return id
    }

    fun get_value(i: Int): Double {
        return values[i]
    }

    fun get_value(): Double {
        return values[0]
    }

    fun get_values(): ArrayList<Double> {
        return values
    }

    private fun code(): String {
        var A = ""
        if (id === Id_lexemes.ARGUMENT) {
            if (values.size > 1) {
                A += "("
                A += values[0]
                for (i in 1 until values.size) {
                    A += ","
                    A += java.lang.Double.toString(values[i])
                }
                A += "("
            } else {
                A += java.lang.Double.toString(values[0])
            }
        } else if (id === Id_lexemes.LEFT_BR) A += "(" else if (id === Id_lexemes.RIGHT_BR) A += ")" else if (id === Id_lexemes.VARIABLE) A += "x" else if (id === Id_lexemes.END) ; else {
            A = Archieve.code(id)
        }
        return A
    }
}