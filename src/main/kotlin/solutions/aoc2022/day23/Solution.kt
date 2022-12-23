package solutions.aoc2022.day23

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 23)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part2(input: List<String>): Int {

    val dirs = mutableListOf("N", "S", "W", "E")
    val points = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') Elf(y, x) else null } }.filterNotNull()

    var afterRound = points.toMutableList()
    var prev = mutableListOf<Elf>()

    var ctr = 0
    while (true) {
        ctr += 1
        val propose = afterRound.map { getNewPos(afterRound, it, dirs) }

        afterRound = propose.mapIndexed { idx, p ->
            if (propose.count { p2 -> p2 == p } == 1) {
                p
            } else {
                afterRound[idx]
            }
        }.toMutableList()

        if (prev == afterRound) {
            return ctr
        }
        prev = afterRound

        val f = dirs.first()
        dirs.removeAt(0)
        dirs.add(f)
    }
}


data class Elf(val y: Int, val x: Int) {}

fun part1(input: List<String>): Int {

    val dirs = mutableListOf("N", "S", "W", "E")
    val points = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') Elf(y, x) else null } }.filterNotNull()
    var afterRound = points.toMutableList()

    repeat(10) {

        val propose = afterRound.map { getNewPos(afterRound, it, dirs) }

        afterRound = propose.mapIndexed { idx, p ->
            if (propose.count { p2 -> p2 == p } == 1) {
                p
            } else {
                afterRound[idx]
            }
        }.toMutableList()

        val f = dirs.first()
        dirs.removeAt(0)
        dirs.add(f)
    }

    val mostE = afterRound.maxOf { e -> e.x }
    val mostW = afterRound.minOf { e -> e.x }
    val mostS = afterRound.maxOf { e -> e.y }
    val mostN = afterRound.minOf { e -> e.y }

    val horizontal = mostE + 1 - mostW
    val vertical = mostS + 1 - mostN
    val numOfPos = horizontal * vertical
    return numOfPos - points.size
}


fun getNewPos(allPos: List<Elf>, elf: Elf, dirs: MutableList<String>): Elf {

    val y = elf.y
    val x = elf.x

    val l1 = listOf(Elf(y - 1, x), Elf(y - 1, x - 1), Elf(y - 1, x + 1))
    val north = !allPos.any { a -> l1.contains(a) }

    val l2 = listOf(Elf(y + 1, x), Elf(y + 1, x - 1), Elf(y + 1, x + 1))
    val south = !allPos.any { a -> l2.contains(a) }

    val l3 = listOf(Elf(y, x - 1), Elf(y + 1, x - 1), Elf(y - 1, x - 1))
    val west = !allPos.any { a -> l3.contains(a) }

    val l4 = listOf(Elf(y, x + 1), Elf(y + 1, x + 1), Elf(y - 1, x + 1))
    val east = !allPos.any { a -> l4.contains(a) }

    val possible = listOf(
        Elf(y - 1, x),
        Elf(y - 1, x - 1),
        Elf(y - 1, x + 1),
        Elf(y + 1, x),
        Elf(y + 1, x - 1),
        Elf(y + 1, x + 1),
        Elf(y, x - 1),
        Elf(y, x + 1)
    )

    val doAnything = allPos.any { a -> possible.contains(a) }
    if (doAnything) {
        dirs.forEach {
            when (it) {
                "N" -> if (north) return Elf(y - 1, x)
                "S" -> if (south) return Elf(y + 1, x)
                "W" -> if (west) return Elf(y, x - 1)
                "E" -> if (east) return Elf(y, x + 1)
            }
        }
    } else {
        return elf
    }
    return elf

}