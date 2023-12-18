package solutions.aoc2023.day18

import utils.Resources

fun main() {

    val inputLine =
        Resources.getLines(2023, 18)
//        Resources.getLinesExample(2023, 18)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part2(input: List<String>): Int {

    input.forEach { it.log() }

    val toMutableList = input.map {

        val split = it.split(" ")
        val hex = split[2]
        val substring = hex.substring(2, hex.length - 1)
        val hDist = substring.take(5)
        val hDir = substring.takeLast(1)
        var dir = ' '
        if (hDir == "0") {
            dir = 'R'
        } else if (hDir == "1") {
            dir = 'D'
        } else if (hDir == "2") {
            dir = 'L'
        } else if (hDir == "3") {
            dir = 'U'
        } else {
            throw Exception()
        }
        val length = hDist.toInt(radix = 16)

        dir to length
    }.toMutableList()

    val visited = funCollect(0 to 0, 0, toMutableList)
    visited.size.log("vis size")

    var s1 = 0L
    for (i in 0..visited.size - 2) {
        //x1 * y2 - y1 * x2
        s1 += visited.get(i).second * visited.get(i + 1).first - (visited.get(i).first * visited.get(i + 1).second)
    }
    val ar = s1 / 2

    val finalArea = (ar + visited.size / 2) + 1
    finalArea.log("final p2")

    return toMutableList.size
}

fun part1(input: List<String>): Int {

    input.forEach { it.log() }


    val toMutableList = input.map {

        val split = it.split(" ")
        val dir = split[0][0]
        val length = split[1].toInt()

        dir to length
    }.toMutableList()

    val visited = funCollect(0 to 0, 0, toMutableList)
    visited.log("vis")
    visited.size.log("vis size")


    var s1 = 0L
    for (i in 0..visited.size - 2) {
        s1 += visited.get(i).second * visited.get(i + 1).first - (visited.get(i).first * visited.get(i + 1).second)
    }
    val ar = s1 / 2
    val finalArea = (ar + visited.size / 2) + 1
    finalArea.log("final p1")

    return toMutableList.size
}

fun funCollect(
    curPos: Pair<Int, Int>,
    idx: Int,
    toVisit: MutableList<Pair<Char, Int>>,
): MutableList<Pair<Int, Int>> {

    val visited = mutableListOf<Pair<Int, Int>>()
    var li = idx
    var (cY, cX) = curPos
    while (toVisit.lastIndex >= li) {
        val (dir, length) = toVisit[li]
        if (dir == 'U') {
            for (i in 1..length) {
                cY -= 1
                visited.add(cY to cX)
            }

        } else if (dir == 'D') {
            for (i in 1..length) {
                cY += 1
                visited.add(cY to cX)
            }

        } else if (dir == 'L') {
            for (i in 1..length) {
                cX -= 1
                visited.add(cY to cX)
            }

        } else if (dir == 'R') {
            for (i in 1..length) {
                cX += 1
                visited.add(cY to cX)
            }
        }
        li++
    }
    return visited
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

