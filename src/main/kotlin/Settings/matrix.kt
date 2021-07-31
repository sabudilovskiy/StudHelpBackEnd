package Settings

import MRV.MRV
import Parameters.Det
import Parameters.Rank
import Support.createSingleArrayList

object matrix {
    object Det {
        private val settings : ArrayList<Parameters.Det> = arrayListOf(Parameters.Det.BASIC, Parameters.Det.BASIC, Parameters.Det.TRIANGLE)
        private var border = 4
        fun setdefaultSettings() {
            settings.clear()
            settings.add(Parameters.Det.BASIC)
            settings.add(Parameters.Det.BASIC)
            settings.add(Parameters.Det.SARUSS)
        }
        fun setSettings(second: Parameters.Det, thirst: Parameters.Det, border: Int) {
            settings.clear()
            settings.add(Parameters.Det.BASIC)
            settings.add(second)
            settings.add(thirst)
            Det.border = border
        }
        fun setBorder(border: Int) {
            Det.border = border
        }
        @Throws(MRV.MATRIX_DIMENSION_MISSMATCH::class)
        fun get_det_method(n: Int): Parameters.Det {
            if (n > 0) {
                if (n >= border) return Parameters.Det.TRIANGLE
                else {
                    if (n > 3) return Parameters.Det.LAPLASS else return settings[n - 1]
                }
            } else {
                throw MRV.MATRIX_DIMENSION_MISSMATCH()
            }
        }
    }

    object Rank {
        private var Settings = Parameters.Rank.ELEMENTAL_ROW
        fun setSettings(_settings: Parameters.Rank) {
            Settings = _settings
        }
        fun getSettings(): Parameters.Rank {
            return Settings
        }
    }

    object Inverse{
        private var Settings = Parameters.Inverse.GAUSS
        fun getSettings() : Parameters.Inverse{
            return Settings
        }
        fun setSettings(_settings: Parameters.Inverse){
            Settings = _settings
        }
    }
    object SLE {
        private var Settings = Parameters.SLE.GAUSS
        fun getSettings(): Parameters.SLE {
            return Settings
        }

        fun setSettings(settings: Parameters.SLE) {
            Settings = settings
        }
    }
}