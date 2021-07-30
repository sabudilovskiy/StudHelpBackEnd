package Matrix

import Logger.Log
import MathObject.Ring
import Logger.Log.add
import MRV.MRV
import MRV.MRV.DEGENERATE_MATRIX
import MRV.MRV.INVALID_NUMBER_STRING
import MRV.MRV.MATRIX_DIMENSION_MISSMATCH
import MRV.MRV.NON_QUADRATIC_MATRIX
import Number.Dec_Number
import Number.FractionalNumber
import Number.createNumber
import Parameters.Det
import Parameters.Inverse.*
import Parameters.Rank
import Settings.matrix
import Settings.matrix.Inverse
import Settings.matrix.Rank.getSettings
import Support.createRectangleArrayList
import Support.newSingleArrayList
import java.util.*
import kotlin.collections.ArrayList

public fun create_vector_str(cords: ArrayList<Ring>): Matrix {
    val zero = createNumber(0.0)
    val temp = createRectangleArrayList(zero, 1, cords.size)
    for (i in 0 until cords.size) temp[0][i] = cords[i]
    return Matrix(temp)
}

public fun create_vector_col(cords: ArrayList<Ring>): Matrix {
    val zero = createNumber(0.0)
    val temp = createRectangleArrayList(zero, cords.size, 1)
    for (i in 0 until cords.size) temp[i][0] = cords[i]
    return Matrix(temp)
}

open class Matrix : Ring {
    protected var cof_det = createNumber(1.0)
    var m = 0
    var n = 0
    lateinit var arr: ArrayList<ArrayList<Ring>>;

    constructor(_arr: ArrayList<ArrayList<Ring>>) {
        arr = _arr
        m = _arr.size
        if (m > 0) n = _arr[0].size else n = 0
    }

    //создаёт единичную матрицу n на n
    constructor(n: Int) {
        val zero = createNumber(0.0)
        val one = createNumber(1.0)
        val arr = createRectangleArrayList<Ring>(zero, n, n)
        m = n
        this.n = n
        for (i in 0 until n) for (j in 0 until n) if (i == j) arr[i][j] = one
    }

    @Throws(MRV.MATRIX_DIMENSION_MISSMATCH::class)

