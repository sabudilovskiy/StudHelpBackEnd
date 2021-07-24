package Lexemes

enum class Priority_operators {
    END, PRIOR_BOOL_FUNC, PRIOR_PLUS_MINUS, PRIOR_MULT_DIV, PRIOR_POW, PRIOR_FUNC;

    companion object {
        fun getId(priority: Priority_operators): Int {
            for (i in values().indices) {
                if (priority == values()[i]) return i
            }
            throw NumberFormatException("No such element in enum")
        }

        fun get_by_id(id: Int): Priority_operators {
            return values()[id]
        }
    }
}