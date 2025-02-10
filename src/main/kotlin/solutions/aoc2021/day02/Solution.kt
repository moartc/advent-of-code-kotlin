package solutions.aoc2021.day02

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    var hor = 0
    var dep = 0
    inputLines.forEach { l ->
        val (f, s) = l.split(" ")
        val sV = s.toInt()
        when (f) {
            "forward" -> hor += sV
            "down" -> dep += sV
            "up" -> dep -= sV
        }
    }
    return hor * dep
}

fun part2(inputLines: List<String>): Any {
    var hor = 0
    var dep = 0
    var aim = 0
    inputLines.forEach { l ->
        val (f, s) = l.split(" ")
        val sV = s.toInt()
        when (f) {
            "forward" -> {
                hor += sV
                dep += (aim * sV)
            }

            "down" -> aim += sV
            "up" -> aim -= sV
        }
    }
    return hor * dep
}
