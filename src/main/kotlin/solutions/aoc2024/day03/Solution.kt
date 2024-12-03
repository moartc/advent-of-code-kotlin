package solutions.aoc2024.day03

import utils.Resources


fun main() {

    val inputLines = Resources.getLines(2024, 3)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {
    val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    var sum = 0L
    inputLines.forEach {
        regex.findAll(it).forEach {
            val list = it.destructured.toList()
            val res = list[0].toLong() * list[1].toLong()
            sum += res
        }
    }
    return sum
}

fun part2(inputLines: List<String>): Long {

    val regex = Regex("""do\(\)|don't\(\)|mul\((\d{1,3}),(\d{1,3})\)""")
    var sum = 0L
    var wasDo = true
    inputLines.forEach {
        regex.findAll(it).forEach {
            if (it.value.equals("do()")) {
                wasDo = true
            } else if (it.value.equals(("don't()"))) {
                wasDo = false
            } else {
                if (wasDo) {
                    val list = it.destructured.toList()
                    val res = list[0].toLong() * list[1].toLong()
                    sum += res
                }
            }
        }
    }
    return sum
}