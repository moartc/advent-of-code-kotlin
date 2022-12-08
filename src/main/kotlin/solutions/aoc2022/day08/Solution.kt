package solutions.aoc2022.day08

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 8)
    val grid = input.map { line -> line.map { it.digitToInt() } }

    println("part1 = ${part1(grid)}")
    println("part2 = ${part2(grid)}")
}

fun part1(grid: List<List<Int>>): Int {
    val border = 2 * grid[0].size + 2 * grid.size - 4
    val pairs = (1..grid.size - 2).flatMap { i -> (1..grid[0].size - 2).map { i to it } }
    val middle = pairs.count { (i, j) -> isVisible(i, j, grid) }
    return border + middle
}

fun part2(input: List<List<Int>>): Int {
    val pairs = (1..input.size - 2).flatMap { i -> (1..input[0].size - 2).map { i to it } }
    return pairs.fold(0) { acc, (i, j) -> acc.coerceAtLeast(getCounter(i, j, input)) }
}

fun getCounter(i: Int, j: Int, inp: List<List<Int>>): Int {
    val checking = inp[i][j]
    val idxBorderUp = inp.map { row -> row[j] }.take(i).reversed().indexOfFirst { it >= checking }
    val ctrUp = if (idxBorderUp == -1) (i) else if (idxBorderUp == 0) 1 else idxBorderUp + 1

    val numberOfElementsOnLeft = inp.size - 1 - i
    val idxBorderDown = inp.map { row -> row[j] }.takeLast(numberOfElementsOnLeft).indexOfFirst { it >= checking }
    val ctrDown = if (idxBorderDown == -1) numberOfElementsOnLeft else if (idxBorderDown == 0) 1 else idxBorderDown + 1

    val idxBorderLeft = inp[i].take(j).reversed().indexOfFirst { it >= checking }
    val ctrLeft = if (idxBorderLeft == -1) (j) else if (idxBorderDown == 0) 1 else idxBorderLeft + 1

    val numberOfElementsBelow = inp[0].size - j - 1
    val idxBorderRight = inp[i].takeLast(numberOfElementsBelow).indexOfFirst { it >= checking }
    val ctrRight = if (idxBorderRight == -1) numberOfElementsBelow else if (idxBorderRight == 0) 1 else idxBorderRight + 1

    return ctrUp * ctrDown * ctrLeft * ctrRight
}

fun isVisible(i: Int, j: Int, inp: List<List<Int>>): Boolean {
    val checking = inp[i][j]
    return inp.map { row -> row[j] }.take(i).none { it >= checking } || inp.map { row -> row[j] }.takeLast(inp.size - 1 - i)
        .none { it >= checking } || inp[i].take(j).none { it >= checking } || inp[i].takeLast(inp[0].size - j - 1).none { it >= checking }
}
