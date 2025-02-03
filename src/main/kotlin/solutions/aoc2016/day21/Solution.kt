package solutions.aoc2016.day21

import utils.Resources
import utils.array.extensions.shiftLeft
import utils.array.extensions.shiftRight
import utils.array.extensions.swap
import utils.parser.getInt
import utils.parser.getInts
import kotlin.math.abs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): String {

    var inputArray = "abcdefgh".toCharArray()

    inputLines.forEach { command ->
        if (command.startsWith("swap position")) {
            val (pos1, pos2) = command.getInts()
            inputArray.swap(pos1, pos2)
        } else if (command.startsWith("swap letter")) {
            val split = command.split(" ")
            val (l1, l2) = split[2][0] to split[5][0]
            for ((index, c) in inputArray.withIndex()) {
                if (c == l1) {
                    inputArray[index] = l2
                } else if (c == l2) {
                    inputArray[index] = l1
                }
            }
        } else if (command.startsWith("rotate left")) {
            val v = command.getInt()!!
            inputArray = inputArray.shiftLeft(v)

        } else if (command.startsWith("rotate right")) {
            val v = command.getInt()!!
            inputArray = inputArray.shiftRight(v)

        } else if (command.startsWith("reverse positions")) {
            var (left, right) = command.getInts()
            while (left < right) {
                val temp = inputArray[left]
                inputArray[left] = inputArray[right]
                inputArray[right] = temp
                left++
                right--
            }
        } else if (command.startsWith("move position")) {
            val (x, y) = command.getInts()
            if (x in inputArray.indices && y in inputArray.indices) {
                val temp = inputArray[x]
                if (x < y) {
                    for (i in x until y) {
                        inputArray[i] = inputArray[i + 1]
                    }
                } else {
                    for (i in x downTo y + 1) {
                        inputArray[i] = inputArray[i - 1]
                    }
                }
                inputArray[y] = temp

            }
        } else if (command.startsWith("rotate based on position")) {
            val x = command.split(" ").last()[0]
            val index = inputArray.indexOf(x)
            val rotations = 1 + index + if (index >= 4) 1 else 0
            inputArray = inputArray.shiftRight(rotations)
        }
    }
    return inputArray.joinToString("")

}

fun part2(inputLines: List<String>): String {

    var inputArray = "fbgdceah".toCharArray()

    inputLines.reversed().forEach { command ->
        if (command.startsWith("swap position")) {              // nothing to change
            val (pos1, pos2) = command.getInts()
            inputArray.swap(pos1, pos2)
        } else if (command.startsWith("swap letter")) {         // nothing to do
            val split = command.split(" ")
            val (l1, l2) = split[2][0] to split[5][0]
            for ((index, c) in inputArray.withIndex()) {
                if (c == l1) {
                    inputArray[index] = l2
                } else if (c == l2) {
                    inputArray[index] = l1
                }
            }
        } else if (command.startsWith("rotate left")) {     // replaced with rotate right
            val v = command.getInt()!!
            inputArray = inputArray.shiftRight(v)
        } else if (command.startsWith("rotate right")) {    // replaced with rotate left
            val v = command.getInt()!!
            inputArray = inputArray.shiftLeft(v)
        } else if (command.startsWith("reverse positions")) { // DONE - nothing to do
            var (left, right) = command.getInts()
            while (left < right) {
                val temp = inputArray[left]
                inputArray[left] = inputArray[right]
                inputArray[right] = temp
                left++
                right--
            }
        } else if (command.startsWith("move position")) {   // DONE - y swapped with x
            val (y, x) = command.getInts()
            if (x in inputArray.indices && y in inputArray.indices) {
                val temp = inputArray[x]
                if (x < y) {
                    for (i in x until y) {
                        inputArray[i] = inputArray[i + 1]
                    }
                } else {
                    for (i in x downTo y + 1) {
                        inputArray[i] = inputArray[i - 1]
                    }
                }
                inputArray[y] = temp

            }
        } else if (command.startsWith("rotate based on position")) {    // DONE - but I am not sure
            val x = command.split(" ").last()[0]
            val currentIndex = inputArray.indexOf(x)

            fun findOriginalPosition(s: CharArray, c: Char): Int {
                val mapping = (0..s.lastIndex)
                    .associateWith { idx -> (idx + 1 + idx + if (idx >= 4) 1 else 0) % s.size }
                    .map { it.value to it.key }
                    .toMap()
                val idx = s.indexOf(c)
                return mapping[idx]!!
            }

            val originalIndex = findOriginalPosition(inputArray, x)
            val toMoveLeft = currentIndex - originalIndex
            if (toMoveLeft < 0) {
                val absToMove = abs(toMoveLeft)
                inputArray = inputArray.shiftRight(absToMove)
            } else {
                inputArray = inputArray.shiftLeft(toMoveLeft)
            }
        }
    }
    return inputArray.joinToString("")
}
