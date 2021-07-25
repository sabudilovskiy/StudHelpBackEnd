package Support

import java.lang.IllegalArgumentException
import java.lang.Math.abs

inline fun <reified T> create_array_with_n_elements(something : Any, n : Int) : ArrayList <T> {
    if (something is T ){
        val array : ArrayList<T> = arrayListOf();
        if (n > 0) {
            var i : Int = 0;
            while (i++ < n) array.add(something)
            return array
        }
        else throw IllegalArgumentException()
    }
    else{
        throw IllegalArgumentException()
    }

}

fun find_GCD(a : Int, b : Int) : Int{
    var temp_a : Int = abs(a)
    var temp_b : Int = abs(b)
    while (temp_a!=0 && temp_b!=0) if (temp_a > temp_b) temp_a%=temp_b else temp_b%=temp_b
    return temp_a+temp_b
}