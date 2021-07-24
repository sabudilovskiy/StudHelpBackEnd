package Lexemes

enum class Id_lexemes {
    //пре-унарные операторы, высший приоритет
    ARGUMENT, VARIABLE, LEFT_BR, RIGHT_BR, COMMA, ABS, SIN, COS, TG, CTG, ARCSIN, ARCCOS, ARCTG, ARCCTG, EXP, LN,  //псевдо пре-унарный оператор
    LOG,  //пост-унарные операторы, приоритет: 2
    FACTORIAL,  //бинарные операторы, приоритет: 3
    POW,  //бинарные операторы, приоритет: 4
    MULT, DIV,  //бинарные операторы, низший приоритет
    PLUS, MINUS,  //требуется для определения числа операторов
    NUMBER_OPERATORS,  //технические вещи
    END, NULL;

    companion object {
        fun getId(lexeme: Id_lexemes): Int {
            for (i in values().indices) {
                if (lexeme == values()[i]) return i
            }
            throw NumberFormatException("No such element in enum")
        }

        fun get_by_id(id: Int): Id_lexemes {
            return values()[id]
        }
    }
}