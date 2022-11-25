package solutions.aoc2015.day15

import utils.Resources
import java.util.function.IntPredicate

fun main() {

    val inputLines = Resources.getLines(2015, 15)
    val ingredients = parseReindeerList(inputLines)
    println("part1 = " + part1(ingredients))
    println("part2 = " + part2(ingredients))
}

fun part1(ingredients: List<Ingredient>) = getAnswer(ingredients) { true }

fun part2(ingredients: List<Ingredient>) = getAnswer(ingredients) { it == 500 }

fun getAnswer(ingredients: List<Ingredient>, kcalFilter: IntPredicate) =
    generatePermutations(100, ingredients.size)
        .asSequence()
        .map { permutation -> permutation.mapIndexed { permutationIndex, value -> ingredients[permutationIndex].properties.map { p -> p * value } } }
        .map(::transposeArray)
        .map { property -> property.map(List<Int>::sum).map { i -> if (i > 0) i else 0 } }
        .filter { result -> kcalFilter.test(result[4]) }
        .maxOf { it.take(4).reduce(Int::times) }

fun transposeArray(list: List<List<Int>>) = List(list.first().size) { i -> list.map { it[i] } }

fun generatePermutations(totalSum: Int, size: Int) =
    (0..totalSum).map { value -> appendValueToRow(totalSum - value, size, listOf(value)) }.flatten()

fun appendValueToRow(sum: Int, totalSize: Int, currentRow: List<Int>): List<List<Int>> =
    currentRow.map {
        return if (currentRow.size == totalSize - 1) {
            listOf(currentRow + sum)
        } else {
            (0..sum).map { value -> appendValueToRow(sum - value, totalSize, currentRow + value) }.flatten()
        }
    }

fun parseReindeerList(input: List<String>) =
    input.map { line -> """.* (-?\d+).* (-?\d+).* (-?\d+).* (-?\d+).* (-?\d+)""".toRegex().matchEntire(line) }
        .map { matchResult -> matchResult!!.groups }.map { group -> Ingredient(group) }

class Ingredient(val properties: List<Int>) {
    constructor(mgc: MatchGroupCollection) : this(
        mgc.asSequence().drop(1).take(5).map { group -> group!!.value.toInt() }.toList()
    )
}