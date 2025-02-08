package solutions.aoc2016.day25

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)
    println("part1 = ${part1(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    val split = inputLines.map { it.split(" ") }
    val registers = mutableMapOf<Char, Int>()
    split.forEach {
        if (it[1].toIntOrNull() == null) {
            registers[(it[1][0])] = 0
        }
        if (it.size > 2 && it[2].toIntOrNull() == null) {
            registers[(it[2][0])] = 0
        }
    }

    fun checkRes(list: MutableList<Int>): Boolean {
        if (list.size == 30) {
            var exp = 0
            for (re in list) {
                if (re != exp) {
                    return false
                } else {
                    exp = if (exp == 0) 1 else 0
                }
            }
        } else {
            return false
        }
        return true
    }

    repeat(Int.MAX_VALUE) {
        registers['a'] = it
        if (checkRes(solve(split, registers))) {
            return it
        }
    }
    return -1
}


fun solve(split: List<List<String>>, registers: MutableMap<Char, Int>): MutableList<Int> {
    var index = 0
    val collected = mutableListOf<Int>()
    while (index <= split.lastIndex) {
        val input = split[index]
        val instr = input[0]
        val inpX = input[1]
        if (instr == "cpy") {
            val inpY = input[2]
            if (inpX.toIntOrNull() != null) {
                registers[inpY.first()] = inpX.toIntOrNull()!!
            } else {
                val valToCpy = registers[inpX.first()]!!
                registers[inpY.first()] = valToCpy
            }
        } else if (instr == "inc") {
            registers[inpX.first()] = registers[inpX.first()]!! + 1

        } else if (instr == "dec") {
            registers[inpX.first()] = registers[inpX.first()]!! - 1
        } else if (instr == "jnz") {
            if (inpX.toIntOrNull() != null) {
                if (inpX.toIntOrNull()!! != 0) {
                    index += input[2].toInt()
                    continue
                }
            } else {
                if (registers[inpX.first()]!! != 0) {
                    index += input[2].toInt()
                    continue
                }
            }
        } else if (instr == "out") {
            if (inpX.toIntOrNull() != null) {
                collected.add(inpX.toInt())
            } else {
                val toAdd = registers[inpX.first()]!!
                collected.add(toAdd)
            }
            if (collected.size == 30) {
                // let's say it's enough
                return collected
            }
        }
        index++
    }
    return mutableListOf()
}