    //прибавляем к a b
    protected open fun summ_strings(a: Int, b: Int, k: Ring) {
        if (0 <= a && a < m && 0 <= b && b < m) {
            for (i in 0 until n) arr[a][i] += arr[b][i] * k
            log_this("Прибавляем к " + (a + 1) + " строке " + (b + 1) + " строку, умноженную на " + k)
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected open fun is_null_string(a: Int): Boolean {
        if (0 <= a && a < m) {
            for (i in 0 until n) if (!arr[a][i].equals(0.0)) return false
            return true
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun swap_strings(a: Int, b: Int) {
        if (0 <= a && a < m && 0 <= b && b < m) {
            cof_det = cof_det * createNumber(-1.0)
            for (i in 0 until n) {
                val first = arr[a][i]
                val second = arr[b][i]
                arr[a][i] = second
                arr[b][i] = first
            }
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    protected open fun delete_string(a: Int) {
        if (0 <= a && a < m) {
            val zero = createNumber(0.0)
            val temp = createRectangleArrayList<Ring>(zero, m - 1, n)
            for (i in 0 until a) for (j in 0 until n) temp[i][j] = arr[i][j]
            for (i in a + 1 until m) for (j in 0 until n) temp[i - 1][j] = arr[i][j]
            arr = temp
            m--
        }
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun delete_column(a: Int) {
        if (0 <= a && a < n) {
            val zero = createNumber(0.0)
            val temp = createRectangleArrayList<Ring>(zero, m, n - 1)
            for (i in 0 until m) for (j in 0 until a) temp[i][j] = arr[i][j]
            for (i in 0 until m) for (j in a + 1 until n) temp[i][j - 1] = arr[i][j]
            arr = temp
            n--
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun move_string_to_end(a: Int) {
        if (0 <= a && a < m) {
            for (i in a until m - 1) {
                swap_strings(i, i + 1)
            }
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun find_non_zero_in_column(column: Int, start: Int): Int {
        return if (0 <= column && column < n) {
            for (i in start until m) {
                if (!arr[i][column].equals(0.0)) return i
            }
            -1
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected open fun mult_string(a: Int, k: Ring) {
        if (0 <= a && a < m) {
            for (i in 0 until n) arr[a][i] = arr[a][i] * k
            log_this("Умножаем " + (a + 1) + " строку на " + k)
            cof_det = cof_det / k
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected open fun div_string(a: Int, k: Ring) {
        if (0 <= a && a < m) {
            for (i in 0 until n) arr[a][i] = arr[a][i] / k
            log_this("Делим " + (a + 1) + " строку на " + k)
            cof_det = cof_det * k
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    fun gauss_transformation() {
        val local_m = m
        log_this("Для того, чтобы преобразовать матрицу методом Гаусса необходимо сначала сделать матрицу верхнетреугольной.")
        triangular_transformation()
        log_this("Мы добились того, что под главной диагональю одни нули. Осталось добиться того же над главной диагональю, где это возможно")
        for (i in m - 1 downTo 1) {
            val f = find_non_zero_in_column(i, 0)
            if (!arr[i][i].equals(0.0) && f != i && f != -1) {
                for (j in i - 1 downTo 0) {
                    add(
                        "k" + j + "= -a" + j + i + " / a" + i + i + "=" + -arr[j][i] + " / " + arr[i][i] + " = " + (-arr[j][i] / arr[i][i]),
                        ""
                    )
                    val k = -arr[j][i] / arr[i][i]
                    summ_strings(j, i, k)
                }
            } else if (f == i || f == -1) {
                add("Так как " + (i + 1) + " столбец выше главной диагонали уже обнулён, то ничего не делаем", "")
            }
        }
        log_this("Теперь и над, и под главной диагональю нули. Осталось добиться того, чтобы на главной диагонали были только единицы.")
        for (i in 0 until m) if (!arr[i][i].equals(0.0) && !arr[i][i].equals(1.0)) div_string(i, arr[i][i])
    }

    //проверяет, является ли матрица единичной в главной части(квадратной подматрице)
    protected fun is_single(): Boolean {
        val one = createNumber(1.0)
        val zero = createNumber(0.0)
        for (i in 0 until m) for (j in 0 until m) if (arr[i][j] != (if (i == j) one else zero)) return false
        return true
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    fun triangular_transformation() {
        var local_m = m
        var local_n = n
        log_this("Для того, чтобы привести матрицу к верхнетреугольному(трапециевидному) виду необходимо постепенно, столбец за столбцом путём вычитания верхних строк из нижних добиваться появления нулей под главной диагональю.")
        var i = 0
        while (i < local_m - 1 && i < local_n - 1) {
            val f = find_non_zero_in_column(i, i + 1)
            if (!arr[i][i].equals(0.0) && f != -1) {
                for (j in i + 1 until m) {
                    add(
                        "k" + (j + 1) + "= -a" + (j + 1) + (i + 1) + " / a" + (i + 1) + (i + 1) + "=" + -arr[j][i] + " / " + arr[i][i] + " = " + -arr[j][i] / arr[i][i],
                        ""
                    )
                    val k = -arr[j][i] / arr[i][i]
                    summ_strings(j, i, k)
                }
            } else if (f == -1) {
                add("Так как " + (i + 1) + " столбец ниже главной диагонали уже обнулён, то ничего не делаем", "")
            } else {
                if (is_null_string(i)) {
                    move_string_to_end(i)
                    log_this("Получилось, что " + (i + 1) + " строка является нулевой. Смещаем её вниз.")
                    i--
                    local_m--
                    local_n--
                } else {
                    swap_strings(i, f)
                    log_this("Так как " + (i + 1) + " строчка имеет ноль на главной диагонали, то мы её поменяли с нижней, у которой нет нуля на главной диагонали")
                }
            }
            i++
        }
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun complement_minor(str: Int, col: Int): Matrix {
        return if (0 <= str && str < m && 0 <= col && col < n) {
            val copy = Matrix(arr)
            copy.delete_string(str)
            copy.delete_column(col)
            copy
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.MATRIX_DIMENSION_MISSMATCH::class)
    protected fun complement_minor(str: IntArray, col: IntArray): Matrix {
        return if (m == n && str.size == col.size) {
            var k = 0
            var a = 0
            var b = 0
            val m_new = m - str.size
            val zero = createNumber(0.0)
            val temp_arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList(zero, m_new, m_new)
            while (k < m_new * m_new) {
                var crossed_out = false
                for (j in str) if (a == j) {
                    crossed_out = true
                    break
                }
                if (!crossed_out) {
                    for (j in col) if (a == j) {
                        crossed_out = true
                        break
                    }
                    if (!crossed_out) {
                        temp_arr[k / m_new][k % m_new] = arr[a++][b++]
                        k++
                    }
                }
            }
            Matrix(temp_arr)
        } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(MRV.MATRIX_DIMENSION_MISSMATCH::class)
    protected fun minor(str: Array<Int?>, col: Array<Int?>): Matrix {
        return if (str.size == col.size) {
            var k = 0
            val a = 0
            val b = 0
            val m_new = str.size
            val zero = createNumber(0.0)
            val temp_arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList(zero, m_new, m_new)
            var i = 0
            while (i < m && k < m_new * m_new) {
                var j = 0
                while (j < n && k < m_new * m_new) {
                    if (Arrays.asList(*str).contains(i) && Arrays.asList(*col).contains(j)) {
                        temp_arr[k / m_new][k % m_new] = arr[i][j]
                        k++
                    }
                    j++
                }
                i++
            }
            Matrix(temp_arr)
        } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun reduce_null_strings() {
        for (i in m - 1 downTo 0) if (is_null_string(i)) {
            delete_string(i)
            add("", "Вычёркиваем " + (i + 1) + " строку")
        }
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun count_null_in_string(a: Int): Int {
        return if (0 <= a && a < m) {
            var count = 0
            val zero = createNumber(0.0)
            for (i in 0 until n) if (arr[a][i] == zero) count++
            count
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun count_null_in_column(a: Int): Int {
        return if (0 <= a && a < m) {
            var count = 0
            val zero = createNumber(0.0)
            for (i in 0 until m) if (arr[i][a] == zero) count++
            count
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    //возвращает номер строки, в которой наибольшее количество нулей и их количество
    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected fun find_most_null_string(): IntArray {
        val max = IntArray(2)
        for (i in 0 until m) if (count_null_in_string(i) > max[1]) {
            max[0] = i
            max[1] = count_null_in_string(i)
        }
        return max
    }

    //возвращает номер столбца, в котором наибольшее количество нулей и их количество
    @Throws(MRV.INVALID_NUMBER_STRING::class)
    protected open fun find_most_null_column(): IntArray {
        val max = IntArray(2)
        for (i in 0 until n) if (count_null_in_column(i) > max[1]) {
            max[0] = i
            max[1] = count_null_in_column(i)
        }
        return max
    }

    @Throws(MRV.INVALID_NUMBER_STRING::class, MRV.NON_QUADRATIC_MATRIX::class)
    protected fun algebraic_complement(a: Int, b: Int): Ring {
        return if (0 <= a && a < m && 0 <= b && b < n) {
            add(
                "",
                "Для вычисления алгебраического дополнения необходимо умножить -1 в степени суммы индексов элемента на его минор."
            )
            add("", "Найдём A" + (a + 1) + (b + 1))
            val minor = complement_minor(a, b)
            add(
                "",
                "Получаем минор вычеркнув " + (a + 1) + " строку и " + (b + 1) + " столбец. Вычислим его определитель."
            )
            val minor_determinant = minor.determinant()
            val value = minor_determinant * createNumber(Math.pow(-1.0, (a + b).toDouble()))
            add(
                "A" + (a + 1) + (b + 1) + " = " + minor_determinant + "*" + "-1^(" + (a + 1) + "+" + (b + 1) + ") = " + value,
                ""
            )
            value
        } else throw MRV.INVALID_NUMBER_STRING()
    }

    @Throws(MRV.NON_QUADRATIC_MATRIX::class, MRV.INVALID_NUMBER_STRING::class)
    public fun determinant(): Ring {
        if (m == n) {
            try {
                val cur_method: Det = matrix.Det.get_det_method(m);
                return when (cur_method) {
                    Det.BASIC -> det_with_basic_rules();
                    Det.LAPLASS -> det_with_laplass();
                    Det.SARUSS -> det_with_saruss_rule();
                    Det.TRIANGLE -> det_with_triangle();
                };
            } catch (ignored: MRV.MATRIX_DIMENSION_MISSMATCH) {
                return createNumber(0.0)
            }
        } else throw MRV.NON_QUADRATIC_MATRIX();
    }

    @Throws(NON_QUADRATIC_MATRIX::class, MATRIX_DIMENSION_MISSMATCH::class)
    fun det_with_basic_rules(): Ring {
        var det = createNumber(0.0)
        return if (m == n) {
            if (m == 1) {
                log_this("Определитель матрицы из одного элемента равен этому элеменету.")
                add("det = " + "a11" + " = " + arr[0][0], "")
                det = arr[0][0]
            } else if (m == 2) {
                det = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0]
                log_this("Определитель матрицы 2 на 2 равен произведению элементов на главной минус произведение элементов на побочной диагонали.")
                add(
                    "det = a11*22 - a12*a21 = " + arr[0][0] + "*" + arr[1][1] + "-" + arr[0][1] + "*" + arr[1][0] + " =  " + det,
                    ""
                )
            } else throw MATRIX_DIMENSION_MISSMATCH()
            det
        } else throw NON_QUADRATIC_MATRIX()
    }

    @Throws(MATRIX_DIMENSION_MISSMATCH::class)
    fun det_with_saruss_rule(): Ring {
        return if (m == n && m == 3) {
            var det =
                arr[0][0] * arr[1][1] * arr[2][2] + arr[0][1] * arr[1][2] * arr[2][0] + arr[0][2] * arr[1][0] * arr[2][1]
            det =
                det - arr[2][0] * arr[1][1] * arr[0][2] - arr[1][0] * arr[0][1] * arr[2][2] - arr[0][0] * arr[2][1] * arr[1][2]
            log_this("Определитель матрицы 3 на 3 можно вычислить используя правило треугольника или способ Саррюса. ")
            add("det = a11*a22*a33 + a12*a23*a31 + a13*a21*a32 - a31*a22*a13 - a21*a12*a33 - a11*a32*a23 =", "")
            add(
                "= " + arr[0][0] + "*" + arr[1][1] + "*" + arr[2][2] + " " + arr[0][1] + "*" + arr[1][2] + "*" + arr[2][0] + " " + arr[0][2] + "*" + arr[1][0] + "*" + arr[2][1] + " -(" + arr[2][0] + "*" + arr[1][1] + "*" + arr[0][2] + " " + arr[1][0] + "*" + arr[0][1] + "*" + arr[2][2] + " " + arr[0][0] + "*" + arr[2][1] + "*" + arr[1][2] + ") =",
                ""
            )
            add(
                "= " + arr[0][0] * arr[1][1] * arr[2][2] + " + " + arr[0][1] * arr[1][2] * arr[2][0] + " + " + arr[0][2] * arr[1][0] * arr[2][1] + " + " + "-(" + arr[2][0] * arr[1][1] * arr[0][2] + " + " + arr[1][0] * arr[0][1] * arr[2][2] + " + " + arr[0][0] * arr[2][1] * arr[1][2] + ")= " + det,
                ""
            )
            det
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(INVALID_NUMBER_STRING::class)
    fun det_with_triangle(): Ring {
        val copy = Matrix(arr)
        copy.triangular_transformation()
        var det = createNumber(1.0)
        var temp = "det = "
        var temp2 = "det = "
        for (i in 0 until m) {
            det = det * copy.arr[i][i]
            temp += "a" + (i + 1) + (i + 1) + " * "
            temp2 += copy.arr[i][i].toString() + " * "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        add(
            temp,
            "Так как матрица треугольного вида, то определитель равен произведению элементов на главной диагонали."
        )
        add(temp2, "")
        add("det = $det", "")
        return det
    }

    @Throws(INVALID_NUMBER_STRING::class, NON_QUADRATIC_MATRIX::class)
    fun det_with_laplass(): Ring {
        val str = find_most_null_string()
        val col = find_most_null_column()
        if (str[1] >= col[1]) {
            return decompositonWithStr(str[0])
        } else {
            return decompositonWithCol(col[0])
        }
    }

    fun decompositonWithStr(str: Int): Ring {
        val zero = createNumber(0.0)
        var det = zero
        val A = newSingleArrayList<Ring>(zero, m) //массив алгебраических дополнений
        log_this("Для подсчёта определителя будем использовать разложение в строку. Раскладываем по " + (str + 1) + " строке.")
        for (i in 0 until n) {
            if (arr[str][i].equals(0.0)) {
                add(
                    "",
                    "Так как a" + (str + 1) + (i + 1) + " равно нулю, то считать A" + (str + 1) + (i + 1) + " нет необходимости."
                )
                A[i] = zero
            } else A[i] = algebraic_complement(str, i)
        }
        var temp = "det = "
        var temp2 = "det = "
        var temp3 = "det ="
        for (i in 0 until n) {
            det += A[i] * arr[str][i]
            temp += "A" + (str + 1) + (i + 1) + "*" + "a" + (str + 1) + (i + 1) + " + "
            temp2 += " " + A[i] + "*" + arr[str][i] + " + "
            temp3 += (A[i] * arr[str][i]).toString() + " + "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        temp3 = temp3.substring(0, temp3.length - 3)
        add(
            temp,
            "Для того, чтобы посчитать определитель при помощи строчки, надо вычислить сумму произведений элементов на их алгебраические дополнения."
        )
        add(temp2, "")
        add(temp3, "")
        add("det = $det", "")
        return det
    }

    fun decompositonWithCol(col: Int): Ring {
        val zero = createNumber(0.0)
        var det = zero
        val A = newSingleArrayList<Ring>(zero, m) //массив алгебраических дополнений
        log_this("Для подсчёта определителя будем использовать разложение в столбец. Раскладываем по " + (col + 1) + " столбцу.")
        for (i in 0 until n) {
            if (arr[i][col].equals(0.0)) {
                A[i] = createNumber(0.0)
                add(
                    "",
                    "Так как a" + (i + 1) + (col + 1) + " равно нулю, то считать A" + (i + 1) + (col + 1) + " нет необходимости."
                )
            } else A[i] = algebraic_complement(i, col)
        }
        var temp = "det = "
        var temp2 = "det = "
        var temp3 = "det ="
        for (i in 0 until n) {
            det += A[i] * arr[i][col]
            temp += "A" + (i + 1) + (col + 1) + "*" + "a" + (i + 1) + (col + 1) + " + "
            temp2 += " " + A[i] + "*" + arr[i][col] + " + "
            temp3 += (A[i] * arr[i][col]).toString() + " + "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        temp3 = temp3.substring(0, temp3.length - 3)
        add(
            temp,
            "Для того, чтобы посчитать определитель при помощи столбца, надо вычислить сумму произведений элементов на их алгебраические дополнения."
        )
        add(temp2, "")
        add(temp3, "")
        add("det = $det", "")
        return det
    }

    fun print() {
        for (i in 0 until m) {
            var temp = ""
            for (j in 0 until n) temp = temp + arr[i][j] + " "
            println(temp)
        }
    }

    @Throws(INVALID_NUMBER_STRING::class)
    fun rank_with_triangle(): Int {
        val copy = Matrix(arr)
        copy.triangular_transformation()
        add("", "Ранг равен количеству ненулевых элементов на главной диагонали")
        var count = 0
        for (i in 0 until m) {
            if (!copy.arr[i][i].equals(0.0)) count++
        }
        return count
    }

    @Throws(MATRIX_DIMENSION_MISSMATCH::class)
    fun rank_with_minors(): Int {
        log_this("Для нахождения ранга методом окламляющих миноров необходимо найти ненулевой элемент.")
        var a = 0
        var b = 0
        var end = false
        var i = 0
        while (i < m && !end) {
            var j = 0
            while (j < n && !end) {
                if (!arr[i][j].equals(0.0)) {
                    a = i
                    b = i
                    end = true
                }
                j++
            }
            i++
        }
        add("a" + (a + 1) + (b + 1) + '\u2260' + " 0 ", "")
        add("", "Теперь рассмотрим все миноры, в которые входит данный элемент.")
        val temp_arr = createRectangleArrayList<Ring>(createNumber(0.0), 1, 1)
        var cur_minor = Matrix(temp_arr)
        var cur_str = arrayOfNulls<Int>(1)
        var cur_col = arrayOfNulls<Int>(1)
        cur_str[0] = a
        cur_col[0] = b
        end = false
        while (cur_minor.m < m && cur_minor.m < n) {
            end = false
            val temp_str = arrayOfNulls<Int>(cur_minor.m + 1)
            val temp_col = arrayOfNulls<Int>(cur_minor.m + 1)
            for (k in 0 until cur_minor.m) {
                temp_str[k] = cur_str[k]
                temp_col[k] = cur_col[k]
            }
            var i = 0
            while (i < m && !end) {
                var j = 0
                while (j < n && !end) {
                    if (!Arrays.asList(*cur_col).contains(i) && !Arrays.asList(*cur_str).contains(j)) {
                        temp_str[cur_minor.m] = i
                        temp_col[cur_minor.m] = j
                        val minor = minor(temp_str, temp_col)
                        minor.log_this("Проверим минор.")
                        var det = createNumber(0.0)
                        try {
                            det = minor.determinant()
                        } catch (ignored: NON_QUADRATIC_MATRIX) {
                        } catch (ignored: INVALID_NUMBER_STRING) {
                        }
                        if (det != createNumber(0.0)) {
                            cur_minor = minor
                            cur_minor.log_this("Так как этот минор не равен нулю, то теперь мы будем рассматривать миноры, которые включают его")
                            cur_str = temp_str
                            cur_col = temp_col
                            end = true
                        }
                    }
                    j++
                }
                i++
            }
        }
        cur_minor.log_this("Этот минор является базисным.")
        return cur_minor.m
    }

    fun Transposition() {
        val zero = createNumber(0.0)
        val new_arr = createRectangleArrayList<Ring>(zero, n, m)
        for (i in 0 until m) for (j in 0 until n) new_arr[j][i] = arr[i][j]
        arr = new_arr
        val temp = n
        n = m
        m = temp
    }

    @Throws(INVALID_NUMBER_STRING::class)
    open fun rank(): Int {
        return if (getSettings() === Rank.ELEMENTAL_ROW) {
            rank_with_triangle()
        } else 0
    }

    @Throws(
        MATRIX_DIMENSION_MISSMATCH::class,
        INVALID_NUMBER_STRING::class,
        DEGENERATE_MATRIX::class,
        NON_QUADRATIC_MATRIX::class
    )
    fun getInverse() : Matrix {
        if (Inverse.getSettings() == GAUSS ){
            return get_inverse_gauss()
        }
        else{
            TODO("Не реализовано нахождение через алгебраические дополнения")
        }
    }
    fun get_inverse_gauss(): Matrix {
        return if (m == n) {
            val single = Matrix(m)
            val copy = Matrix(arr)
            val temp = AugmentedMatrix(copy, single)
            temp.log_this("Чтобы найти обратную матрицу методом Гаусса необходимо дописать справа от неё единичную и методом Гаусса добиться того, чтобы слева была единичная.")
            temp.gauss_transformation()
            if (temp.get_main().is_single()) temp.get_augmentation() else throw DEGENERATE_MATRIX()
        } else throw NON_QUADRATIC_MATRIX()
    }

    override fun plus(right: Ring): Ring {
        if (right is Matrix){
            val left: Matrix = this
            if (left.m == right.m && left.n == right.n) {
                val m = left.m
                val n = left.n
                val zero = createNumber(0.0)
                val arr = createRectangleArrayList<Ring>(zero, m, n)
                for (i in 0 until m) for (j in 0 until n) arr[i][j] = left.arr[i][j] + right.arr[i][j]
                return Matrix(arr)
            } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun minus(right: Ring): Ring {
        if (right is Matrix){
            val left: Matrix = this
            if (left.m == right.m && left.n == right.n) {
                val m = left.m
                val n = left.n
                val zero = createNumber(0.0)
                val arr = createRectangleArrayList<Ring>(zero, m, n)
                for (i in 0 until m) for (j in 0 until n) arr[i][j] = left.arr[i][j] - right.arr[i][j]
                return Matrix(arr)
            } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun times(right: Ring): Ring {
        if (right is Dec_Number || right is FractionalNumber){
            val copy : Matrix = Matrix(arr)
            for (i in 0 until m) copy.mult_string(i, right)
            return copy
        }
        else if (right is Matrix){
            val left: Matrix = this
            if (left.n == right.m) {
                val m = left.m
                val n = left.n
                val p = right.n
                val zero = createNumber(0.0)
                val arr = createRectangleArrayList<Ring>(zero, m, p)
                for (i in 0 until m) for (j in 0 until p) for (k in 0 until n) arr[i][j] += left.arr[i][k] * right.arr[k][j]
                return Matrix(arr)
            } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun div(right: Ring): Ring {
        if (right is Dec_Number || right is FractionalNumber){
            val copy : Matrix = Matrix(arr)
            for (i in 0 until m) copy.div_string(i, right)
            return copy
        }
        else if (right is Matrix){
            val left: Matrix = this
            if (left.n == right.m && right.m == right.n) {
                Log.add("", " чтобы разделить матрицу на матрицу, надо умножить левую матрицу на противоположную правой")
                val real_right = right.getInverse()
                return left*real_right
            } else throw MRV.MATRIX_DIMENSION_MISSMATCH()
        }
        else throw MRV.NON_COMPLIANCE_TYPES()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix) {
            if (m == other.m && n == other.n){
                for (i in 0 until m) for (j in 0 until n) if (arr[i][j]!=other.arr[i][j]) return false
                return true
            }
            else return false
        }
        else return false
    }

    override fun unaryMinus() : Ring{
        val copy = Matrix(arr)
        for (i in 0 until m) copy.mult_string(i, createNumber(-1.0))
        return copy
    }

    override fun toString(): String {
        var decode = ""
        for (i in 0 until m) {
            for (j in 0 until n) decode += arr[i][j].toString() + " "
            decode += "\n"
        }
        return decode
    }
}