package Lexemes

abstract class Function {
    abstract fun count(args: ArrayList<Double>): Double
    open fun check(args: ArrayList<Double>): Boolean  = true;
}

internal class F_Sin : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.sin(args[0])
    }
}

internal class F_Cos : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.cos(args[0])
    }
}

internal class F_Tg : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.tan(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return if (Math.cos(args[0]) == 0.0) false else true
    }
}

internal class F_Ctg : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return 1 / Math.tan(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return if (Math.sin(args[0]) == 0.0) false else true
    }
}

internal class F_Abs : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.abs(args[0])
    }
}

internal class F_Arcsin : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.asin(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return -1 <= args[0] && args[0] <= 1
    }
}

internal class F_Arccos : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.acos(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return -1 <= args[0] && args[0] <= 1
    }
}

internal class F_Arctg : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.atan(args[0])
    }
}

internal class F_Arcctg : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.PI / 2 - Math.atan(args[0])
    }
}

internal class F_Exp : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.exp(args[0])
    }
}

internal class F_Ln : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.log(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return args[0] > 0
    }
}

internal class F_Log : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.log(args[1]) / Math.log(args[0])
    }

    override fun check(args: ArrayList<Double>): Boolean {
        return args[0] != 1.0 && args[0] > 0 && args[1] > 0
    }
}

internal class F_Pow : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return Math.pow(args[0], args[1])
    } //        @Override
    //        public boolean check(ArrayList<Double> args) {
    //                if (args.get(0) > 0) return true;
    //                else
    //                {
    //                        if (args.get(1) - trunc(args.get(1)) == 0)
    //                        {
    //                                if (args.get(0) != 0 || args.get(1) >= 0)
    //                                {
    //                                        return true;
    //                                }
    //                        }
    //                }
    //                return false;
    //        }
}

internal class F_Mult : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return args[0] * args[1]
    }
}

internal class F_Div : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return args[0] / args[1]
    }
    override fun check(args: ArrayList<Double>): Boolean {
        return args[1] != 0.0
    }
}

internal class F_Plus : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return args[0] + args[1]
    }
}

internal class F_Minus : Function() {
    override fun count(args: ArrayList<Double>): Double {
        return args[0] - args[1]
    }
}