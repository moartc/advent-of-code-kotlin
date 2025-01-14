package solutions.aoc2016.day09

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    val line = inputLines[0]

    var i = 0
    var ctr = 0
    while (i <= line.lastIndex) {
        val c = line[i]
        if (c == '(') {
            val closeIndex = line.indexOf(')', i)
            val pattern = line.substring(i + 1, closeIndex)
            val (left, right) = pattern.split("x")
            val lVal = left.toInt()
            val rVal = right.toInt()
            val jumpTo = closeIndex + lVal
            ctr += (lVal * rVal)
            i = jumpTo + 1
            continue
        }
        ctr++
        i++
    }
    return ctr
}

fun part2(inputLines: List<String>): Long {

    val line = inputLines[0]

    fun calculateSize(s: String): Long {
        var size = 0L
        var i = 0
        while (i <= s.lastIndex) {
            val c = s[i]
            if (c == '(') {
                val closeIndex = s.indexOf(')', i)
                val pattern = s.substring(i + 1, closeIndex)
                val (left, right) = pattern.split("x")
                val lVal = left.toInt()
                val rVal = right.toInt()
                val substring = s.substring(closeIndex + 1, closeIndex + lVal + 1)
                size += rVal * calculateSize(substring)
                i = closeIndex + substring.length + 1
                continue
            }
            i++
            size++
        }
        return size
    }

    return calculateSize(line)
}