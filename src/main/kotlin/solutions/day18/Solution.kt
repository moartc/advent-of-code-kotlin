package solutions.day18

import utils.Resources

fun main() {

    val input = Resources.getLines(18).map { it.toList() }
    println("part1 = " + part1(input, 100))
    println("part2 = " + part2(input, 100))
}

fun part1(input: List<List<Char>>, steps: Int) =
    generateSequence(input) { step(it) }.take(steps + 1).last().sumOf { i -> i.count { it == '#' } }

fun part2(input: List<List<Char>>, steps: Int) =
    generateSequence(input) { stepPart2(it) }.take(steps + 1).last().sumOf { i -> i.count { it == '#' } }

fun step(input: List<List<Char>>): List<List<Char>> =
    input.mapIndexed { y, line -> line.mapIndexed { x, c -> c.switch(getNeighbors(Pair(y, x), input)) } }

fun stepPart2(input: List<List<Char>>): List<List<Char>> {
    val fixed =
        input.mapIndexed { y, line -> line.mapIndexed { x, c -> if (isCorner(Pair(y, x), input.size)) '#' else c } }
    return fixed.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (isCorner(Pair(y, x), fixed.size)) '#' else c.switch(getNeighbors(Pair(y, x), fixed))
        }
    }
}

fun isCorner(coordinates: Pair<Int, Int>, size: Int): Boolean {
    val f = coordinates.first
    val s = coordinates.second
    return f == 0 && s == 0 || f == 0 && s == size - 1 || f == size - 1 && s == 0 || f == size - 1 && s == size - 1
}

fun getNeighbors(p: Pair<Int, Int>, array: List<List<Char>>): List<Char> {
    return (p.first - 1..p.first + 1).filter { it >= 0 && it < array.size }.flatMap { y ->
        (p.second - 1..p.second + 1).filter { it >= 0 && it < array.size && !(y == p.first && it == p.second) }
            .map { x -> array[y][x] }
    }
}

fun Char.switch(neighbors: List<Char>): Char {
    val count = neighbors.count { it == '#' }
    return if (count == 3 || (this == '#' && count == 2)) '#' else '.'
}