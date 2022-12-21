package solutions.aoc2022.day21

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 21)

    println("part1 = ${part1(input)}")
    println("part2 to solve in wolfram alpha = ${part2(input)}")
}


fun part2(input: List<String>): String {

    val registerM = mutableMapOf<String, String>()
    val fixedInput = input.map { i ->
        if (i.contains("root")) i.replace("+", "=")
        else if(i.contains("humn")) {
            val split = i.split(" ")
            split[0] + " x"
        } else {
            i
        }
    }
    val ops = parse(fixedInput)

    ops.forEach { i ->
        when (i) {
            is Yell -> registerM[i.monkey] = i.value.toString()
            is Yell2 -> registerM[i.monkey] = i.value
        }
    }

    val register = registerM.toMutableMap()
    while (!register.containsKey("root")) {
        ops.forEach {
            when (it) {
                is Complex -> {
                    val fM = it.first
                    val sM = it.second
                    if (register.containsKey(fM) && register.containsKey(sM)) {
                        val op = it.operation
                        when (op) {
                            '+' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff.equals("0"))
                                        register[it.monkey] = fs
                                    else if (fs.equals("0"))
                                        register[it.monkey] = ff
                                    else
                                        register[it.monkey] = "(" + ff + "+" + fs + ")"

                                } else {
                                    val res = ff.toLong() + fs.toLong()
                                    register[it.monkey] = res.toString()
                                }

                            }

                            '-' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff.equals("0"))
                                        register[it.monkey] = fs
                                    else if (fs.equals("0"))
                                        register[it.monkey] = ff
                                    else
                                        register[it.monkey] = "(" + ff + "-" + fs + ")"
                                } else {
                                    val res = ff.toLong() - fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '/' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff == ("0"))
                                        register[it.monkey] = "0"
                                    else
                                        register[it.monkey] = "(" + ff + "/" + fs + ")"
                                } else {
                                    val res = ff.toLong() / fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '*' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff == ("0") || fs == "0")
                                        register[it.monkey] = "0"
                                    else
                                        register[it.monkey] = "(" + ff + "*" + fs + ")"
                                } else {
                                    val res = ff.toLong() * fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '=' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                register[it.monkey] = "(" + ff + "=" + fs + ")"

                            }
                        }
                    }
                }
            }
        }
    }
    val res = register["root"]!!
    return res


}


fun parse(input: List<String>): List<Op> {
    val list = input.map {
        val vals = it.split(" ")
        if (vals.size == 2) {
            val name = vals[0].dropLast(1)
            val value = vals[1]
            if (value == "x")
                Yell2(name, value)
            else
                Yell(name, value.toLong())
        } else {
            val name = vals[0].dropLast(1)
            val fMonk = vals[1]
            val sMonk = vals[3]
            val operator = vals[2]
            Complex(name, fMonk, operator[0], sMonk)
        }
    }
    return list

}

fun solve(ops: List<Op>, register: MutableMap<String, String>): String {
    while (!register.containsKey("root")) {
        ops.forEach {
            when (it) {
                is Complex -> {
                    val fM = it.first
                    val sM = it.second
                    if (register.containsKey(fM) && register.containsKey(sM)) {
                        val op = it.operation
                        when (op) {
                            '+' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff.equals("0"))
                                        register[it.monkey] = fs
                                    else if (fs.equals("0"))
                                        register[it.monkey] = ff
                                    else
                                        register[it.monkey] = "(" + ff + "+" + fs + ")"

                                } else {
                                    val res = ff.toLong() + fs.toLong()
                                    register[it.monkey] = res.toString()
                                }

                            }

                            '-' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff.equals("0"))
                                        register[it.monkey] = fs
                                    else if (fs.equals("0"))
                                        register[it.monkey] = ff
                                    else
                                        register[it.monkey] = "(" + ff + "-" + fs + ")"
                                } else {
                                    val res = ff.toLong() - fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '/' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff == ("0"))
                                        register[it.monkey] = "0"
                                    else
                                        register[it.monkey] = "(" + ff + "/" + fs + ")"
                                } else {
                                    val res = ff.toLong() / fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '*' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                if (ff.contains("x") || fs.contains("x")) {
                                    if (ff == ("0") || fs == "0")
                                        register[it.monkey] = "0"
                                    else
                                        register[it.monkey] = "(" + ff + "*" + fs + ")"
                                } else {
                                    val res = ff.toLong() * fs.toLong()
                                    register[it.monkey] = res.toString()
                                }
                            }

                            '=' -> {
                                val ff = register[fM]!!
                                val fs = register[sM]!!
                                register[it.monkey] = "(" + ff + "=" + fs + ")"

                            }
                        }
                    }
                }
            }
        }
    }
    val res = register["root"]!!
    return res
}
fun part1(input: List<String>): String {

    val register = mutableMapOf<String, String>()
    val ops = parse(input)
    ops.forEach { i ->
        when (i) {
            is Yell -> register[i.monkey] = i.value.toString()
        }
    }
    return solve(ops, register)
}

abstract class Op(val monkey: String)
class Yell2(monkey: String, val value: String) : Op(monkey)
class Yell(monkey: String, val value: Long) : Op(monkey)
class Complex(monkey: String, val first: String, val operation: Char, val second: String) : Op(monkey)
