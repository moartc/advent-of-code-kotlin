package solutions.aoc2022.day25

import utils.Resources
import solutions.aoc2022.day25.normal5ToSnafu as normal5ToSnafu1


fun main() {

    val input = Resources.getLines(2022, 25)
    println("part1 = ${part1(input)}")
}



fun part1(input: List<String>): String {

    var tot = 0L
    val mapping = mutableMapOf('2' to 2, '1' to 1, '0' to 0, '-' to -1, '=' to -2)
    input.forEach { line ->
        var mt = 1L
        var res = 0L
        line.trim().reversed().forEach {
            res += (mt * mapping[it]!!)
            mt *= 5
        }
        tot += res
    }
    return normal5ToSnafu1(tot.toString(5))
}

fun normal5ToSnafu(f: String): String {
    var move = 0
    var res = ""
    f.reversed().forEach {
        val dig = it.digitToInt()
        if (dig + move == 3) {
            res += "="
            move = 1
        } else if (dig + move == 4) {
            res += "-"
            move = 1
        } else if (dig + move == 5) {
            res += "0"
            move = 1
        } else {
            res += (dig + move)
            move = 0
        }
    }
    if (move != 0) res += move.toString()
    return res.reversed()
}