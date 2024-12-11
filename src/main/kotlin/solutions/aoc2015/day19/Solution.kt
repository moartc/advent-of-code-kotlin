package solutions.aoc2015.day19

import utils.Resources

fun main() {

    val input = Resources.getLines(2015, 19)
    println("part1 = " + part1(input))
    println("part2 = " + part2(input))
}

fun part1(input: List<String>): Int {
    val replacements = getReplacements(input)
    val molecule = getMolecule(input)
    return replacements.map { replacement -> replace(molecule, replacement) }.flatten().toSet().size
}

fun part2(input: List<String>): Int {
    val replacements = getReplacements(input).map { rep -> Pair(rep.second, rep.first) }
    val molecule = getMolecule(input)
    return findAnswer(molecule, replacements, 0)[0]
}

fun findAnswer(molecule: String, replacements: List<Pair<String, String>>, counter: Int): List<Int> {
    return if (molecule == "e") {
        println(counter)
        listOf(counter)
    } else {
        replacements.map { replacement -> replace(molecule, replacement) }
            .flatMap { mol -> mol.flatMap { m -> findAnswer(m, replacements.shuffled(), counter + 1) } }
    }
}

private fun replace(molecule: String, replacement: Pair<String, String>) =
    molecule.indicesOf(replacement.first)
        .map { idx -> molecule.replaceRange(idx, idx + replacement.first.length, replacement.second) }

private  fun getReplacements(input: List<String>) =
    input.subList(0, input.size - 2).map { rep -> rep.split(" => ") }.map { split -> split[0] to split[1] }

private fun getMolecule(input: List<String>) = input.last()

private fun String.indicesOf(toFind: String): List<Int> = toFind.toRegex().findAll(this).map { it.range.first }.toList()

/*
It doesn't always find the solution to part 2. If found, then it prints to the console.
Otherwise, the program must be run again until it is successful.
 */