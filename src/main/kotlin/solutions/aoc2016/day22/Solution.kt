package solutions.aoc2016.day22

import utils.Resources
import kotlin.math.abs

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part2(inputLines: List<String>): Int {

    val nodes = inputLines.drop(2).map {

        val split = it.split(Regex("\\s+"))
        val name = split[0]
        val (xPos, yPos) = name.split("-").takeLast(2)
        val xInt = xPos.drop(1).toInt()
        val yInt = yPos.drop(1).toInt()
        val size = split[1].dropLast(1).toInt()
        val used = split[2].dropLast(1).toInt()
        val avail = split[3].dropLast(1).toInt()
        val percent = split[4].dropLast(1).toInt()
        Node(yInt, xInt, name, size, used, avail, percent)
    }

    /*
    nodes.map { x -> x.size }.sortedDescending().log("sizes")
    // sizes: [510, 509, 508, 508, 508, 507, 506, 505, 504, 504, 503, 503, 501, 501, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 94,...

    nodes.map { x -> x.avail }.sortedDescending().log("available")
    // available: [90, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 29, 29, 29, 29, 29, 29, 29, ...

    nodes.map { x -> x.used }.sortedDescending().log("used")
    // used: [497, 496, 496, 496, 494, 494, 493, 492, 492, 491, 491, 491, 491, 490, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73, 73,...

    // Conclusion:
    // - Nodes with size > 90 cannot be moved.
    // - Only nodes with size <= 90 can be relocated.
    ----------------------------------------------------

    println("sizes of nodes in the first row")
    nodes.filter { it.y == 0 }.map { it.size }.log("")
    [94, 94, 89, 85, 92, 89, 90, 87, 90, 89, 94, 86, 93, 85, 91, 90, 92, 91, 91, 92, 92, 87, 93, 92, 89, 90, 90, 94, 88, 91, 88, 93, 92, 88, 88]

    It's possible to move a node to the left without changing the `y` position - it has a size of 69.
    ----------------------------------------------------

    sizes of all nodes (desc) from the first 2 rows
    nodes.filter { x -> x.y == 0 || x.y == 1 }.map { it.used }.sortedDescending().log("used sizes of 2 first nodes")
    sizes of 2 first nodes: [73, 73, 73, 73, 73, 73, 72, 72, 72, 72, 71, 71,
    so I can move any of nodes from the 1st and 2nd row

    solution
    Move empty node to the left node of the node to be moved.
    1. swap it with the empty node.
    2. move the empty node further left (as confirmed above) using moves: D, L, L, U
    repeat steps 1 and 2

    total cost = cost of moving the empty node to the left of node to move
    + (position X of the node to move - 1  * 5)  (cost of swap + setting node on the left side)
    + 1 (final swap)
    ----------------------------------------------------

    nodes.filter { x -> x.used > emptyNode.size }.map { it.y to it.x }.log("positions of giga nodes")
    positions of giga nodes: [(11, 21), (11, 22), (11, 23), (11, 24), (11, 25), (11, 26), (11, 27), (11, 28), (11, 29), (11, 30), (11, 31), (11, 32), (11, 33), (11, 34)]
    so I cannot move directly to the left node of node to move. I have to go around them on the left.
     */

    val nodeToMove = nodes.maxBy { it.x }
    val emptyNode = nodes.first { x -> x.used == 0 }

    // node to go around on the left side
    val leftmostGigaNode = nodes.filter { x -> x.used > emptyNode.size }.minBy { it.x }
    val costToGetToTheLeftOfGigaNode = abs(emptyNode.y - leftmostGigaNode.y) + abs(emptyNode.x - (leftmostGigaNode.x - 1))
    val costToGetToLeftOfNodeToMove = abs(leftmostGigaNode.y - 0) + abs((nodeToMove.x - 1) - (leftmostGigaNode.x - 1))

    return costToGetToTheLeftOfGigaNode + costToGetToLeftOfNodeToMove + (nodeToMove.x - 1) * 5 + 1
}

data class Node(val y: Int, val x: Int, val file: String, val size: Int, val used: Int, val avail: Int, val percent: Int)

fun part1(inputLines: List<String>): Int {

    val nodes = inputLines.drop(2).map {

        val split = it.split(Regex("\\s+"))
        val name = split[0]
        val (xPos, yPos) = name.split("-").takeLast(2)
        val xInt = xPos.drop(1).toInt()
        val yInt = yPos.drop(1).toInt()
        val size = split[1].dropLast(1).toInt()
        val used = split[2].dropLast(1).toInt()
        val avail = split[3].dropLast(1).toInt()
        val percent = split[4].dropLast(1).toInt()

        Node(yInt, xInt, name, size, used, avail, percent)
    }
    var ctr = 0
    for (i in 0..nodes.lastIndex) {
        val f = nodes[i]
        if (f.used == 0) {
            continue
        }
        for (j in 0..nodes.lastIndex) {
            if (i == j) {
                continue
            }
            val s = nodes[j]
            if (f.used <= s.avail) {
                ctr++
            }
        }
    }
    return ctr
}