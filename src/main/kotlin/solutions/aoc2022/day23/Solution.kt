package solutions.aoc2022.day23

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 23)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val dirs = mutableListOf("N", "S", "W", "E")
    val points = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') Elf(y, x) else null } }.filterNotNull()
    var afterRound = points.toList()

    repeat(10) {
        val propose = afterRound.map { getNewPos(afterRound, it, dirs) }
        afterRound = propose.mapIndexed { idx, p -> if (propose.count { p2 -> p2 == p } == 1) p else afterRound[idx] }
        val firstDir = dirs.first()
        dirs.removeAt(0)
        dirs.add(firstDir)
    }
    val horizontalSize = afterRound.maxOf { e -> e.x } + 1 - afterRound.minOf { e -> e.x }
    val verticalSize = afterRound.maxOf { e -> e.y } + 1 - afterRound.minOf { e -> e.y }
    return horizontalSize * verticalSize - points.size
}

fun part2(input: List<String>): Int {

    val dirs = mutableListOf("N", "S", "W", "E")
    val points = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> if (c == '#') Elf(y, x) else null } }.filterNotNull()

    var afterRound = points.toList()
    var prev = listOf<Elf>()

    var ctr = 0
    while (true) {
        ctr += 1
        val propose = afterRound.map { getNewPos(afterRound, it, dirs) }
        afterRound = propose.mapIndexed { idx, p -> if (propose.count { p2 -> p2 == p } == 1) p else afterRound[idx] }
        if (prev == afterRound) {
            return ctr
        }
        prev = afterRound
        val firstDir = dirs.first()
        dirs.removeAt(0)
        dirs.add(firstDir)
    }
}

data class Elf(val y: Int, val x: Int)

fun getNewPos(allPos: List<Elf>, elf: Elf, dirs: MutableList<String>): Elf {

    val (y, x) = elf

    val l1 = listOf(Elf(y - 1, x), Elf(y - 1, x - 1), Elf(y - 1, x + 1))
    val north = allPos.none { a -> l1.contains(a) }

    val l2 = listOf(Elf(y + 1, x), Elf(y + 1, x - 1), Elf(y + 1, x + 1))
    val south = allPos.none { a -> l2.contains(a) }

    val l3 = listOf(Elf(y, x - 1), Elf(y + 1, x - 1), Elf(y - 1, x - 1))
    val west = allPos.none { a -> l3.contains(a) }

    val l4 = listOf(Elf(y, x + 1), Elf(y + 1, x + 1), Elf(y - 1, x + 1))
    val east = allPos.none { a -> l4.contains(a) }

    val adjacentPositions = listOf(
        Elf(y - 1, x),
        Elf(y - 1, x - 1),
        Elf(y - 1, x + 1),
        Elf(y + 1, x),
        Elf(y + 1, x - 1),
        Elf(y + 1, x + 1),
        Elf(y, x - 1),
        Elf(y, x + 1)
    )

    if (allPos.any { a -> adjacentPositions.contains(a) }) {
        dirs.forEach {
            when (it) {
                "N" -> if (north) return Elf(y - 1, x)
                "S" -> if (south) return Elf(y + 1, x)
                "W" -> if (west) return Elf(y, x - 1)
                "E" -> if (east) return Elf(y, x + 1)
            }
        }
    }
    return elf
}