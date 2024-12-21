package solutions.aoc2024.day21

import utils.Resources
import utils.grid.Point

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2024, day)
    println("part1 = ${part1(inputLines)} ")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    var res = 0L
    val memoPerDepth = MutableList(26) { mutableMapOf<String, Long>() }
    for (code in inputLines) {
        val start = genAllShortest(code, numericKeypad, bigStart)
        val minBy = start.minOfOrNull { dfsSolution(it, robotKeypad, smallStart, 2, memoPerDepth) }
        val num = extractNumber(code)
        res += num * minBy!!
    }
    return res
}

fun part2(inputLines: List<String>): Long {

    var res = 0L
    val memoPerDepth = MutableList(26) { mutableMapOf<String, Long>() }
    for (code in inputLines) {
        val start = genAllShortest(code, numericKeypad, bigStart)
        val minBy = start.minOfOrNull { dfsSolution(it, robotKeypad, smallStart, 25, memoPerDepth) }
        val num = extractNumber(code)
        res += num * minBy!!
    }
    return res
}

val numericKeypad = listOf(
    "789",
    "456",
    "123",
    " 0A"
)
val robotKeypad = listOf(
    " ^A",
    "<v>"
)

val bigStart = Point(3, 2)
val smallStart = Point(0, 2)

fun splitStringByA(input: String): List<String> {
    val split = input.split('A').toMutableList()
    split.removeLast()
    return split.map { it + 'A' }
}

fun extractNumber(code: String): Int {
    return code.filter { it.isDigit() }.toInt()
}

fun generateAllPaths(start: Point, target: Point, keypad: List<String>): List<String> {
    val directions = mapOf(
        Point(0, -1) to "<",
        Point(0, 1) to ">",
        Point(-1, 0) to "^",
        Point(1, 0) to "v"
    )

    fun dfs(current: Point, target: Point, visited: Set<Point>, path: String): List<String> {
        if (current == target) {
            return listOf(path)
        }
        if (visited.contains(current)) {
            return emptyList()
        }

        val paths = mutableListOf<String>()
        for ((dir, move) in directions) {
            val next = Point(current.y + dir.y, current.x + dir.x)
            if (next.y in keypad.indices && next.x in keypad[0].indices && keypad[next.y][next.x] != ' ') {
                paths += dfs(next, target, visited + current, path + move)
            }
        }
        return paths
    }
    return dfs(start, target, emptySet(), "")
}


fun genAllShortest(target: String, numericKeypad: List<String>, startPoint: Point): List<String> {

    val keypadMap = numericKeypad.flatMapIndexed { y, row ->
        row.mapIndexed { x, char -> char to Point(y, x) }
    }.toMap()

    var allPaths = listOf("")
    var currentPosition = startPoint

    for (char in target) {
        val targetPos = keypadMap[char]!!
        val allGenPaths = generateAllPaths(currentPosition, targetPos, numericKeypad)
        val shortestLength = allGenPaths.minOf { it.length }
        val allShortestPaths = allGenPaths.filter { it.length == shortestLength }
        allPaths = allPaths.flatMap { path ->
            allShortestPaths.map { move -> path + move + "A" }
        }
        currentPosition = targetPos
    }
    return allPaths
}

fun dfsSolution(
    sequenceToType: String,
    robotKeypad: List<String>,
    smallStart: Point,
    depth: Int,
    memo: MutableList<MutableMap<String, Long>>
): Long {
    if (depth == 0) {
        return sequenceToType.length.toLong()
    }

    val splitStringByA = splitStringByA(sequenceToType)
    var sum = 0L
    splitStringByA.forEach { part ->
        if (memo[depth].containsKey(part)) {
            sum += (memo[depth][part]!!)
        } else {
            val allShortestPaths = genAllShortest(part, robotKeypad, smallStart)
            val resultsForAllShortestPaths = allShortestPaths.map {
                dfsSolution(it, robotKeypad, smallStart, depth - 1, memo)
            }
            val toMem = resultsForAllShortestPaths.minBy { it }
            sum += toMem
            memo[depth][part] = toMem
        }
    }
    memo[depth][sequenceToType] = sum
    return sum
}
