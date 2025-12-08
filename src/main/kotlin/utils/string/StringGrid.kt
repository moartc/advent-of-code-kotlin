package utils.string

/**
 * Treats List<String> as a 2D grid and rotates it
 */
fun List<String>.rotateClockwise(): List<String> {
    val rows = size
    val cols = this[0].length
    return List(cols) { y ->
        String(CharArray(rows) { x -> this[rows - 1 - x][y] })
    }
}

fun List<String>.rotateCounterClockwise(): List<String> {
    val rows = size
    val cols = this[0].length
    return List(cols) { y ->
        String(CharArray(rows) { x -> this[x][cols - 1 - y] })
    }
}

fun List<String>.rotate180(): List<String> {
    return this.reversed().map { it.reversed() }
}

/**
 * Get column as string
 */
fun List<String>.getColumn(index: Int): String {
    return this.map { it[index] }.joinToString("")
}

/**
 * Get all columns
 */
fun List<String>.columns(): List<String> {
    return (0 until this[0].length).map { getColumn(it) }
}

/**
 * Replace character at position
 */
fun List<String>.setAt(y: Int, x: Int, char: Char): List<String> {
    return this.mapIndexed { index, row ->
        if (index == y) {
            row.substring(0, x) + char + row.substring(x + 1)
        } else row
    }
}

/**
 * Check if position is valid
 */
fun List<String>.isValidPosition(y: Int, x: Int): Boolean {
    return y in indices && x in this[0].indices
}

/**
 * Get character safely (returns null if out of bounds)
 */
fun List<String>.getOrNull(y: Int, x: Int): Char? {
    return if (isValidPosition(y, x)) this[y][x] else null
}