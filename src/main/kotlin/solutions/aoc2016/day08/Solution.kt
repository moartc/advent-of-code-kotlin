package solutions.aoc2016.day08

import utils.Resources
import utils.array.extensions.printDoubleArray
import utils.array.extensions.shiftColumn
import utils.array.extensions.shiftRight

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val array = Array(6) { Array(50) { '.' } }

    inputLines.forEach { line ->
        if (line.startsWith("rect")) {
            val (x, y) = line.split(" ")[1].split("x")
            val xI = x.toInt()
            val yI = y.toInt()
            for (y in 0..<yI) {
                for (x in 0..<xI) {
                    array[y][x] = '#'
                }
            }
        } else if (line.startsWith("rotate row")) {
            val spl = line.split(" ")
            val yRow = spl[2].split("=")[1].toInt()
            val by = spl[4].toInt()
            array[yRow] = array[yRow].shiftRight(by)

        } else if (line.startsWith("rotate column")) {
            val spl = line.split(" ")
            val xCol = spl[2].split("=")[1].toInt()
            val by = spl[4].toInt()
            array.shiftColumn(xCol, by)
        }
    }

    return array.sumOf { x -> x.count { it == '#' } }
}

fun part2(inputLines: List<String>): String {

    val array = Array(6) { Array(50) { ' ' } }

    inputLines.forEach { line ->
        if (line.startsWith("rect")) {
            val (x, y) = line.split(" ")[1].split("x")
            val xI = x.toInt()
            val yI = y.toInt()
            for (y in 0..<yI) {
                for (x in 0..<xI) {
                    array[y][x] = '#'
                }
            }
        } else if (line.startsWith("rotate row")) {
            val spl = line.split(" ")
            val yRow = spl[2].split("=")[1].toInt()
            val by = spl[4].toInt()
            array[yRow] = array[yRow].shiftRight(by)

        } else if (line.startsWith("rotate column")) {
            val spl = line.split(" ")
            val xCol = spl[2].split("=")[1].toInt()
            val by = spl[4].toInt()
            array.shiftColumn(xCol, by)
        }
    }
    printDoubleArray(array)
    /*
    # # # # . # # # # . # . . # . # # # # . . # # # . # # # # . . # # . . . # # . . # # # . . . # # . .
    . . . # . # . . . . # . . # . # . . . . # . . . . # . . . . # . . # . # . . # . # . . # . # . . # .
    . . # . . # # # . . # # # # . # # # . . # . . . . # # # . . # . . # . # . . . . # . . # . # . . # .
    . # . . . # . . . . # . . # . # . . . . . # # . . # . . . . # . . # . # . # # . # # # . . # . . # .
    # . . . . # . . . . # . . # . # . . . . . . . # . # . . . . # . . # . # . . # . # . . . . # . . # .
    # # # # . # . . . . # . . # . # . . . . # # # . . # . . . . . # # . . . # # # . # . . . . . # # . .
     */
    return "ZFHFSFOGPO"
}