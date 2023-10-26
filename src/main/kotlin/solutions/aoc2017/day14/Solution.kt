package solutions.aoc2017.day14

import utils.Resources

fun main() {
    val input = Resources.getLine(2017, 14)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: String): Int {

    return (0..127)
        .sumOf { it ->
            (solutions.aoc2017.day10.part2("$input-$it") as String).sumOf {
                hexToBinary(it.toString()).count { c -> c == '1' }
            }
        }
}

fun part2(input: String): Int {

    val array = (0..127).map { idx ->
        (solutions.aoc2017.day10.part2("$input-$idx") as String)
            .map { hexToBinaryPart2(it.toString()) }
            .joinToString("")
            .toCharArray()
    }
    return findGroups(array.toTypedArray())
}

fun findGroups(arr: Array<CharArray>): Int {

    var counter = 0

    fun find(rowIdx: Int, colIdx: Int, arr: Array<CharArray>, alreadyCounted: Boolean) {
        if (!(rowIdx in 0..127 && colIdx in 0..127)) {
            return
        }
        if (arr[rowIdx][colIdx] == '1') {
            if (!alreadyCounted) {
                counter++
            }
            arr[rowIdx][colIdx] = 'X' // change current char
            find(rowIdx - 1, colIdx, arr, true)
            find(rowIdx + 1, colIdx, arr, true)
            find(rowIdx, colIdx - 1, arr, true)
            find(rowIdx, colIdx + 1, arr, true)
        }
    }

    for (row in 0..127) {
        for (col in 0..127) {
            find(row, col, arr, false)
        }
    }
    return counter
}

fun hexToBinaryPart2(hex: String): String {
    return String.format("%4s", Integer.toBinaryString(hex.toInt(16))).replace(' ', '0')
}

fun hexToBinary(hex: String): String {
    return Integer.toBinaryString(hex.toInt(16))
}
