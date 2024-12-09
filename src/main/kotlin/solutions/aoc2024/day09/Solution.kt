package solutions.aoc2024.day09

import utils.Resources
import utils.collections.extensions.findIndexed


fun main() {

    val inputLines = Resources.getLines(2024, 9)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val input = inputLines[0]
    val blocks = mutableListOf<Int>()
    var currIdx = 0


    for ((index, c) in input.withIndex()) {
        if (index % 2 == 0) { // field
            repeat(c.digitToInt()) { blocks.add(currIdx) }
            currIdx++
        } else { // empty
            repeat(c.digitToInt()) { blocks.add(-1) }
        }
    }
    var left = 0
    var right = blocks.lastIndex

    while (left <= right) {
        val leftBlock = blocks[left]
        if (leftBlock == -1) {
            while (blocks[right] == -1) {
                right--
            }
            val charToMove = blocks[right]
            blocks[right] = -1
            right--
            blocks[left] = charToMove
            left++
        } else {
            left++
        }
    }
    var finAnswer = 0L
    var printIndex = 0L
    for (value in blocks) {
        if (value != -1) {
            finAnswer += (printIndex * value)
            printIndex++
        }
    }
    return finAnswer
}

fun part2(inputLines: List<String>): Long {

    val inp = inputLines[0]
    var currIdx = 0
    var sizeToValue = mutableListOf<Pair<Int, Int>>()
    for ((index, c) in inp.withIndex()) {
        if (index % 2 == 0) { // field
            sizeToValue.add(c.digitToInt() to currIdx)
            currIdx++
        } else {
            sizeToValue.add(c.digitToInt() to -1)
        }
    }
    fun tryToMove(numToValue: MutableList<Pair<Int, Int>>, idOfElemToMove: Int): MutableList<Pair<Int, Int>> {
        val elToMoveWithIdx = numToValue.findIndexed { x -> x.second == idOfElemToMove }!!
        val idxOfElemToMove = elToMoveWithIdx.first
        val elToMove = elToMoveWithIdx.second
        val idxOfFreeSpace =
            numToValue.withIndex().indexOfFirst { (i, el) -> el.second == -1 && el.first >= elToMove.first && i < idxOfElemToMove }
        if (idxOfFreeSpace != -1) {
            val freeSpace = numToValue[idxOfFreeSpace]
            val spaceSize = freeSpace.first
            val remainingSize = spaceSize - elToMove.first
            // add at the beginning of a free space
            numToValue.add(idxOfFreeSpace, elToMove)
            // update remaining
            numToValue[idxOfFreeSpace + 1] = remainingSize to -1
            // set moved elem to empty
            numToValue[idxOfElemToMove + 1] = elToMove.first to -1
        }
        return numToValue
    }

    val idOfLastElement = sizeToValue.last { x -> x.second != -1 }.second
    for (i in idOfLastElement downTo 0) {
        sizeToValue = tryToMove(sizeToValue, i)
    }

    var finAnswer = 0L
    var globIdx = 0L
    sizeToValue.forEach { stv ->
        if (stv.second != -1) {
            repeat(stv.first) {
                finAnswer += (globIdx * stv.second)
                globIdx++
            }
        } else {
            globIdx += stv.first
        }
    }
    return finAnswer
}


