package Lexemes

open class Operator {
    protected var id: Id_lexemes = Id_lexemes.NULL;
    protected var func: Function = F_Sin()
    protected var left_argue = 0
    protected var right_argue = 0
    protected var priority = 0
    protected var decode_base = ArrayList<String>()
    fun get_id(): Int {
        return Id_lexemes.getId(id!!)
    }

    fun get_left_argue(): Int {
        return left_argue
    }

    fun get_right_argue(): Int {
        return right_argue
    }

    fun is_it(input: String): Int {
        var max_code = 0
        for (i in decode_base.indices) {
            if (decode_base[i].length == input.length && decode_base[i] == input) return 2 else if (decode_base[i].length >= input.length && decode_base[i].startsWith(
                    input
                )
            ) {
                max_code = 1
            }
        }
        return max_code
    }

    fun get_priority(): Int {
        return priority
    }

    fun count(args: ArrayList<Double>): Double {
        return func.count(args)
    }

    fun check(args: ArrayList<Double>): Boolean {
        return func.check(args)
    }

    //id, количество аргументов слева, количество аргументов справа, приоритет, количество кодовых слов, кодовые слова
    internal constructor(
        func: Function,
        id: Id_lexemes,
        left_argue: Int,
        right_argue: Int,
        priority: Int,
        list_of_words: ArrayList<String>
    ) {
        this.id = id
        this.left_argue = left_argue
        this.right_argue = right_argue
        this.priority = priority
        for (i in list_of_words.indices) {
            decode_base.add(list_of_words[i])
        }
    }

    internal constructor() {}

    fun code(): String {
        return decode_base[0]
    } //    protected void load_decode_base(String src){
    //        if (src.equals ( "" )) return;
    //        else {
    //            try {
    //                FileReader localisation_file = new FileReader (src);
    //                BufferedReader scan = new BufferedReader ( localisation_file );
    //                String line;
    //                while ((line = scan.readLine ()) != null){
    //                    if (line.equals ("")!=true) decode_base.add ( line );
    //                }
    //
    //            } catch (FileNotFoundException e) {
    //                e.printStackTrace ();
    //            } catch (IOException e) {
    //                e.printStackTrace ();
    //            }
    //        }
    //
    //    }
}

internal class Argument : Operator() {
    init {
        id = Id_lexemes.ARGUMENT
    }
}

internal class Variable(decode_base: ArrayList<String>) : Operator() {
    init {
        id = Id_lexemes.VARIABLE
        this.decode_base = decode_base
    }
}

internal class Left_br : Operator() {
    init {
        id = Id_lexemes.LEFT_BR
        decode_base.add("(")
    }
}

internal class Right_br : Operator() {
    init {
        id = Id_lexemes.RIGHT_BR
        decode_base.add(")")
    }
}

internal class Comma : Operator() {
    init {
        id = Id_lexemes.COMMA
        decode_base.add(",")
    }
}

internal class Abs : Operator() {
    init {
        decode_base.add("abs")
        id = Id_lexemes.ABS
        left_argue = 0
        right_argue = 1
        func = F_Abs()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Sin : Operator() {
    init {
        decode_base.add("sin")
        id = Id_lexemes.SIN
        left_argue = 0
        right_argue = 1
        func = F_Sin()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Cos : Operator() {
    init {
        decode_base.add("cos")
        id = Id_lexemes.COS
        left_argue = 0
        right_argue = 1
        func = F_Cos()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Tg : Operator() {
    init {
        decode_base.add("tg")
        decode_base.add("tan")
        id = Id_lexemes.TG
        left_argue = 0
        right_argue = 1
        func = F_Tg()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Ctg : Operator() {
    init {
        decode_base.add("ctg")
        decode_base.add("cot")
        decode_base.add("cotan")
        id = Id_lexemes.CTG
        left_argue = 0
        right_argue = 1
        func = F_Ctg()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Arcsin : Operator() {
    init {
        decode_base.add("arcsin")
        decode_base.add("asin")
        id = Id_lexemes.ARCSIN
        left_argue = 0
        right_argue = 1
        func = F_Arcsin()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Arccos : Operator() {
    init {
        decode_base.add("arccos")
        decode_base.add("acos")
        id = Id_lexemes.ARCCOS
        left_argue = 0
        right_argue = 1
        func = F_Arccos()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Arctg : Operator() {
    init {
        decode_base.add("arctg")
        decode_base.add("arctan")
        decode_base.add("atg")
        decode_base.add("atan")
        id = Id_lexemes.ARCTG
        left_argue = 0
        right_argue = 1
        func = F_Arctg()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Arcctg : Operator() {
    init {
        decode_base.add("arcctg")
        decode_base.add("arccot")
        decode_base.add("arccotan")
        decode_base.add("actg")
        decode_base.add("acot")
        decode_base.add("acotan")
        id = Id_lexemes.ARCCTG
        left_argue = 0
        right_argue = 1
        func = F_Arcctg()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Exp : Operator() {
    init {
        decode_base.add("exp")
        id = Id_lexemes.EXP
        left_argue = 0
        right_argue = 1
        func = F_Exp()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Ln : Operator() {
    init {
        decode_base.add("ln")
        id = Id_lexemes.LN
        left_argue = 0
        right_argue = 1
        func = F_Ln()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Log : Operator() {
    init {
        decode_base.add("log")
        id = Id_lexemes.LOG
        left_argue = 0
        right_argue = 2
        func = F_Log()
        priority = Priority_operators.getId(Priority_operators.PRIOR_FUNC)
    }
}

internal class Pow : Operator() {
    init {
        decode_base.add("^")
        id = Id_lexemes.POW
        left_argue = 1
        right_argue = 1
        func = F_Pow()
        priority = Priority_operators.getId(Priority_operators.PRIOR_POW)
    }
}

internal class Mult : Operator() {
    init {
        decode_base.add("*")
        decode_base.add("×")
        decode_base.add("⋅")
        decode_base.add("∙")
        decode_base.add("·")
        id = Id_lexemes.MULT
        left_argue = 1
        right_argue = 1
        func = F_Mult()
        priority = Priority_operators.getId(Priority_operators.PRIOR_MULT_DIV)
    }
}

internal class Div : Operator() {
    init {
        decode_base.add("/")
        decode_base.add("÷")
        decode_base.add("∶")
        id = Id_lexemes.DIV
        left_argue = 1
        right_argue = 1
        func = F_Div()
        priority = Priority_operators.getId(Priority_operators.PRIOR_MULT_DIV)
    }
}

internal class Plus : Operator() {
    init {
        decode_base.add("+")
        id = Id_lexemes.PLUS
        left_argue = 1
        right_argue = 1
        func = F_Plus()
        priority = Priority_operators.getId(Priority_operators.PRIOR_PLUS_MINUS)
    }
}

internal class Minus : Operator() {
    init {
        decode_base.add("—")
        decode_base.add("–")
        decode_base.add("−")
        decode_base.add("-")
        id = Id_lexemes.MINUS
        left_argue = 1
        right_argue = 1
        func = F_Minus()
        priority = Priority_operators.getId(Priority_operators.PRIOR_PLUS_MINUS)
    }
}