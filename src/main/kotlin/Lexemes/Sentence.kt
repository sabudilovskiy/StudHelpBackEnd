package Lexemes

import Lexemes.ErrorHandler.getError
import Lexemes.ErrorHandler.setError

class Sentence {
    private var _array : ArrayList<Lexeme> = arrayListOf()

    constructor() {}
    constructor(input: String) {
        var pos = 0
        while (pos < input.length) {
            while (pos < input.length && input[pos] == ' ') pos++
            if (pos < input.length) {
                val begin = pos
                if (Support.is_numeral(input[pos])) {
                    var buffer = ""
                    var use_point = false
                    while (pos < input.length && (input[pos] == '.' || Support.is_numeral(input[pos]))) {
                        if (input[pos] == '.') {
                            if (use_point == false) {
                                use_point = true
                                buffer += input[pos++]
                            } else {
                                setError(Id_errors.ERROR_SIGNS, pos, pos)
                                return
                            }
                        } else {
                            buffer += input[pos++]
                        }
                    }
                    val temp_array = ArrayList<Double>()
                    temp_array.add(buffer.toDouble())
                    val temp = Lexeme(Id_lexemes.ARGUMENT, temp_array)
                    temp.begin = begin
                    temp.end = pos - 1
                    add_lexeme(temp)
                } else {
                    var buffer = String()
                    var a = ArrayList<Int>()
                    a.add(-1)
                    var max_id = -1
                    var pos_max = pos
                    var max_buffer = ""
                    while (a.size > 0 && pos < input.length) {
                        buffer += input[pos++]
                        a = Archieve.decode(buffer, null)
                        for (i in a.indices) {
                            if (a[i] > 0) {
                                max_buffer = buffer
                                max_id = a[i]
                                pos_max = pos
                                break
                            }
                        }
                    }
                    if (max_id == -1) {
                        val end = pos - 1
                        setError(Id_errors.UNKNOWN_FUNCTION, begin, end)
                        return
                    } else {
                        val id = Id_lexemes.get_by_id(max_id)
                        var temp: Lexeme
                        pos = pos_max
                        temp = if (id !== Id_lexemes.VARIABLE) {
                            Lexeme(id)
                        } else {
                            Lexeme(max_buffer)
                        }
                        temp.begin = begin
                        temp.end = pos_max - 1
                        add_lexeme(temp)
                    }
                }
            }
        }
        var left_brs = 0
        var right_brs = 0
        for (i in _array.indices) {
            if (_array[i].get_id() === Id_lexemes.LEFT_BR) left_brs++ else if (_array[i].get_id() === Id_lexemes.RIGHT_BR) right_brs++
            if (right_brs > left_brs) {
                setError(Id_errors.MORE_RIGHT_BRACKETS, _array[i])
                return
            }
        }
        if (left_brs == right_brs) {
            add_lexeme(Lexeme(Id_lexemes.END))
            return
        } else {
            val a = find_left_br()
            setError(Id_errors.HAVE_OPEN_BRACKETS, a, a)
            return
        }
    }

    internal constructor(array: ArrayList<Lexeme>) {
        for (lexeme in array) add_lexeme(lexeme)
    }

    fun find_left_br(): Int {
        var i = 0
        while (_array[i].get_id() !== Id_lexemes.END) {
            if (_array[i].get_id() === Id_lexemes.LEFT_BR) return i
            i++
        }
        return -1
    }

    //получить номер лексемы-конца
    fun get_end(): Int {
        return _array.size - 1
    }

    //добавить лексему
    fun add_lexeme(a: Lexeme) {
        _array.add(a)
    }

    //удалить лексему с таким номером
    fun delete_lexeme(i: Int) {
        _array.removeAt(i)
    }

    //заменить лексему x на значение
    fun substitute(keys: ArrayList<String>, values: ArrayList<Double>) {
        for (i in keys.indices) {
            val key = keys[i]
            val x = values[i]
            for (j in _array.indices) {
                if (_array[j].get_id() === Id_lexemes.VARIABLE && _array[j].key == key) {
                    val x0 = ArrayList<Double>()
                    x0.add(x)
                    _array[j] = Lexeme(Id_lexemes.ARGUMENT, x0)
                }
            }
        }
    }

