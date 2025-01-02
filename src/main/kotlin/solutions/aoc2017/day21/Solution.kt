package solutions.aoc2017.day21

import utils.Resources
import utils.array.extensions.rotateClockwise

fun main() {
    val input = Resources.getLines(2017, 21)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

val start = ".#.\n..#\n###".split("\n")

fun part1(input: List<String>): Int {
    return solve(input, 5)
}

fun part2(input: List<String>): Int {
    return solve(input, 18)
}

fun solve(input: List<String>, numberOfRepetition: Int): Int {

    val start = start

    val mapping = input.map { line -> line.split(" => ") }.map {
        val left = it[0].split("/")
        val right = it[1].split("/")
        left to right
    }.toMap()

    fun findMatching(sq: Array<Array<Char>>, mapping: Map<List<String>, List<String>>): List<String> {
        val allVariants = listOf(
            sq,
            sq.rotateClockwise(),
            sq.rotateClockwise().rotateClockwise(),
            sq.rotateClockwise().rotateClockwise().rotateClockwise(),
            sq.reversed().toTypedArray(),
            sq.rotateClockwise().reversed().toTypedArray(),
            sq.rotateClockwise().rotateClockwise().reversed().toTypedArray(),
            sq.rotateClockwise().rotateClockwise().rotateClockwise().reversed().toTypedArray(),
        )
        for (entry in mapping.entries) {
            val k = entry.key
            if (sq.size == 2 && k.size == 2) {
                if (allVariants.any { x -> k[0] == x[0].joinToString("") && k[1] == x[1].joinToString("") }) {
                    return entry.value
                }
            } else if (allVariants.any { x -> k[0] == x[0].joinToString("") && k[1] == x[1].joinToString("") && k[2] == x[2].joinToString("") }) {
                return entry.value
            }
        }

        throw Exception("Cannot find")
    }

    fun divideMatrix(matrix: List<String>): List<Array<Array<Char>>> {
        val n = matrix.size
        val subSize = when {
            n % 2 == 0 -> 2
            n % 3 == 0 -> 3
            else -> throw IllegalArgumentException("Matrix size must be divisible by 3 or 2!")
        }

        val submatrices = mutableListOf<Array<Array<Char>>>()

        for (row in 0 until n step subSize) {
            for (col in 0 until n step subSize) {
                val submatrix = Array(subSize) { Array(subSize) { ' ' } }
                for (i in 0 until subSize) {
                    for (j in 0 until subSize) {
                        submatrix[i][j] = matrix[row + i][col + j]
                    }
                }
                submatrices.add(submatrix)
            }
        }
        return submatrices
    }

    fun recreateMatrix(submatrices: MutableList<List<String>>): Array<Array<Char>> {
        if (submatrices.isEmpty()) throw IllegalArgumentException("Submatrices list is empty.")
        val subSize = submatrices[0].size
        val numSubmatrices = submatrices.size
        val gridSize = Math.sqrt(numSubmatrices.toDouble()).toInt() // Determine grid of submatrices
        val n = gridSize * subSize
        val originalMatrix = Array(n) { Array(n) { ' ' } }

        for (subRow in 0 until gridSize) {
            for (subCol in 0 until gridSize) {
                val submatrix = submatrices[subRow * gridSize + subCol]
                for (i in 0 until subSize) {
                    for (j in 0 until subSize) {
                        originalMatrix[subRow * subSize + i][subCol * subSize + j] = submatrix[i][j]
                    }
                }
            }
        }
        return originalMatrix
    }

    var current = start
    repeat(numberOfRepetition) {
        val list = mutableListOf<List<String>>()
        val new = divideMatrix(current)
        for (arrays in new) {
            val match = findMatching(arrays, mapping)
            list.add(match)
        }
        val recreateMatrix = recreateMatrix(list)
        current = recreateMatrix.map { it.joinToString("") }
    }
    var sum = 0
    for (s in current) {
        sum += s.count { x -> x == '#' }
    }
    return sum
}