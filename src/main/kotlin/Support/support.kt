package Support

import java.lang.IllegalArgumentException
import java.lang.Math.abs

//создаём массив с n одинаковых элементов. Первый аргумент - экземпляр, второй аргумент - количество
inline fun <reified T> createSingleArrayList(something : T, n : Int) : ArrayList <T> {
    val array : ArrayList<T> = arrayListOf();
    if (n > 0) {
        var i : Int = 0;
        while (i++ < n) array.add(something)
        return array
    }
    else throw IllegalArgumentException()

}
inline fun <reified T> createRectangleArrayList(something: T, m: Int, n: Int) : ArrayList<ArrayList<T>> {
    val array : ArrayList<ArrayList<T>> = arrayListOf()
    if (n > 0) {
        var i : Int = 0;
        while (i++ < m) array.add(createSingleArrayList(something, n))
        return array
    }
    else throw IllegalArgumentException()
}

fun find_GCD(a : Long, b : Long) : Long {
    var temp_a: Long = abs(a)
    var temp_b: Long = abs(b)
    while (temp_a != 0L && temp_b != 0L) if (temp_a > temp_b) temp_a %= temp_b else temp_b %= temp_a
    return temp_a + temp_b
}