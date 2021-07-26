import CanBeInMatrix.CanBeInMatrix
import Logger.Log.print_log
import MRV.MRV
import MRV.MRV.HAVE_NOT_SOLUTIONS
import MRV.MRV.INVALID_NUMBER_STRING
import MRV.MRV.MATRIX_DIMENSION_MISSMATCH
import MRV.MRV.NON_SINGLE
import MathObject.MathObject.MathObject
import Matrix.AugmentedMatrix
import Number.newNumber
import Settings.matrix.Det.setBorder
import Settings.matrix.Det.setdefaultSettings
import Support.newQuadraticArrayList
import java.util.*

object Test_Matrix {
    @Throws(
        MATRIX_DIMENSION_MISSMATCH::class,
        INVALID_NUMBER_STRING::class,
        HAVE_NOT_SOLUTIONS::class,
        NON_SINGLE::class
    )
    @JvmStatic
    fun main(args: Array<String>) {
        val scan = Scanner(System.`in`)
        val m: Int
        println("Введите m: ")
        m = scan.nextLine().toInt()
        val n: Int
        println("Введите n: ")
        n = scan.nextLine().toInt()
        val arr = newQuadraticArrayList<CanBeInMatrix>(newNumber(0.0), m, n)
        val augmenated_arr = newQuadraticArrayList<CanBeInMatrix>(newNumber(0.0), m, 1)
        for (i in 0 until m) {
            var temp = "Введите " + (i + 1) + " строку: "
            println(temp)
            val buffer = scan.nextLine().split(" ".toRegex()).toTypedArray()
            for (j in 0 until n) arr[i][j] = newNumber(buffer[j].toDouble())
            temp = "Введите " + (i + 1) + " свободный член: "
            println(temp)
            augmenated_arr[i][0] = newNumber(scan.nextLine().toDouble())
        }
        val temp = AugmentedMatrix(arr, augmenated_arr)
        setdefaultSettings()
        setBorder(4)
        var temp2 : MathObject? = null
        try {
            temp2 = temp.solve_system()
        } catch (ignored: MATRIX_DIMENSION_MISSMATCH) {
        } catch (ignored: MRV.NON_COMPLIANCE_TYPES) {
        } finally {
            temp2?.log_this("")
            print_log()
        }
    }
}