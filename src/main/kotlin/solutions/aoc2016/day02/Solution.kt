package solutions.aoc2016.day02

import utils.Resources
import utils.grid.Point

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): String {

    val keys = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    fun move(curr: Point, s: String): Point {

        var int = curr
        s.forEach { c ->
            val next = if (c == 'U') {
                int.up()
            } else if (c == 'D') {
                int.down()
            } else if (c == 'L') {
                int.left()
            } else {
                int.right()
            }
            if (next.isInRange(keys)) {
                int = next
            }
        }
        return int
    }

    var curr = Point(1, 1)
    var ans = ""
    inputLines.forEach {
        curr = move(curr, it)
        ans += keys[curr.y][curr.x]
    }
    return ans
}

fun part2(inputLines: List<String>): String {

    val keys = listOf(
        listOf('Z', 'Z', '1', 'Z', 'Z'),
        listOf('Z', '2', '3', '4', 'Z'),
        listOf('5', '6', '7', '8', '9'),
        listOf('Z', 'A', 'B', 'C', 'Z'),
        listOf('Z', 'Z', 'D', 'Z', 'Z')
    )

    fun move(curr: Point, s: String): Point {

        var int = curr
        s.forEach { c ->
            val next = if (c == 'U') {
                int.up()
            } else if (c == 'D') {
                int.down()
            } else if (c == 'L') {
                int.left()
            } else {
                int.right()
            }
            if (next.isInRange(keys) && keys[next.y][next.x] != 'Z') {
                int = next
            }
        }
        return int
    }

    var curr = Point(2, 0)
    var ans = ""
    inputLines.forEach {
        curr = move(curr, it)
        ans += keys[curr.y][curr.x]
    }
    return ans
}



