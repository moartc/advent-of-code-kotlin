package solutions.aoc2022.day10

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 10)

    println("part1 = ${part1(input)}")
    println("part2 (to read on console):\n")
    part2(input)
}

fun part1(input: List<String>): Int {
    var cycle = 0
    var regX = 1
    val signals = mutableListOf<Int>()
    input.forEach { inst ->
        cycle += 1
        if ((cycle - 20) % 40 == 0) {
            signals.add(regX * cycle)
        }
        if (inst.startsWith("addx")) {
            cycle += 1
            if ((cycle - 20) % 40 == 0) {
                signals.add(regX * cycle)
            }
            val instVal = inst.split(" ").last().toInt()
            regX += instVal
        }
    }
    return signals.sum()
}

fun part2(input: List<String>) {

    var cycle = 0
    var x = 1
    var crtPos = 0
    input.forEach { inst ->
        cycle += 1
        crtPos = printAndUpdateCrt(crtPos, x, cycle)
        if (inst.startsWith("addx")) {
            cycle += 1
            crtPos = printAndUpdateCrt(crtPos, x, cycle)
            x += inst.split(" ").last().toInt()
        }
    }
}

fun printAndUpdateCrt(crtPos: Int, x: Int, cycle: Int): Int {
    if (crtPos - x >= -1 && crtPos - x <= 1) {
        print("#")
    } else {
        print(".")
    }
    if (cycle % 40 == 0) {
        println()
    }
    return if (crtPos + 1 == 40) 0 else crtPos + 1
}
