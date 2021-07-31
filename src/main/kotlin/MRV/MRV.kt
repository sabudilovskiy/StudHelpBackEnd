package MRV

import Lexemes.Archieve
import Lexemes.ErrorHandler.getError
import Lexemes.ErrorHandler.get_begin_error
import Lexemes.ErrorHandler.get_end_error
import Lexemes.ErrorHandler.set_default
import Lexemes.Id_errors
import Lexemes.Lexeme
import Lexemes.Sentence
import Logger.Log
import MathObject.MathObject.MathObject
import MathObject.Ring
import Matrix.AugmentedMatrix
import Matrix.Matrix
import Number.createNumber
import Parameters.Number
import Support.createRectangleArrayList

fun find_null_string_in_array(arr : ArrayList<String?>): Int{
    var i : Int = 0;
    val n : Int = arr.size
    while (i < n){
        if (arr[i] == null) return i
        i++
    }
    return -1
}
fun is_number(str : String): Boolean{
    if (str == "") return false
    var commas : Int = 0
    for (ch : Char in str){
        if (is_digit(ch)){}
        else if (ch == '.' && commas == 0) commas++
        else return false
    }
    return true
}
fun is_digit(ch : Char ) : Boolean{
    return if ('0' <= ch && ch <= '9') true
    else false
}
object MRV {
    @Throws(
        ARGUMENT_LIST_MISMATCH::class,
        UNKNOWN_FUNCTION::class,
        ERROR_SIGNS::class,
        IMPOSSIBLE_COUNT::class,
        MISS_ARGUMENT_BINARY_OPERATOR::class,
        MISS_ARGUMENT_PRE_OPERATOR::class,
        MISS_ARGUMENT_POST_OPERATOR::class,
        HAVE_OPEN_BRACKETS::class,
        MORE_RIGHT_BRACKETS::class,
        BAD_ARGUMENTS::class,
        UNKNOWN_ERROR::class, MRV.Input_Values_Lexemes_Exception::class
    )
    fun get_log() : ArrayList<String>{
        return Log.get_log()
    }
    fun count_lexemes(temp_input: String?, temp_keys: ArrayList<String?>, temp_values: ArrayList<String?>): Double {
        Log.clear()
        if (temp_input == null || temp_input == "") throw Input_Input_Lexemes_Exception()
        val input : String = temp_input
        val keys : ArrayList<String> = arrayListOf()
        val str_values : ArrayList<String> = arrayListOf()
        val values : ArrayList<Double> = arrayListOf()
        run {
            val i = find_null_string_in_array(temp_keys)
            if (i != -1) throw Input_Keys_Lexemes_Exception(i)
            for (str in temp_keys) keys.add(str!!)
        }
        run{
            val i = find_null_string_in_array(temp_values)
            if (i != -1) throw Input_Values_Lexemes_Exception(i)
            for (str in temp_values) str_values.add(str!!)
        }
        run{
            val i = 0
            val n = str_values.size
            while (i < n){
                if (is_number(str_values[i])){
                    values.add(str_values[i].toDouble())
                }
                else throw Input_Values_Lexemes_Exception(i)
            }
        }
        if (keys.size != values.size) {
            throw ARGUMENT_LIST_MISMATCH()
        }
        Archieve(keys)
        set_default()
        val answer: Lexeme
        val sentence = Sentence(input!!)
        if (getError() === Id_errors.ERROR_SIGNS) throw ERROR_SIGNS() else if (getError() === Id_errors.UNKNOWN_FUNCTION) throw UNKNOWN_FUNCTION()
        sentence.substitute(keys, values)
        answer = sentence.count()
        return if (getError() === Id_errors.NON_ERROR) {
            answer.get_value()
        } else if (getError() === Id_errors.UNKNOWN_FUNCTION) {
            throw UNKNOWN_FUNCTION()
        } else if (getError() === Id_errors.ERROR_SIGNS) {
            throw ERROR_SIGNS()
        } else if (getError() === Id_errors.IMPOSSIBLE_COUNT) {
            throw IMPOSSIBLE_COUNT()
        } else if (getError() === Id_errors.MISS_ARGUMENT_BINARY_OPERATOR) {
            throw MISS_ARGUMENT_BINARY_OPERATOR()
        } else if (getError() === Id_errors.MISS_ARGUMENT_PRE_OPERATOR) {
            throw MISS_ARGUMENT_PRE_OPERATOR()
        } else if (getError() === Id_errors.MISS_ARGUMENT_POST_OPERATOR) {
            throw MISS_ARGUMENT_POST_OPERATOR()
        } else if (getError() === Id_errors.HAVE_OPEN_BRACKETS) {
            throw HAVE_OPEN_BRACKETS()
        } else if (getError() === Id_errors.MORE_RIGHT_BRACKETS) {
            throw MORE_RIGHT_BRACKETS()
        } else if (getError() === Id_errors.BAD_ARGUMENTS) {
            throw BAD_ARGUMENTS()
        } else {
            throw UNKNOWN_ERROR()
        }
    }
    fun count_determinant(temp_matrix : ArrayList<ArrayList<String?>>, method : String?, number : String?) : String{
        Log.clear()
        val m : Int = temp_matrix.size
        if (m<1) throw MATRIX_FAIL()
        for (i in 0 until m) if (temp_matrix[i].size != m) throw NON_QUADRATIC_MATRIX()
        val arr = createRectangleArrayList(createNumber(0.0), m, m)
        for (i in 0 until m)
            for (j in 0 until m)
                if (is_number(temp_matrix[i][j]!!)) arr[i][j] = createNumber(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)
        if (number == null) throw KEY_NUMBER_EMPTY()
        else if (number == "DEC"){
            Settings.numbers.use_number = Number.DEC
        }
        else if (number == "PROPER"){
            Settings.numbers.use_number = Number.PROPER
        }
        else throw UNKNOWN_NUMBER()
        val current : Matrix = Matrix(arr)
        val answer : Ring
        if (method == null) throw KEY_METHOD_EMPTY()
        else if (method == "LAPLASS"){
            answer = current.det_with_laplass()
        }
        else if (method == "TRIANGLE"){
            answer = current.det_with_triangle()
        }
        else if (method == "SARUSS"){
            answer = current.det_with_saruss_rule()
        }
        else if (method ==  "BASIC"){
            answer = current.det_with_basic_rules()
        }
        else throw UNKNOWN_METHOD()
        return answer.toString()
    }
    fun find_inverse_matrix(temp_matrix : ArrayList<ArrayList<String?>>, method : String?, number : String?) : String{
        Log.clear()
        val m : Int = temp_matrix.size
        if (m<1) throw MATRIX_FAIL()
        for (i in 0 until m) if (temp_matrix[i].size != m) throw NON_QUADRATIC_MATRIX()
        val arr = createRectangleArrayList(createNumber(0.0), m, m)
        for (i in 0 until m)
            for (j in 0 until m)
                if (is_number(temp_matrix[i][j]!!)) arr[i][j] = createNumber(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)
        if (number == null) throw KEY_NUMBER_EMPTY()
        else if (number == "DEC"){
            Settings.numbers.use_number = Number.DEC
        }
        else if (number == "PROPER"){
            Settings.numbers.use_number = Number.PROPER
        }
        else throw UNKNOWN_NUMBER()
        val current : Matrix = Matrix(arr)
        val answer : Ring
        if (method == null) throw KEY_METHOD_EMPTY()
        else if (method == "GAUSS") {
            try {
                answer = current.get_inverse_gauss()
            }
            catch (degenerate_matrix : DEGENERATE_MATRIX){
                return "Матрица вырождена. Обратная не существует"
            }
        }
        else if (method == "ALGEBRAIC_COMPLEMENT")
        {
            try {
                answer = current.get_inverse_algebraic_complement()
            }
            catch (degenerate_matrix : DEGENERATE_MATRIX){
                return "Матрица вырождена. Обратная не существует"
            }
        }
        else throw UNKNOWN_METHOD()
        return answer.toString()
    }
    fun rank(temp_matrix : ArrayList<ArrayList<String?>>, method : String?, number : String?) : Int{
        Log.clear()
        val m : Int = temp_matrix.size
        if (m<1) throw MATRIX_FAIL()
        for (i in 0 until m) if (temp_matrix[i].size != m) throw NON_QUADRATIC_MATRIX()
        val arr = createRectangleArrayList(createNumber(0.0), m, m)
        for (i in 0 until m)
            for (j in 0 until m)
                if (is_number(temp_matrix[i][j]!!)) arr[i][j] = createNumber(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)
        if (number == null) throw KEY_NUMBER_EMPTY()
        else if (number == "DEC"){
            Settings.numbers.use_number = Number.DEC
        }
        else if (number == "PROPER"){
            Settings.numbers.use_number = Number.PROPER
        }
        else throw UNKNOWN_NUMBER()
        val current : Matrix = Matrix(arr)
        val answer : Int
        if (method == null) throw KEY_METHOD_EMPTY()
        else if (method == "TRIANGLE"){
            answer = current.rank_with_triangle()
        }
        else if (method == "MINORS"){
            answer = current.rank_with_minors()
        }
        else throw UNKNOWN_METHOD()
        return answer
    }
    fun SLE(temp_matrix : ArrayList<ArrayList<String?>>, untrivial : Boolean, method : String?, number : String?) : String{
        Log.clear()
        val m : Int = temp_matrix.size
        if (m<1) throw MATRIX_FAIL()
        val n : Int = temp_matrix[0].size
        for (i in 0 until m) if (temp_matrix[i].size != n) throw MATRIX_FAIL()
        val arr : ArrayList<ArrayList<Ring>>
        val free : ArrayList<ArrayList<Ring>>
        if (untrivial){
            arr = createRectangleArrayList(createNumber(0.0), m, n-1)
            free = createRectangleArrayList(createNumber(0.0), m, 1)
            for (i in 0 until m) {
                for (j in 0 until n - 1)
                    if (is_number(temp_matrix[i][j]!!)) arr[i][j] = createNumber(temp_matrix[i][j]!!.toDouble())
                    else throw FIELD_ERROR(i, j)
                if (is_number(temp_matrix[i][n-1]!!)) free[i][0] = createNumber(temp_matrix[i][n-1]!!.toDouble())
                else throw FIELD_ERROR(i, n-1)
            }
        }
        else{
            arr = createRectangleArrayList(createNumber(0.0), m, n)
            free = createRectangleArrayList(createNumber(0.0), m, 1)
            for (i in 0 until m)
                for (j in 0 until n)
                    if (is_number(temp_matrix[i][j]!!)) arr[i][j] = createNumber(temp_matrix[i][j]!!.toDouble())
                    else throw FIELD_ERROR(i, j)
        }
        val current : AugmentedMatrix = AugmentedMatrix(arr, free)
        try {
            val answer : MathObject = current.solve_system()
            return answer.toString()
        }
        catch (have_not_solution : HAVE_NOT_SOLUTIONS){
            return "Нет решений."
        }
    }
    open class Lexemes_Exception : Exception {
        var error_begin = -1
            private set
        var error_end = -1
            private set

