package solutions.day15

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(15)
    val ingredients = parseReindeerList(inputLines)

    val part1 = part1(ingredients)
    val part2 = part2(ingredients)
    println("part1 = $part1 OK? - ${part1 == 13882464}")
    println("part2 = $part2 OK? - ${part2 == 11171160}")
}

fun part1(ingredients: List<Ingredient>): Int {

    return generatePermutations(100, ingredients.size).maxOf { perm ->
        val capacity = ingredients.mapIndexed { index, ingredient -> ingredient.capacity * perm[index] }.sum()
        val durability = ingredients.mapIndexed { index, ingredient -> ingredient.durability * perm[index] }.sum()
        val flavor = ingredients.mapIndexed { index, ingredient -> ingredient.flavor * perm[index] }.sum()
        val texture = ingredients.mapIndexed { index, ingredient -> ingredient.texture * perm[index] }.sum()
        (if (capacity > 0) capacity else 0) * (if (durability > 0) durability else 0) * (if (flavor > 0) flavor else 0) * (if (texture > 0) texture else 0)
    }
}

fun part2(ingredients: List<Ingredient>): Int {
    return generatePermutations(100, ingredients.size).map { perm ->
        val capacity = ingredients.mapIndexed { index, ingredient -> ingredient.capacity * perm[index] }.sum()
        val durability = ingredients.mapIndexed { index, ingredient -> ingredient.durability * perm[index] }.sum()
        val flavor = ingredients.mapIndexed { index, ingredient -> ingredient.flavor * perm[index] }.sum()
        val texture = ingredients.mapIndexed { index, ingredient -> ingredient.texture * perm[index] }.sum()
        val total =
            (if (capacity > 0) capacity else 0) * (if (durability > 0) durability else 0) * (if (flavor > 0) flavor else 0) * (if (texture > 0) texture else 0)
        val calories = ingredients.mapIndexed { index, ingredient -> ingredient.calories * perm[index] }.sum()
        Pair(total, calories)
    }.filter { it.second == 500 }.maxByOrNull { it.first }!!.first
}

fun generatePermutations(totalSum: Int, size: Int) =
    (0..totalSum).map { value -> appendValueToRow2(totalSum - value, size, listOf(value)) }.flatten()

fun appendValueToRow2(sum: Int, totalSize: Int, currentRow: List<Int>): List<List<Int>> =
    currentRow.map {
        return if (currentRow.size == totalSize - 1) {
            listOf(currentRow + sum)
        } else {
            (0..sum).map { value -> appendValueToRow2(sum - value, totalSize, currentRow + value) }.flatten()
        }
    }

fun parseReindeerList(input: List<String>) =
    input.map { line -> """.* (-?\d+).* (-?\d+).* (-?\d+).* (-?\d+).* (-?\d+)""".toRegex().matchEntire(line) }
        .map { matchResult -> matchResult!!.groups }.map { group -> Ingredient(group) }

class Ingredient(val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int) {
    constructor(mgc: MatchGroupCollection) : this(
        mgc[1]!!.value.toInt(),
        mgc[2]!!.value.toInt(),
        mgc[3]!!.value.toInt(),
        mgc[4]!!.value.toInt(),
        mgc[5]!!.value.toInt()
    )
}