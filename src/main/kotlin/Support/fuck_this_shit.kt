package Support

import java.lang.IllegalArgumentException

inline fun <reified T> fuck_this_shit(shit : Any, count : Int) : ArrayList <T> {
    if (shit is T ){
        var fuck : ArrayList<T> = arrayListOf();
        if (count > 0) {
            var i : Int = 0;
            while (i++ < count) fuck.add(shit)
            return fuck
        }
        else throw IllegalArgumentException()
    }
    else{
        throw IllegalArgumentException()
    }

}