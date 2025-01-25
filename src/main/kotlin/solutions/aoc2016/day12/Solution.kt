package solutions.aoc2016.day12

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
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
    return solve(split, registers)
}

fun part2(inputLines: List<String>): Int {
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
    registers['c'] = 1
    return solve(split, registers)
}

fun solve(split: List<List<String>>, registers: MutableMap<Char, Int>): Int {
    var index = 0
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
        }
        index++
    }
    return registers['a']!!
}
