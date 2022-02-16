package solutions.day24

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(24).map { it.toInt() }
    println("part1 = ${solve(inputLines, 3)}")
    println("part2 = ${solve(inputLines, 4)}")
}

fun solve(input: List<Int>, groups: Int) = (1..input.size - 2).asSequence().map { size ->
    input.flatMapIndexed { index, startValue ->
        addNext(listOf(startValue), input.subList(index + 1, input.size), size, input.sum() / groups)
    }
}.filter { it.isNotEmpty() }.first().minOf { list -> list.fold(1L) { acc, i -> acc * i.toLong() } }

fun addNext(currentList: List<Int>, listOfAll: List<Int>, size: Int, sumToFind: Int): List<List<Int>> {
    return if (currentList.sum() > sumToFind || currentList.size > size) emptyList()
    else if (currentList.size == size && currentList.sum() == sumToFind) {
        listOf(currentList)
    } else {
        listOfAll.flatMapIndexed { idx, value -> addNext(currentList + value, listOfAll.subList(idx + 1, listOfAll.size), size, sumToFind)
        }
    }
}