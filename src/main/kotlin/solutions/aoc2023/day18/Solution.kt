package solutions.aoc2023.day18

import utils.Resources

fun main() {

    val inputLine = Resources.getLines(2023, 18)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part1(input: List<String>): Long {

    val toMutableList = input.map {
        val split = it.split(" ")
        val dir = split[0][0]
        val length = split[1].toInt()
        dir to length
    }.toMutableList()

    val visited = funCollect(0 to 0, toMutableList)
    val area = area(visited)
    return (area + visited.size / 2) + 1
}

fun part2(input: List<String>): Long {

    val toMutableList = input.map {

        val split = it.split(" ")
        val hex = split[2]
        val substring = hex.substring(2, hex.length - 1)
        val hDist = substring.take(5)
        val hDir = substring.takeLast(1)
        val dir = when (hDir) {
            "0" -> 'R'
            "1" -> 'D'
            "2" -> 'L'
            "3" -> 'U'
            else -> throw Exception()
        }
        val length = hDist.toInt(radix = 16)
        dir to length
    }.toMutableList()

    val visited = funCollect(0 to 0, toMutableList)
    val area = area(visited)
    return (area + visited.size / 2) + 1
}

fun funCollect(
    curPos: Pair<Int, Int>,
    toVisit: MutableList<Pair<Char, Int>>,
): MutableList<Pair<Int, Int>> {

    val visited = mutableListOf<Pair<Int, Int>>()
    var (cY, cX) = curPos
    toVisit.forEach { (dir, length) ->
        for (i in 1..length) {
            cY += (if (dir == 'U') -1 else if (dir == 'D') 1 else 0)
            cX += (if (dir == 'L') -1 else if (dir == 'R') 1 else 0)
            visited.add(cY to cX)
        }
    }
    return visited
}

fun area(points: List<Pair<Int, Int>>): Long {

    return points.asSequence()
        .plus(points[0])
        .windowed(2, 1)
        .sumOf { (p1, p2) ->
            (p1.second * p2.first - p1.first * p2.second).toLong()
        } / 2
}
