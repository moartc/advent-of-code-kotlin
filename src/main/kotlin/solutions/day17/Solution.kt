package solutions.day17

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(17)
    val containers = inputLines.map(String::toInt)
    println("part1 = ${part1(containers, 150)}")
    println("part2 = ${part2(containers, 150)}")
}

fun part1(containers: List<Int>, storeValue: Int) = solve(emptyList(), containers, storeValue).size

fun part2(containers: List<Int>, liters: Int) =
    solve(emptyList(), containers, liters).groupingBy { it.size }.eachCount().minByOrNull { it.key }!!.value

fun solve(curr: List<Int>, allContainers: List<Int>, storeVal: Int): List<List<Int>> {
    return if (curr.sum() == storeVal) {
        listOf(curr)
    } else {
        allContainers.filter { container -> curr.sum() + container <= storeVal }.run {
            this.flatMapIndexed { index, c -> solve(curr.plus(c), this.subList(index + 1, this.size), storeVal) }
        }
    }
}