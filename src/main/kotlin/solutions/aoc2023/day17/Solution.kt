package solutions.aoc2023.day17

import dijkstra
import utils.Resources
import utils.grid.Direction

fun main() {

    val inputLine = Resources.getLines(2023, 17)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Int {

    val list = input.map {
        it.toCharArray().map { c -> c.digitToInt() }
    }.toList()

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > list.lastIndex || nextPos.second < 0 || nextPos.second > list[0].lastIndex)
    }

    fun getNext(i: Node): List<Node> {
        val (current, newDir, lengthOnDir) = i
        val toReturn = mutableListOf<Node>()

        for (newDirection in Direction.entries) {
            val nextY = current.first + newDirection.y
            val nextX = current.second + newDirection.x
            if (isPosValid(nextY to nextX)) {
                if (newDir == newDirection && lengthOnDir == 3) {
                    continue
                } else if (newDir.isOpposite(newDirection)) {
                    continue
                }
                if (newDir == newDirection) {
                    toReturn.add(Node(nextY to nextX, newDirection, lengthOnDir + 1))
                } else {
                    toReturn.add(Node(nextY to nextX, newDirection, 1))
                }
            }
        }
        return toReturn
    }

    val start = Node(0 to 0, Direction.DOWN, 0)
    val dijkstraGen = dijkstra(start, { _, n2 -> list[n2.pos.first][n2.pos.second] }, ::getNext)
    return dijkstraGen.filter { q -> q.key.pos == input.lastIndex to input[0].lastIndex }.values.min()
}

fun part2(input: List<String>): Int {


    val list = input.map {
        it.toCharArray().map { c -> c.digitToInt() }
    }.toList()

    fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
        return !(nextPos.first < 0 || nextPos.first > list.lastIndex || nextPos.second < 0 || nextPos.second > list[0].lastIndex)
    }

    fun getNext(i: Node): List<Node> {
        val (current, newDir, currDirLength) = i
        val toReturn = mutableListOf<Node>()

        for (newDirection in Direction.entries) {
            val nextY = current.first + newDirection.y
            val nextX = current.second + newDirection.x
            if (isPosValid(nextY to nextX)) {
                if (newDir.isOpposite(newDirection)) {
                    continue
                }

                val possibleNewLength = if (newDirection == newDir) currDirLength + 1 else 1
                val newNode = Node(nextY to nextX, newDirection, possibleNewLength)

                if ((nextY == input.lastIndex && nextX == input[0].lastIndex) && possibleNewLength < 4) { // cannot finish with newLength < 4
                    continue
                }

                if (currDirLength == 0) {   // beginning case
                    toReturn.add(newNode)
                } else if (currDirLength < 4) {
                    if (newDirection == newDir) {
                        toReturn.add(newNode)
                    }
                } else if (currDirLength < 10) {
                    toReturn.add(newNode)

                } else if (currDirLength == 10) { // must turn
                    if (newDir != newDirection && !newDirection.isOpposite(newDir)) {
                        toReturn.add(newNode)
                    }
                }
            }
        }
        return toReturn
    }

    val start = Node(0 to 0, Direction.RIGHT, 0)
    val dijkstraGen = dijkstra(start, { _, n2 -> list[n2.pos.first][n2.pos.second] }, ::getNext)
    val filter2 = dijkstraGen.filter { q -> q.key.pos == input.lastIndex to input[0].lastIndex }
    return filter2.values.min()
}

data class Node(var pos: Pair<Int, Int>, val dirNew: Direction, var dist: Int)


