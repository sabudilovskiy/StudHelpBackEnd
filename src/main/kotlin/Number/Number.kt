package Number

import MathObject.Division_Ring
import Parameters.Number.PROPER

public fun createNumber(value : Double) : Division_Ring {
    val cur_set : Parameters.Number = Settings.numbers.use_number;
    if (cur_set.equals(PROPER)) {
        return FractionalNumber(value)
    }
    else {
        return Dec_Number(value)
    }
}