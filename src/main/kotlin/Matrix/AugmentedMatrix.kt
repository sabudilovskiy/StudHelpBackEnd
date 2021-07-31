package Matrix

import AffineSpace.AffineSpace
import MathObject.Ring
import Number.FractionalNumber
import LinearSpace.LinearSpace
import Logger.Log.add
import MRV.MRV
import MRV.MRV.HAVE_NOT_SOLUTIONS
import MRV.MRV.INVALID_NUMBER_STRING
import MRV.MRV.MATRIX_DIMENSION_MISSMATCH
import MRV.MRV.NON_SINGLE
import MathObject.MathObject.MathObject
import Number.createNumber
import Parameters.SLE
import Point.Point
import Settings.matrix.SLE.getSettings
import Support.createRectangleArrayList
import Support.createSingleArrayList

class AugmentedMatrix : Matrix {
    protected var augmented_n = 0
    lateinit var augmented_arr: ArrayList<ArrayList<Ring>>
    constructor(arr: ArrayList<ArrayList<Ring>>, _augmented_arr: ArrayList<ArrayList<Ring>>) : super(arr) {
        val augmented_m = _augmented_arr.size
        if (augmented_m == m) {
            if (augmented_m > 0) augmented_n = _augmented_arr[0].size else augmented_n = 0
            augmented_arr = createRectangleArrayList<Ring>(createNumber(0.0), augmented_m, augmented_n)
            for (i in 0 until augmented_m) for (j in 0 until augmented_n) augmented_arr[i][j] = _augmented_arr[i][j]
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }
    constructor(left: Matrix, right: Matrix) : super(left.arr) {
        if (left.m == right.m) {
            val _arr : ArrayList<ArrayList<Ring>> = left.arr
            val _augmented_arr : ArrayList<ArrayList<Ring>> = right.arr
            val temp = AugmentedMatrix(_arr, _augmented_arr)
            augmented_arr = temp.augmented_arr
            augmented_n = temp.n
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun summ_strings(a: Int, b: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] + augmented_arr[b][i] * k
        super.summ_strings(a, b, k)
    }

    public fun get_augmentation(): Matrix {
        return Matrix(augmented_arr)
    }

    public fun get_main(): Matrix {
        return Matrix(arr)
    }

    public override fun toString(): String {
        var decode = ""
        for (i in 0 until m) {
            for (j in 0 until n) decode += arr.get(i).get(j).toString() + " "
            decode += " | "
            for (j in 0 until augmented_n) decode += augmented_arr[i][j].toString() + " "
            decode += "\n"
        }
        return decode
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun mult_string(a: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] * k
        super.mult_string(a, k)
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun div_string(a: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] / k
        super.div_string(a, k)
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun rank(): Int {
        val temp_arr = createRectangleArrayList<Ring>(FractionalNumber(0.0), m, n+augmented_n)
        for (i in 0 until m) for (j in 0 until n + augmented_n) {
            if (j < n) temp_arr[i][j] = arr[i][j] else temp_arr[i][j] = augmented_arr[i][j - n]
        }
        val temp = Matrix(temp_arr)
        return temp.rank()
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun is_null_string(a: Int): Boolean {
        var augmented_check = true
        return if (0 <= a && a < m) {
            for (i in 0 until augmented_n) {
                if (!augmented_arr[a][i].equals( 0.0)) {
                    augmented_check = false
                    break
                }
            }
            if (augmented_check) super.is_null_string(a) else false
        } else throw INVALID_NUMBER_STRING()
    }

    override fun delete_string(a: Int) {
        val zero = createNumber(0.0)
        val temp = createRectangleArrayList<Ring>(zero, m-1, augmented_n)
        for (i in 0 until a) for (j in 0 until augmented_n) temp[i][j] = augmented_arr[i][j]
        for (i in a + 1 until m) for (j in 0 until augmented_n) temp[i - 1][j] = augmented_arr[i][j]
        augmented_arr = temp
        super.delete_string(a)
    }

    protected fun reset_augmented() {
        augmented_arr = createRectangleArrayList(createNumber(0.0), m, 1)
    }

    protected fun is_homogeneous(): Boolean {
        if (augmented_n > 1) return false
        for (i in 0 until m) if (!augmented_arr[i][0].equals( 0.0)) return false
        return true
    }

    @Throws(MATRIX_DIMENSION_MISSMATCH::class, NON_SINGLE::class)
    fun substituion(array: ArrayList<Ring>): Matrix {
        if (n - m == array.size && augmented_n == 1) {
            if (is_single()) {
                val cof : ArrayList<Ring> = createSingleArrayList<Ring> (createNumber(0.0), n)
                for (i in m until n) cof[i] = array[i - m]
                for (i in 0 until m) {
                    var temp1 = "x" + (i + 1) + " = "
                    var temp2 = temp1
                    for (j in m until n) {
                        //если и коэффициент в матрице ненулевой и подставляется не ноль у соответствующей переменной
                        if (!arr[i][j].equals( 0.0)&& !cof[j].equals( 0.0)) {
                            temp1 += "-a" + (i + 1) + (j + 1) + " * " + "x" + (j + 1) + " + "
                            temp2 += (-arr[i][j]).toString() + " * " + cof[j] + " + "
                            cof[i] += -arr[i][j] * cof[j]
                        }
                    }
                    if (!augmented_arr[i][0].equals( 0.0)) {
                        temp1 += "b" + (i + 1)
                        temp2 += augmented_arr[i][0]
                        cof[i] = cof[i] + augmented_arr[i][0]
                    } else {
                        temp1 = temp1.substring(0, temp1.length - 3)
                        temp2 = temp2.substring(0, temp2.length - 3)
                    }
                    add(temp1, "")
                    add(temp2, "")
                    add("x" + (i + 1) + " = " + cof[i], "")
                }
                return create_vector_str(cof)
            } else throw NON_SINGLE()
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(
        MATRIX_DIMENSION_MISSMATCH::class,
        INVALID_NUMBER_STRING::class,
        HAVE_NOT_SOLUTIONS::class,
        NON_SINGLE::class,
    )
    fun solve_system():MathObject {
        return if (augmented_n == 1) {
            val copy : AugmentedMatrix = AugmentedMatrix(arr, augmented_arr)
            if (m == n && getSettings() === SLE.KRAMER_RULE) {
                TODO("")
            } else {
                add("", "Решим систему методом Гаусса")
                copy.gauss_transformation()
                copy.reduce_null_strings()
                if (m == n) {
                    val answer : ArrayList<Ring> = createSingleArrayList<Ring>(createNumber(0.0), n)
                    for (i in 0 until n) if (arr[i][i].equals(1.0)) answer[i] = augmented_arr[0][i] else throw HAVE_NOT_SOLUTIONS()
                    return Point(answer)
                } else {
                    val base = createSingleArrayList<Matrix>(Matrix(1), n-m);
                    if (is_homogeneous()) {
                        add(
                            "",
                            "Так как СЛАУ является однородной и прямоугольной, то она задаёт линейное подпространство(оболочку). Найдём базис."
                        )
                        for (i in 0 until n - m) {
                            val cords_vector = createSingleArrayList(createNumber(0.0),n - m)
                            cords_vector[i] = createNumber(1.0)
                            for (j in m until n) add("x" + (j + 1) + " = " + cords_vector[j], "")
                            for (j in 0 until m) {
                                base[i] = substituion(cords_vector)
                                base[i].log_this("")
                            }
                        }
                        return LinearSpace(base)
                    } else {
                        add(
                            "",
                            "Так как СЛАУ является неоднородной и прямоугольной, то она задаёт линейное многообразие. Найдём базис."
                        )
                        add("", "Найдём частное решение")
                        val v = substituion(createSingleArrayList(createNumber(0.0), n - m))
                        v.log_this("Это вектор, на который перенесёно линейное подпространство")
                        copy.reset_augmented()
                        copy.log_this("Найдём базис соответствующего пространства. Для этого обнулим столбец свободных членов.")
                        for (i in 0 until n - m) {
                            val cords_vector = createSingleArrayList<Ring>(createNumber(0.0),n - m)
                            cords_vector[i] = createNumber(1.0)
                            for (j in m until n) add("x" + (j + 1) + " = " + cords_vector[j - m], "")
                            for (j in 0 until m) {
                                base[i] = substituion(cords_vector)
                                base[i].log_this("")
                            }
                        }
                        var temp : AffineSpace? = null
                        try {
                             temp = AffineSpace(v, base)
                        }
                        catch (matrix_error : MRV.MATRIX_ERROR){
                            println("Этого не должно было произойти")
                        }
                        return temp!!
                    }
                }
            }
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

}