        constructor() {}
        constructor(begin: Int, end: Int) {
            error_begin = begin
            error_end = end
        }
    }
    class Input_Input_Lexemes_Exception(): Exception(){
    }
    class Input_Keys_Lexemes_Exception() : Exception() {
        var number_key : Int = -1
        constructor(_error : Int ): this(){
            number_key = _error
        }
    }
    class Input_Values_Lexemes_Exception() : Exception() {
        var number_value : Int = -1
        constructor(_error : Int ): this(){
            number_value = _error
        }
    }
    class ARGUMENT_LIST_MISMATCH : Lexemes_Exception()
    class BAD_ARGUMENTS : Lexemes_Exception(get_begin_error(), get_end_error())
    class ERROR_SIGNS : Lexemes_Exception(get_begin_error(), get_end_error())
    class HAVE_OPEN_BRACKETS internal constructor() : Lexemes_Exception(get_begin_error(), get_end_error())
    class IMPOSSIBLE_COUNT : Lexemes_Exception(get_begin_error(), get_end_error())
    class MISS_ARGUMENT_BINARY_OPERATOR : Lexemes_Exception(get_begin_error(), get_end_error())
    class MISS_ARGUMENT_POST_OPERATOR : Lexemes_Exception(get_begin_error(), get_end_error())
    class MISS_ARGUMENT_PRE_OPERATOR : Lexemes_Exception(get_begin_error(), get_end_error())
    class MORE_RIGHT_BRACKETS : Lexemes_Exception(get_begin_error(), get_end_error())
    class UNKNOWN_ERROR : Lexemes_Exception(get_begin_error(), get_end_error())
    class UNKNOWN_FUNCTION : Lexemes_Exception(get_begin_error(), get_end_error())
    open class MATRIX_ERROR : Exception()
    class NON_QUADRATIC_MATRIX : MATRIX_ERROR()
    class DEGENERATE_MATRIX : MATRIX_ERROR()
    class MATRIX_FAIL : MATRIX_ERROR()
    class MATRIX_DIMENSION_MISSMATCH : MATRIX_ERROR()
    class INVALID_NUMBER_STRING : MATRIX_ERROR()
    class HAVE_NOT_SOLUTIONS : MATRIX_ERROR()
    class NON_SINGLE : MATRIX_ERROR()
    class NON_COMPLIANCE_TYPES : Exception()
    class KEY_METHOD_EMPTY : Exception()
    class UNKNOWN_METHOD : Exception()
    class KEY_NUMBER_EMPTY : Exception()
    class UNKNOWN_NUMBER : Exception()
    class FIELD_ERROR : Exception{
        var i : Int = -1
        var j : Int = -1
        constructor(_i : Int, _j : Int){
            i = _i
            j = _j
        }
    }
}