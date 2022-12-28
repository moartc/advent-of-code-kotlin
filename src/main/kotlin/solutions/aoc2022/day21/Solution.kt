import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 21)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Long {
    val ops = input.map {
        val vals = it.split(" ")
        if (vals.size == 2) {
            val name = vals[0].dropLast(1)
            val value = vals[1]
            Yell(name, value.toLong())
        } else {
            val name = vals[0].dropLast(1)
            val fMonk = vals[1]
            val sMonk = vals[3]
            val operator = vals[2]
            Complex(name, fMonk, operator[0], sMonk)
        }
    }
    return getValue("root", ops)
}

fun getValue(s: String, ops: List<Op>): Long {
    val m = ops.first { it.monkey == s }
    if (m is Yell)
        return m.value
    else if (m is Complex) {
        return when (m.operation) {
            '+' -> getValue(m.first, ops) + getValue(m.second, ops)
            '-' -> getValue(m.first, ops) - getValue(m.second, ops)
            '/' -> getValue(m.first, ops) / getValue(m.second, ops)
            else -> getValue(m.first, ops) * getValue(m.second, ops)
        }
    }
    return -1
}

fun part2(input: List<String>): Any {

    val ops = input.map {
        val vals = it.split(" ")
        if (vals.size == 2) {
            val name = vals[0].dropLast(1)
            val value = vals[1]
            if (name != "humn") {
                Yell(name, value.toLong())
            } else {
                YellX(name)
            }
        } else {
            val name = vals[0].dropLast(1)
            val fMonk = vals[1]
            val sMonk = vals[3]
            val operator = vals[2]
            Complex(name, fMonk, operator[0], sMonk)
        }
    }
    val root = ops.first { it.monkey == "root" } as Complex
    val rightSideValue = getValue(root.second, ops)
    return getHumnValue(root.first, ops, rightSideValue)
}

fun getHumnValue(reg: String, ops: List<Op>, rightSideValue: Long): Long {
    if (reg == "humn") {
        return rightSideValue
    } else {
        val r = ops.first { it.monkey == reg }
        if (r is Yell) {
            return r.value
        } else if (r is Complex) {
            val f = r.first
            val s = r.second
            if (isHumn(s, ops)) {
                return when (r.operation) {
                    '/' -> getHumnValue(s, ops, getValue(f, ops) / rightSideValue)  // 12/x = 3
                    '+' -> getHumnValue(s, ops, rightSideValue - getValue(f, ops))  // 12+x = 3
                    '-' -> getHumnValue(s, ops, getValue(f, ops) - rightSideValue)  // 12-x = 3
                    else -> getHumnValue(s, ops, rightSideValue / getValue(f, ops)) // 12*x = 3
                }
            } else if (isHumn(f, ops)) {
                return when (r.operation) {
                    '/' -> getHumnValue(f, ops, rightSideValue * getValue(s, ops))
                    '+' -> getHumnValue(f, ops, rightSideValue - getValue(s, ops))
                    '-' -> getHumnValue(f, ops, rightSideValue + getValue(s, ops))  // x-12 = 3
                    else -> getHumnValue(f, ops, rightSideValue / getValue(s, ops))
                }
            }
        }
    }
    return -12
}

fun isHumn(s: String, ops: List<Op>): Boolean {
    return if (s == "humn") {
        true
    } else {
        val op = ops.first { it.monkey == s }
        if (op is Yell) {
            false
        } else {
            isHumn((op as Complex).first, ops) || isHumn(op.second, ops)
        }
    }
}

abstract class Op(val monkey: String)
class Yell(monkey: String, val value: Long) : Op(monkey)
class YellX(monkey: String) : Op(monkey)
class Complex(monkey: String, val first: String, val operation: Char, val second: String) : Op(monkey)