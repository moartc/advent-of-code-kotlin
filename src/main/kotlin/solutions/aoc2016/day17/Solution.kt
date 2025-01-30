package solutions.aoc2016.day17

import solutions.aoc2015.day04.md5Hash
import utils.Resources
import utils.grid.Point

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): String? {

    val input = inputLines[0]
    val grid = Array(4) { Array(4) { ' ' } }.map { it.toList() }
    var shortest: String? = null

    fun move(pos: Point, path: String) {
        if (pos.y == 3 && pos.x == 3) {
            if (shortest == null) {
                shortest = path
            } else {
                if (shortest!!.length > path.length) {
                    shortest = path
                }
            }
            return
        }
        val toCalc = input + path
        val hash = md5Hash(toCalc)
        val u = pos.up()
        val d = pos.down()
        val l = pos.left()
        val r = pos.right()
        if (hash[0] in "bcdef" && u.isInRange(grid)) {
            move(u, path + 'U')
        }
        if (hash[1] in "bcdef" && d.isInRange(grid)) {
            move(d, path + 'D')
        }
        if (hash[2] in "bcdef" && l.isInRange(grid)) {
            move(l, path + 'L')
        }
        if (hash[3] in "bcdef" && r.isInRange(grid)) {
            move(r, path + 'R')
        }
    }

    val start = Point(0, 0)
    move(start, "")
    return shortest
}

fun part2(inputLines: List<String>): Int {

    val input = inputLines[0]
    val grid = Array(4) { Array(4) { ' ' } }.map { it.toList() }
    var shortest: String? = null

    fun move(pos: Point, path: String) {
        if (pos.y == 3 && pos.x == 3) {
            if (shortest == null) {
                shortest = path
            } else {
                if (shortest!!.length < path.length) {
                    shortest = path
                }
            }
            return
        }

        val toCalc = input + path
        val hash = md5Hash(toCalc)
        val u = pos.up()
        val d = pos.down()
        val l = pos.left()
        val r = pos.right()
        if (hash[0] in "bcdef" && u.isInRange(grid)) {
            move(u, path + 'U')
        }
        if (hash[1] in "bcdef" && d.isInRange(grid)) {
            move(d, path + 'D')
        }
        if (hash[2] in "bcdef" && l.isInRange(grid)) {
            move(l, path + 'L')
        }
        if (hash[3] in "bcdef" && r.isInRange(grid)) {
            move(r, path + 'R')
        }
    }

    val start = Point(0, 0)
    move(start, "")
    return shortest!!.length
}