import MRV.MRV
import MRV.MRV.HAVE_NOT_SOLUTIONS
import MRV.MRV.INVALID_NUMBER_STRING
import MRV.MRV.MATRIX_DIMENSION_MISSMATCH
import MRV.MRV.NON_SINGLE
import Support.createRectangleArrayList
import Support.createSingleArrayList
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object test_backend {
    @Throws(
        MATRIX_DIMENSION_MISSMATCH::class,
        INVALID_NUMBER_STRING::class,
        HAVE_NOT_SOLUTIONS::class,
        NON_SINGLE::class
    )
    @JvmStatic
    fun main(args: Array<String>) {
        val scan = Scanner(System.`in`)
        val command : Int
        println("Введите номер того, что необходимо вычислить: \n 1)значение функции \n 2)определитель матрицы \n 3)обратную матрицу \n 4)ранк матрицы \n 5)решение системы линейных уравнений")
        command = scan.nextLine().toInt()
        if (command == 1){
            val n : Int
            val input : String
            val keys : ArrayList<String?>
            val values : ArrayList<String?>
            println("Введите функцию: ")
            input = scan.nextLine()
            println("Введите количество переменных: ")
            n = scan.nextLine().toInt()
            keys = createSingleArrayList(null, n)
            values = createSingleArrayList(null, n)
            for (i in 0 until n){
                println("Введите ключ переменной: ")
                keys[i] = scan.nextLine()
                println("Введите значение переменной: ")
                values[i] = scan.nextLine()
            }
            try {
                val answer : Double = MRV.count_lexemes(input, keys, values)
                println(answer)
            }
            catch (error : MRV.Input_Input_Lexemes_Exception){
                println("Функция не введена")
            }
            catch (error : MRV.Input_Keys_Lexemes_Exception){
                println("Ключ переменной №" + (error.number_key + 1) + " не введён")
            }
            catch (error : MRV.Input_Values_Lexemes_Exception){
                println("Значение переменной №" + (error.number_value + 1) + " не введено")
            }
            catch (error : MRV.ERROR_SIGNS){
                println("В одном из чисел слишком много разделителей. Ошибка в символе №" + (error.error_begin+1))
            }
            catch (error : MRV.UNKNOWN_FUNCTION){
                println("Не распознана функция. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
            catch (error : MRV.IMPOSSIBLE_COUNT){
                println("Функцию не возможно вычислить. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
            catch (error : MRV.BAD_ARGUMENTS){
                println("Аргумент не соответствует функции. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
            catch (error : MRV.MORE_RIGHT_BRACKETS){
                println("Закрыта лишняя правая скобка. Ошибка в символе №" + (error.error_begin+1))
            }
            catch (error : MRV.HAVE_OPEN_BRACKETS){
                println("Не закрыта скобка. Ошибка в символе №" + (error.error_begin+1))
            }
            catch (error : MRV.MISS_ARGUMENT_BINARY_OPERATOR){
                println("Отсутствует аргумент у бинарного оператора. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
            catch (error : MRV.MISS_ARGUMENT_POST_OPERATOR){
                println("Отсутствует аргумент у постоператора. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
            catch (error : MRV.MISS_ARGUMENT_PRE_OPERATOR){
                println("Отсутствует аргумент у преоператора. Ошибка с " + (error.error_begin+1) + " по " + (error.error_end+1))
            }
        }
        else if (2 <= command && command <= 5)
        {
            val m: Int
            val n : Int
            val method : String
            val number : String
            println("Введите m: ")
            m = scan.nextLine().toInt()
            if (command == 5){
                println("Введите n: ")
                n = scan.nextLine().toInt()
            }
            else n = m
            val arr = createRectangleArrayList<String?>("", m, n)
            for (i in 0 until m) {
                var temp = "Введите " + (i + 1) + " строку: "
                println(temp)
                val buffer = scan.nextLine().split(" ".toRegex()).toTypedArray()
                for (j in 0 until n) arr[i][j] = buffer[j]
            }
            println("Введите метод решения: ")
            method = scan.nextLine()
            println("Введите тип чисел, которые будут использоваться")
            number = scan.nextLine()
            val temp : String
            try {
                if (command == 2) temp = MRV.count_determinant(arr, method, number)
                else if (command == 3) temp = MRV.find_inverse_matrix(arr, method, number)
                else if (command == 4) temp = MRV.rank(arr, method, number).toString()
                else {
                    val untrivial : Boolean
                    val temp_scan : String
                    println("Введите, является ли ваша матрица тривиальной. Если нет, то последний столбец будет считаться столбцом свободных членов.(y/n):")
                    temp_scan = scan.nextLine()
                    if (temp_scan == "y" || temp_scan == "yes") untrivial = false;
                    else if (temp_scan == "n" || temp_scan == "no") untrivial = true;
                    else throw Exception()
                    temp = MRV.SLE(arr, untrivial, method, number)
                }
                println("Ответ:")
                println(temp)
            }
            catch (matrix_fail : MRV.MATRIX_FAIL){
                println("Матрица повреждена")
            }
            catch (field_error : MRV.FIELD_ERROR){
                println("Ошибка при создании матрицы: проблема в " + (field_error.i +1) + " строке, " + (field_error.j + 1) + " столбце")
            }
            catch (key_number_empty : MRV.KEY_NUMBER_EMPTY){
                println("Не введен ключ используемых чисел")
            }
            catch (key_method_empty : MRV.KEY_METHOD_EMPTY){
                println("Не введен ключ используемых чисел")
            }
            catch (unknown_method : MRV.UNKNOWN_METHOD){
                println("Неизвестный метод")
            }
            catch (unknown_number : MRV.UNKNOWN_NUMBER){
                println("Неизвестное число")
            }
            val Logs : ArrayList<String>  = MRV.get_log()
            for (i in 0 until Logs.size){
                //не ну тут можно сделать разделение по объектам или коммитам, но мне лень.
                println(Logs[i])
            }
        }
        else println("Неизвестная команда")
    }
}