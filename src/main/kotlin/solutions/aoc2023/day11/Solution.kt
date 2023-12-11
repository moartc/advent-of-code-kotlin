package solutions.aoc2023.day11

import utils.Resources
import utils.algorithms.bfs
import utils.algorithms.bfsWithPath
import utils.algorithms.withoutDiagonal
import java.math.BigInteger

fun main() {

    val inputLine =
        Resources.getLines(2023, 11)
//        Resources.getLinesExample(2023, 11)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    input.forEach { it.log("p1") }

    val toMutableList = mutableListOf<String>()



    for (y in input) {
        if (y.all { x -> x.equals('.') }) { // in row
            toMutableList.add(y)
        }
        toMutableList.add(y)
    }

    "first".log()
    toMutableList.forEach { line ->
        println(line)
    }


    val chars = toMutableList.map { s -> s.toMutableList() }

    var added = 0
    for (col in input[0].indices) {
        if (allDotsInRow(col, input)) {

            col.log("col")
            // add new row
            for (row in chars.indices) {
                chars[row].add(col + added, '.')
            }
            added++
        }
    }


    chars.forEach { line ->
        line.forEach { c -> print(c) }
        println()
    }

    fun canVisit(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
        return true
    }

    val positions = mutableListOf<Pair<Int,Int>>()
    for(y in chars.indices) {
        for (x in chars[0].indices) {
            if(chars[y][x] == '#') {
                positions.add(y to x)
            }
        }
    }
    var sum = 0

    for(i1 in positions.indices) {
        for(i2 in positions.indices) {
            if(i2 > i1) {
                val bfs = bfs(chars, withoutDiagonal, positions[i1], positions[i2], ::canVisit)
                println("for $i1 and $i2 its $bfs")
                sum += bfs
            }
        }
    }

    sum.log("result")

    return sum
}
fun part2(input: List<String>): BigInteger {

    input.forEach { it.log("p1") }

    val toMutableList = mutableListOf<String>()



    for (y in input) {
        if (y.all { x -> x.equals('.') }) { // in row
            toMutableList.add("E".repeat(y.length))
        }
        toMutableList.add(y)
    }

    "first".log()
    toMutableList.forEach { line ->
        println(line)
    }


    val chars = toMutableList.map { s -> s.toMutableList() }

    var added = 0
    for (col in input[0].indices) {
        if (allDotsInRow(col, input)) {

            col.log("col")
            // add new row
            for (row in chars.indices) {
                chars[row].add(col + added, 'E')
            }
            added++
        }
    }


    chars.forEach { line ->
        line.forEach { c -> print(c) }
        println()
    }

    fun canVisit(currentPos: Pair<Int, Int>, nextPos: Pair<Int, Int>): Boolean {
        return true
    }

    val positions = mutableListOf<Pair<Int, Int>>()
    for (y in chars.indices) {
        for (x in chars[0].indices) {
            if (chars[y][x] == '#') {
                positions.add(y to x)
            }
        }
    }
    var sum = BigInteger.ZERO

    for (i1 in positions.indices) {
        for (i2 in positions.indices) {
            if (i2 > i1) {
                val bfs = bfsWithPath(chars, withoutDiagonal, positions[i1], positions[i2], ::canVisit)
                var bfsRes = bfs.first
                for (pair in bfs.second) {
                    val cOnPos = chars[pair.first][pair.second]
                    if (cOnPos == 'E') {
                        bfsRes += 1000000 - 2
                    }
                }
//                println("for $i1 and $i2 its $bfsRes")
                sum = sum.add(bfsRes.toBigInteger())
                println("for ${positions[i1]} and ${positions[i2]} it's $sum")
            }
        }
    }

    sum.log("result")

    return sum
}


fun allDotsInRow(row: Int, str: List<String>): Boolean {

    for (line in str) {
        if (line[row] != '.') {
            return false
        }
    }
    return true
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



