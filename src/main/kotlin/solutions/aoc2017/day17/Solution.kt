package solutions.aoc2017.day17

import utils.Resources

fun main() {
    val input = Resources.getLine(2017, 17).toInt()
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: Int): Int {
    var currentIdx = 0
    var currentValue = 0
    val list = mutableListOf<Int>()
    repeat(2018) {
        list.add(currentIdx, currentValue)
        currentIdx = (((currentIdx + input) % list.size) % list.size) + 1
        currentValue++
    }
    return list[list.indexOf(2017) + 1]
}


fun part2(input: Int): Int {
    var currentIdx = 0
    var currentValue = 0
    var listSize = 0
    var currIdxOf1 = 0
    repeat(50000001) {
        if (currentIdx == 1) {
            currIdxOf1 = currentValue
        }
        listSize++
        currentIdx = (((currentIdx + input) % listSize) % listSize) + 1
        currentValue++
    }
    return currIdxOf1
}
