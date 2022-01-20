package solutions.day06

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(6)
    println("part1 = " + part1(inputLines))
    println("part2 = " + part2(inputLines))
}

fun part1(input: List<String>): Int {
    return input.fold(Array(1000) { BooleanArray(1000) }) { acc, command ->
        acc.applyCommand(
            getCommandFromString(
                command
            )
        )
    }.flatMap(BooleanArray::asIterable).count { it }
}

fun part2(input: List<String>): Int {
    return input.fold(Array(1000) { IntArray(1000) }) { acc, command ->
        acc.applyCommand(
            getCommandFromString(
                command
            )
        )
    }.flatMap(IntArray::asIterable).sum()
}

fun getCommandFromString(command: String): Command {
    val regex = Regex("(.+) (\\d+),(\\d+) through (\\d+),(\\d+)")
    val groups = regex.matchEntire(command)!!.groups
    return Command(
        groups[1]!!.value, Pair(groups[2]!!.value.toInt(), groups[3]!!.value.toInt()),
        Pair(groups[4]!!.value.toInt(), groups[5]!!.value.toInt())
    )
}

fun Array<BooleanArray>.applyCommand(command: Command): Array<BooleanArray> {
    (command.start.first..command.end.first)
        .flatMap { a -> (command.start.second..command.end.second).map { b -> a to b } }
        .forEach { pair ->
            when (command.type) {
                "toggle" -> this[pair.first][pair.second] = !this[pair.first][pair.second]
                "turn on" -> this[pair.first][pair.second] = true
                "turn off" -> this[pair.first][pair.second] = false
            }
        }
    return this
}

fun Array<IntArray>.applyCommand(command: Command): Array<IntArray> {
    (command.start.first..command.end.first)
        .flatMap { a -> (command.start.second..command.end.second).map { b -> a to b } }
        .forEach { pair ->
            when (command.type) {
                "toggle" -> this[pair.first][pair.second] += 2
                "turn on" -> this[pair.first][pair.second] += 1
                "turn off" -> if (this[pair.first][pair.second] > 0) this[pair.first][pair.second]-- else this[pair.first][pair.second] = 0
            }
        }
    return this
}

data class Command(val type: String, val start: Pair<Int, Int>, val end: Pair<Int, Int>)
