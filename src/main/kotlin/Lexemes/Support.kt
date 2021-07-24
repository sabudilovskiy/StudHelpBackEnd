package Lexemes

object Support {
    fun union(left: ArrayList<Double>, right: ArrayList<Double>): ArrayList<Double> {
        return if (left.size != 0) {
            if (right.size == 0) left else {
                for (element in right) {
                    left.add(element)
                }
                left
            }
        } else right
    }

    fun is_numeral(a: Char): Boolean {
        return if ('0' <= a && a <= '9') true else false
    }
}