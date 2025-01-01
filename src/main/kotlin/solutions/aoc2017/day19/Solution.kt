package solutions.aoc2017.day19

import utils.Resources
import utils.collections.extensions.firstIndexedOrNull
import utils.grid.get
import utils.grid.Direction
import utils.grid.Point
import utils.grid.toPoint

fun main() {
    val input = Resources.getLines(2017, 19)
    println("part1 = ${part1(input)} ")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): String {

    val grid = input.map { it.toList() }
    val startChar = grid.first().firstIndexedOrNull { x -> x == '|' }!!.first
    val startPoint = Point(0, startChar)
    var str = ""
    var current = startPoint
    var cD = Direction.DOWN
    while (true) {
        val cc = grid.get(current)
        if (cc !in listOf('-', '+', '|')) {
            str += cc
        }

        val nextTheSame = current.moveInDir(cD)
        val npc = if (nextTheSame.isInRange(grid)) grid.get(nextTheSame) else ' '
        if (npc != ' ') {
            current = nextTheSame
        } else {
            val d = current.down()
            val l = current.left()
            val r = current.right()
            val u = current.up()

            if (cD != Direction.UP && d.isInRange(grid) && grid.get(d) != ' ') {
                cD = Direction.DOWN
                current = d
            } else if (cD != Direction.DOWN && u.isInRange(grid) && grid.get(u) != ' ') {
                cD = Direction.UP
                current = u
            } else if (cD != Direction.RIGHT && l.isInRange(grid) && grid.get(l) != ' ') {
                cD = Direction.LEFT
                current = l
            } else if (cD != Direction.LEFT && r.isInRange(grid) && grid.get(r) != ' ') {
                cD = Direction.RIGHT
                current = r
            } else {
                return str
            }
        }
    }
}

fun part2(input: List<String>): Int {
    val grid = input.map { it.toList() }
    val startChar = grid.first().firstIndexedOrNull { x -> x == '|' }!!.first
    val startPoint = Point(0, startChar)
    var lc = 0
    var rc = 0
    var dc = 1 // starts by going 1 down
    var uc = 0
    var current = startPoint
    var cD = Direction.DOWN
    while (true) {
        var lc1 = 0
        var rc1 = 0
        var dc1 = 0
        var uc1 = 0
        var nextTheSame: Point
        if (cD == Direction.DOWN) {
            nextTheSame = (current.y + 1 to current.x).toPoint()
            dc1 = 1
        } else if (cD == Direction.RIGHT) {
            nextTheSame = (current.y to current.x + 1).toPoint()
            rc1 = 1
        } else if (cD == Direction.LEFT) {
            nextTheSame = (current.y to current.x - 1).toPoint()
            lc1 = 1
        } else { // N
            nextTheSame = (current.y - 1 to current.x).toPoint()
            uc1 = 1
        }

        val npc = if (nextTheSame.isInRange(grid)) grid.get(nextTheSame) else ' '
        if (npc != ' ') {
            current = nextTheSame
            lc += lc1
            rc += rc1
            uc += uc1
            dc += dc1
        } else {
            val d = current.down()
            val l = current.left()
            val r = current.right()
            val u = current.up()

            if (cD != Direction.UP && d.isInRange(grid) && grid.get(d) != ' ') {
                cD = Direction.DOWN
                current = d
                dc += 1
            } else if (cD != Direction.DOWN && u.isInRange(grid) && grid.get(u) != ' ') {
                cD = Direction.UP
                current = u
                uc += 1
            } else if (cD != Direction.RIGHT && l.isInRange(grid) && grid.get(l) != ' ') {
                cD = Direction.LEFT
                current = l
                lc += 1
            } else if (cD != Direction.LEFT && r.isInRange(grid) && grid.get(r) != ' ') {
                cD = Direction.RIGHT
                current = r
                rc += 1
            } else {
                return lc + rc + dc + uc
            }
        }
    }
}