    //создать новое предложение из части старого
    fun create_lexeme_vector(a: Int, b: Int): Sentence {
        val buffer = ArrayList<Lexeme>()
        for (i in a..b) {
            buffer.add(_array[i])
        }
        buffer.add(Lexeme(Id_lexemes.END, ArrayList()))
        return Sentence(buffer)
    }

    //заменить диапазон на одну лексему
    fun replace_sector(a: Int, b: Int, paste: Lexeme) {
        for (i in a until b) {
            delete_lexeme(a)
        }
        _array[a] = paste
    }

    fun find_commas(begin: Int): ArrayList<Int> {
        val answer = ArrayList<Int>()
        var i = 0
        while (_array[i].get_id() !== Id_lexemes.END) {
            if (_array[i].get_id() === Id_lexemes.COMMA) {
                answer.add(i + begin + 1)
            } else if (_array[i].get_id() === Id_lexemes.LEFT_BR) {
                i = find_right_bracket(i)
            }
            i++
        }
        return answer
    }

    //найти закрывающую скобку для открывающей
    fun find_right_bracket(a: Int): Int {
        var count_open_brackets = 1
        var i = a + 1
        while (true) {
            if (_array[i].get_id() === Id_lexemes.LEFT_BR) count_open_brackets++ else if (_array[i].get_id() === Id_lexemes.RIGHT_BR) count_open_brackets--
            if (count_open_brackets == 0) {
                return i
            }
            i++
        }
    }

    //найти оператор с наивысшим приоритетом
    fun find_highest_priority(): Int {
        var max_priority = 0
        var i = 0
        while (_array[i].get_id() !== Id_lexemes.END) {
            if (_array[i].get_id() !== Id_lexemes.ARGUMENT) {
                val cur_priority = Archieve.get_priority(_array[i].get_id())
                if (cur_priority > max_priority) {
                    max_priority = cur_priority
                }
            }
            i++
        }
        return max_priority
    }

    fun find_countable_operator(priority: Int): Int {
        var i = 0
        while (_array[i].get_id() !== Id_lexemes.END) {
            if (_array[i].get_id() !== Id_lexemes.ARGUMENT && Archieve.get_priority(_array[i].get_id()) === priority) {
                val left = Archieve.get_left_argue(_array[i].get_id())
                val right = Archieve.get_right_argue(_array[i].get_id())
                if ((left == 0 || _array[i - 1].get_id() === Id_lexemes.ARGUMENT) && (right == 0 || _array[i + 1].get_id() === Id_lexemes.ARGUMENT)) return i
            }
            i++
        }
        return -1
    }

    fun have_errors(): Boolean {
        val n = _array.size - 1
        if (Archieve.get_left_argue(_array[0].get_id()) === 0 && Archieve.get_right_argue(_array[n].get_id()) === 0) {
            for (i in 1 until n) {
                val cur_id = _array[i].get_id()
                val left = Archieve.get_left_argue(cur_id)
                val right = Archieve.get_right_argue(cur_id)
                if (left > 0 && right > 0) {
                    if (Archieve.get_right_argue(_array[i - 1].get_id()) === 0 && Archieve.get_left_argue(_array[i + 1].get_id()) === 0) {
                    } else {
                        setError(Id_errors.MISS_ARGUMENT_BINARY_OPERATOR, _array[i])
                        return true
                    }
                } else if (left > 0) {
                    if (Archieve.get_right_argue(_array[i - 1].get_id()) === 0 && Archieve.get_left_argue(_array[i + 1].get_id()) !== 0) {
                    } else {
                        setError(Id_errors.MISS_ARGUMENT_POST_OPERATOR, _array[i])
                        return true
                    }
                } else if (right > 0) {
                    if (Archieve.get_left_argue(_array[i + 1].get_id()) === 0 && Archieve.get_right_argue(_array[i - 1].get_id()) !== 0) {
                    } else {
                        setError(Id_errors.MISS_ARGUMENT_PRE_OPERATOR, _array[i])
                        return true
                    }
                }
            }
        }
        return false
    }

