package utils.math

import kotlin.math.pow

/**
 * Returns only Long values (null for Double solution)
 */
fun solveCramerLong(coefficients: Array<LongArray>, constants: LongArray): LongArray? {
    val n = constants.size
    val determinant = calcDet(coefficients)
    if (determinant == 0L) {
        return null
    }
    return LongArray(n) { i ->
        val modifiedMatrix = replaceColumn(coefficients, constants, i)
        val solution = calcDet(modifiedMatrix) / determinant
        if (solution * determinant == calcDet(modifiedMatrix)) {
            solution
        } else {
            return null
        }
    }
}

fun solveCramerDouble(coefficients: Array<DoubleArray>, constants: DoubleArray): DoubleArray? {
    val n = constants.size
    val determinant = calcDet(coefficients)
    if (determinant == 0.0) {
        return null
    }
    return DoubleArray(n) { i ->
        val modifiedMatrix = replaceColumn(coefficients, constants, i)
        calcDet(modifiedMatrix) / determinant
    }
}

fun calcDet(matrix: Array<LongArray>): Long {
    val n = matrix.size
    if (n == 1) return matrix[0][0]
    if (n == 2) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    }
    var determinant = 0L
    for (i in 0 until n) {
        val subMatrix = Array(n - 1) { row ->
            LongArray(n - 1) { col ->
                matrix[row + 1][if (col >= i) col + 1 else col]
            }
        }
        determinant += (-1L).toDouble().pow(i).toLong() * matrix[0][i] * calcDet(subMatrix)
    }
    return determinant
}

private fun replaceColumn(matrix: Array<LongArray>, column: LongArray, colIndex: Int): Array<LongArray> {
    return Array(matrix.size) { row ->
        LongArray(matrix.size) { col ->
            if (col == colIndex) column[row] else matrix[row][col]
        }
    }
}

fun calcDet(matrix: Array<DoubleArray>): Double {
    val n = matrix.size
    if (n == 1) {
        return matrix[0][0]
    }
    if (n == 2) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    }
    var determinant = 0.0
    for (i in 0 until n) {
        val subMatrix = Array(n - 1) { row ->
            DoubleArray(n - 1) { col ->
                matrix[row + 1][if (col >= i) col + 1 else col]
            }
        }
        determinant += (-1.0).pow(i) * matrix[0][i] * calcDet(subMatrix)
    }
    return determinant
}

private fun replaceColumn(matrix: Array<DoubleArray>, column: DoubleArray, colIndex: Int): Array<DoubleArray> {
    return Array(matrix.size) { row ->
        DoubleArray(matrix.size) { col ->
            if (col == colIndex) column[row] else matrix[row][col]
        }
    }
}