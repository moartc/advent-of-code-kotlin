package solutions.aoc2017.day02

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(2017, 2)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun part1(input: List<String>): Int {
    return input.map { line -> line.split("\\s".toRegex()) }
        .map { line -> line.map { str -> str.toInt() } }
        .sumOf { list -> list.max() - list.min() }
}

fun part2(input: List<String>): Int {
    return input.map { line -> line.split("\\s".toRegex()) }.toList()
        .map { line -> line.map { str -> str.toInt() } }.toList()
        .map { line -> line.cartesianProduct(line) }
        .map { line -> getPair(line) }
        .sumOf { pair -> pair.first / pair.second }
}

fun getPair(listOfPair: List<Pair<Int, Int>>): Pair<Int, Int> {
    return listOfPair.find { list -> list.first != list.second && list.first % list.second == 0 }!!
}

fun <T, S> Collection<T>.cartesianProduct(other: Iterable<S>): List<Pair<T, S>> {
    return cartesianProduct(other) { first, second -> first to second }
}

fun <T, S, V> Collection<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer.invoke(first, second) } }
}