    //посчитать значение предложения
    fun count(): Lexeme {
        var a = find_left_br()
        while (a != -1) //избавляемся от скобок
        {
            val b = find_right_bracket(a)
            val current = create_lexeme_vector(a + 1, b - 1)
            val commas = current.find_commas(a)
            //в скобках выражение без запятых
            if (commas.size == 0) {
                val replace = current.count()
                if (getError() === Id_errors.NON_ERROR) {
                    replace_sector(a, b, replace)
                } else {
                    return Lexeme(Id_lexemes.END, ArrayList())
                }
            } else {
                val A = ArrayList<Sentence>()
                val values = ArrayList<Double>()
                for (i in 0 until commas.size + 1) {
                    A.add(Sentence())
                    values.add(0.0)
                }
                A[0] = create_lexeme_vector(a + 1, commas[0] - 1)
                for (i in 1 until commas.size) {
                    A[1] = create_lexeme_vector(commas[i - 1] + 1, commas[i] - 1)
                }
                A[A.size - 1] = create_lexeme_vector(commas[commas.size - 1] + 1, b - 1)
                for (i in values.indices) {
                    values[i] = A[i].count().get_value()
                    if (getError() !== Id_errors.NON_ERROR) {
                        return Lexeme(Id_lexemes.END, ArrayList())
                    }
                }
                val replace = Lexeme(Id_lexemes.ARGUMENT, values)
                replace_sector(a, b, replace)
            }
            a = find_left_br()
        }
        if (have_errors()) {
            return Lexeme(Id_lexemes.END)
        }
        a = find_highest_priority()
        while (a != 0) {
            var b = find_countable_operator(a)
            while (b != -1) {
                val left = Archieve.get_left_argue(_array[b].get_id())
                val right = Archieve.get_right_argue(_array[b].get_id())
                val l = if (left != 0) 1 else 0
                val r = if (right != 0) 1 else 0
                var left_argue = ArrayList<Double>()
                var right_argue = ArrayList<Double>()
                if (l != 0) {
                    left_argue = _array[b - 1].get_values()
                    if (left != left_argue.size) {
                        setError(Id_errors.BAD_ARGUMENTS, _array[b])
                        return Lexeme(Id_lexemes.END, ArrayList())
                    }
                }
                if (r != 0) {
                    right_argue = _array[b + 1].get_values()
                    if (right != right_argue.size) {
                        setError(Id_errors.BAD_ARGUMENTS, _array[b])
                        return Lexeme(Id_lexemes.END, ArrayList())
                    }
                }
                val argue: ArrayList<Double> = Support.union(left_argue, right_argue)
                if (Archieve.check_countable(_array[b].get_id(), argue)) {
                    val x0 = ArrayList<Double>()
                    x0.add(Archieve.count(_array[b].get_id(), argue))
                    val replace = Lexeme(Id_lexemes.ARGUMENT, x0)
                    replace_sector(b - 1 * l, b + 1 * r, replace)
                } else {
                    setError(Id_errors.IMPOSSIBLE_COUNT, _array[b])
                    return Lexeme(Id_lexemes.END, ArrayList())
                }
                b = find_countable_operator(a)
            }
            a = find_highest_priority()
        }
        return if (_array.size == 2 && _array[0].get_id() === Id_lexemes.ARGUMENT && _array[1].get_id() === Id_lexemes.END) {
            _array[0]
        } else {
            setError(Id_errors.UNKNOWN_ERROR, -1, -1)
            Lexeme(Id_lexemes.END, ArrayList())
        }
    }

    fun code(): String {
        var A = ""
        var i = 0
        while (_array[i].get_id() !== Id_lexemes.END) {
            val cur_lexeme = _array[i]
            A += cur_lexeme.key
            i++
        }
        return A
    }
}