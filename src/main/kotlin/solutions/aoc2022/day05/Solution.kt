package solutions.aoc2022.day05

import utils.Resources
import utils.getInts

/**
One part at a time because one rune changes the lists in the map due to mutability
The map was manually rewritten from the input because writing the parsing function seemed too time consuming.
TODO ("Parse all input and fix mutability issues")

 */
fun main() {
    val inputLines = Resources.getLines(2022, 5)
//    println("part1 = ${part1(inputLines)}")
    println("part1 = ${part2(inputLines)}")
}


fun solve(map: MutableMap<Int, MutableList<Char>>, input: List<String>, rev: Boolean): String {

    input.forEach { line ->
        val ints = line.getInts()
        val move = ints[0]
        val from = ints[1]
        val to = ints[2]
        move(map, move, from, to, rev)
    }
    return map.values.map { it.last() }.joinToString("")
}

fun move(map: MutableMap<Int, MutableList<Char>>, num: Int, from: Int, to: Int, rev: Boolean) {
    val toMove = map[from]?.takeLast(num)
    val newFrom = map[from]?.take(map[from]?.size!! - num)
    map[from] = newFrom!!.toMutableList()
    map[to]!!.addAll(if (rev) toMove!!.reversed() else toMove!!)
}

fun part1(input: List<String>): String {
    return solve(map.toMutableMap(), input, true)
}

fun part2(input: List<String>): String {
    return solve(map.toMutableMap(), input, false)
}

var map = mapOf<Int, MutableList<Char>>(
    1 to "JHPMSFNV".toMutableList(),
    2 to "SRLMJDQ".toMutableList(),
    3 to "NQDHCSWB".toMutableList(),
    4 to "RSCL".toMutableList(),
    5 to "MVTPFB".toMutableList(),
    6 to "TRQNC".toMutableList(),
    7 to "GVR".toMutableList(),
    8 to "CZSPDLR".toMutableList(),
    9 to "DSJVGPBF".toMutableList()
)

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

//[V]     [B]                     [F]
//[N] [Q] [W]                 [R] [B]
//[F] [D] [S]     [B]         [L] [P]
//[S] [J] [C]     [F] [C]     [D] [G]
//[M] [M] [H] [L] [P] [N]     [P] [V]
//[P] [L] [D] [C] [T] [Q] [R] [S] [J]
//[H] [R] [Q] [S] [V] [R] [V] [Z] [S]
//[J] [S] [N] [R] [M] [T] [G] [C] [D]
// 1   2   3   4   5   6   7   8   9