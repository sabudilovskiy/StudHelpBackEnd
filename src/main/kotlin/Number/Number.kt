package Number

import CanBeInMatrix.CanBeInMatrix
import Parameters.Number.PROPER

public fun newNumber(value : Double) : CanBeInMatrix{
    val cur_set : Parameters.Number = Settings.numbers.use_number;
    if (cur_set.equals(PROPER)) {
        return FractionalNumber(value)
    }
    else {
        return Dec_Number(value)
    }
}