package solutions.aoc2016.day14

import solutions.aoc2015.day04.md5Hash
import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Long {
    val puzzleInput = inputLines[0]
    return solve(puzzleInput, ::md5Hash)
}

fun part2(inputLines: List<String>): Long {
    val puzzleInput = inputLines[0]

    fun md5HashP2(inp: String): String {
        var input = inp
        repeat(2017) {
            input = md5Hash(input)
        }
        return input
    }
    return solve(puzzleInput, ::md5HashP2)
}

fun solve(puzzleInput: String, hashFunction: (String) -> String): Long {
    var index = 0L
    var ctr = 0
    val hashMap = mutableMapOf<String, String>()
    while (true) {
        val toHash = puzzleInput + index
        val hash = hashMap.computeIfAbsent(toHash) { hashFunction(it) }
        val has3 = getConsecutive3charsOrX(hash)
        if (has3 != 'x') {
            for (i in index + 1..index + 1000) {
                val nextToHash = puzzleInput + i
                val newHash = hashMap.computeIfAbsent(nextToHash) { x -> hashFunction(x) }
                if (hasConsecutive5charsEquals(newHash, has3)) {
                    ctr++
                    if (ctr == 64) {
                        return index
                    }
                }
            }
        }
        index++
    }
}

fun hasConsecutive5charsEquals(s: String, c: Char): Boolean {
    var count = 1

    for (i in 1 until s.length) {
        if (s[i] == s[i - 1] && s[i] == c) {
            count++
            if (count == 5) {
                return true
            }
        } else {
            count = 1
        }
    }
    return false
}

fun getConsecutive3charsOrX(s: String): Char {
    var count = 1

    for (i in 1 until s.length) {
        if (s[i] == s[i - 1]) {
            count++
            if (count == 3) {
                return s[i]
            }
        } else {
            count = 1
        }
    }
    return 'x'
}
