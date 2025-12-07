package solutions.aoc2025.day07

import utils.Resources
import utils.collections.extensions.findPosition

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines =
        Resources.getLines(2025, day)
//    Resources.getLinesExample(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val (y, x) = inputLines.findPosition('S')
    val grid = inputLines.map { x -> x.toCharArray() }
    var beams = mutableSetOf<Pair<Int, Int>>()
    beams.add((y to x))
    var sc = 0L
    while (beams.isNotEmpty()) {
        val ns = mutableSetOf<Pair<Int, Int>>()
        beams.forEach { p ->
            if (p.first == grid.lastIndex) {
                // skip
            } else {
                val newPos = p.first + 1 to p.second
                if (grid[p.first][p.second] == '^') {
                    ns.add(newPos.first to newPos.second - 1)
                    ns.add(newPos.first to newPos.second + 1)
                    sc++
                } else {
                    ns.add(newPos.first to newPos.second)
                }
            }
        }
        beams = ns
    }

    return sc
}

fun part2(inputLines: List<String>): Any {

    val (y, x) = inputLines.findPosition('S')
    val grid = inputLines.map { x -> x.toCharArray() }
    var beams = mutableMapOf<Pair<Int, Int>, Long>()
    beams[(y to x)] = 1
    while (beams.isNotEmpty()) {
        val nm = mutableMapOf<Pair<Int, Int>, Long>()
        beams.forEach { p ->
            if (p.key.first == grid.lastIndex) {
                // all of them should be here
                return beams.values.sum()
            } else {
                val newPos = p.key.first + 1 to p.key.second
                val prevCtr = p.value
                if (grid[p.key.first][p.key.second] == '^') {
                    nm.merge(newPos.first to newPos.second - 1, prevCtr, Long::plus)
                    nm.merge(newPos.first to newPos.second + 1, prevCtr, Long::plus)
                } else {
                    nm.merge(newPos.first to newPos.second, prevCtr, Long::plus)
                }
            }
        }
        beams = nm
    }
    return beams.values.sum()
}
// 352321814


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

