package Lexemes

//token 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
class Archieve(variables: ArrayList<String>) {
    fun add_operator(A: Operator) {
        val n: Int = A.get_id()
        base[n] = A
    }

    companion object {
        var base: ArrayList<Operator> = ArrayList<Operator>()
        fun decode(input: String, verif: ArrayList<Int>?): ArrayList<Int> {
            val answer = ArrayList<Int>()
            if (verif == null || verif.size == 0) {
                for (i in 0 until Id_lexemes.getId(Id_lexemes.NUMBER_OPERATORS)) {
                    val check: Int = base[i].is_it(input)
                    if (check == 2) {
                        answer.add(i)
                    } else if (check == 1) {
                        answer.add(-i)
                    }
                }
            } else {
                for (i in verif.indices) {
                    val b = Math.abs(verif[i])
                    val check: Int = base[b].is_it(input)
                    if (check == 2) {
                        answer.add(b)
                    } else if (check == 1) {
                        answer.add(-b)
                    }
                }
            }
            return answer
        }

        fun code(id: Id_lexemes): String {
            return base[Id_lexemes.getId(id)].code()
        }

        fun get_priority(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_priority() else 0
        }

        fun get_left_argue(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_left_argue() else 0
        }

        fun get_right_argue(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_right_argue() else 0
        }

        fun check_countable(id: Id_lexemes, argues: ArrayList<Double>): Boolean {
            return base[Id_lexemes.getId(id)].check(argues)
        }

        fun count(id: Id_lexemes, argues: ArrayList<Double>): Double {
            return base[Id_lexemes.getId(id)].count(argues)
        }
    }

    init {
        val n: Int = Id_lexemes.getId(Id_lexemes.NUMBER_OPERATORS)
        while (base.size < n) {
            base.add(Sin())
        }
        add_operator(Argument())
        add_operator(Variable(variables))
        add_operator(Left_br())
        add_operator(Right_br())
        add_operator(Comma())
        add_operator(Abs())
        add_operator(Sin())
        add_operator(Cos())
        add_operator(Tg())
        add_operator(Ctg())
        add_operator(Arcsin())
        add_operator(Arccos())
        add_operator(Arctg())
        add_operator(Arcctg())
        add_operator(Exp())
        add_operator(Ln())
        add_operator(Log())
        add_operator(Pow())
        add_operator(Mult())
        add_operator(Div())
        add_operator(Plus())
        add_operator(Minus())
    }
}