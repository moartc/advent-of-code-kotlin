package utils.array.extensions

fun <T> Array<T>.shiftLeftMutable(d: Int) {
    val n = d % this.size  // just in case
    if (n == 0) return  // no need to shift
    val left = this.copyOfRange(0, n)
    val right = this.copyOfRange(n, this.size)
    System.arraycopy(right, 0, this, 0, right.size)
    System.arraycopy(left, 0, this, right.size, left.size)
}

fun <T> Array<T>.shiftRightMutable(d: Int) {
    val n = d % this.size  // just in case
    if (n == 0) return  // no need to shift
    val left = this.copyOfRange(0, this.size - n)
    val right = this.copyOfRange(this.size - n, this.size)
    System.arraycopy(right, 0, this, 0, right.size)
    System.arraycopy(left, 0, this, right.size, left.size)
}

fun <T> Array<T>.shiftLeft(d: Int): Array<T> {
    val n = d % this.size  // just in case
    val left = this.copyOfRange(0, n)
    val right = this.copyOfRange(n, this.size)
    return right + left
}

fun <T> Array<T>.shiftRight(d: Int): Array<T> {
    val n = d % this.size  // just in case
    val left = this.copyOfRange(0, this.size - n)
    val right = this.copyOfRange(this.size - n, this.size)
    return right + left
}

inline fun <reified T> Array<Array<T>>.rotateClockwise(): Array<Array<T>> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> Array(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun <reified T> Array<Array<T>>.rotateCounterClockwise(): Array<Array<T>> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> Array(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun <reified T> Array<Array<T>>.rotate180(): Array<Array<T>> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> Array(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

inline fun <reified T> Array<Array<T>>.shiftColumn(columnIndex: Int, by: Int) {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows // Handle negative and large shifts
    if (shift == 0) {
        return
    }

    val tempColumn = Array(numRows) { rowIndex ->
        this[(rowIndex - shift + numRows) % numRows][columnIndex]
    }

    for (rowIndex in this.indices) {
        this[rowIndex][columnIndex] = tempColumn[rowIndex]
    }
}

inline fun <reified T> Array<Array<T>>.shiftColumnNoMut(columnIndex: Int, by: Int): Array<Array<T>> {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows
    if (shift == 0) {
        return this.map { it.clone() }.toTypedArray()
    }

    val newArray = this.map { it.clone() }.toTypedArray()

    for (rowIndex in this.indices) {
        val newRowIndex = (rowIndex + shift) % numRows
        newArray[newRowIndex][columnIndex] = this[rowIndex][columnIndex]
    }
    return newArray
}

// CharArray
inline fun Array<CharArray>.rotateClockwise(): Array<CharArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> CharArray(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun Array<CharArray>.rotateCounterClockwise(): Array<CharArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> CharArray(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun Array<CharArray>.rotate180(): Array<CharArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> CharArray(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

fun Array<CharArray>.shiftColumn(columnIndex: Int, by: Int) {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows // Handle negative and large shifts
    if (shift == 0) {
        return
    }

    val tempColumn = Array(numRows) { rowIndex ->
        this[(rowIndex - shift + numRows) % numRows][columnIndex]
    }

    for (rowIndex in this.indices) {
        this[rowIndex][columnIndex] = tempColumn[rowIndex]
    }
}

fun Array<CharArray>.shiftColumnNoMut(columnIndex: Int, by: Int): Array<CharArray> {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows
    if (shift == 0) {
        return this.map { it.clone() }.toTypedArray()
    }

    val newArray = this.map { it.clone() }.toTypedArray()

    for (rowIndex in this.indices) {
        val newRowIndex = (rowIndex + shift) % numRows
        newArray[newRowIndex][columnIndex] = this[rowIndex][columnIndex]
    }
    return newArray
}

fun Array<CharArray>.deepCopy(): Array<CharArray> = this.map { it.clone() }.toTypedArray()

// IntArray
inline fun Array<IntArray>.rotateClockwise(): Array<IntArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> IntArray(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun Array<IntArray>.rotateCounterClockwise(): Array<IntArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> IntArray(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun Array<IntArray>.rotate180(): Array<IntArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> IntArray(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

fun Array<IntArray>.deepCopy(): Array<IntArray> = this.map { it.clone() }.toTypedArray()

// LongArray
inline fun Array<LongArray>.rotateClockwise(): Array<LongArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> LongArray(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun Array<LongArray>.rotateCounterClockwise(): Array<LongArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> LongArray(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun Array<LongArray>.rotate180(): Array<LongArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> LongArray(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

fun Array<LongArray>.shiftColumn(columnIndex: Int, by: Int) {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows // Handle negative and large shifts
    if (shift == 0) {
        return
    }

    val tempColumn = Array(numRows) { rowIndex ->
        this[(rowIndex - shift + numRows) % numRows][columnIndex]
    }

    for (rowIndex in this.indices) {
        this[rowIndex][columnIndex] = tempColumn[rowIndex]
    }
}

fun Array<LongArray>.shiftColumnNoMut(columnIndex: Int, by: Int): Array<LongArray> {
    val numRows = this.size
    if (numRows == 0 || columnIndex < 0 || columnIndex >= this[0].size) {
        throw IllegalArgumentException("Invalid column index or empty array.")
    }

    val shift = (by % numRows + numRows) % numRows
    if (shift == 0) {
        return this.map { it.clone() }.toTypedArray()
    }

    val newArray = this.map { it.clone() }.toTypedArray()

    for (rowIndex in this.indices) {
        val newRowIndex = (rowIndex + shift) % numRows
        newArray[newRowIndex][columnIndex] = this[rowIndex][columnIndex]
    }
    return newArray
}

fun Array<LongArray>.deepCopy(): Array<LongArray> = this.map { it.clone() }.toTypedArray()

// DoubleArray
inline fun Array<DoubleArray>.rotateClockwise(): Array<DoubleArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> DoubleArray(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun Array<DoubleArray>.rotateCounterClockwise(): Array<DoubleArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> DoubleArray(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun Array<DoubleArray>.rotate180(): Array<DoubleArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> DoubleArray(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

fun Array<DoubleArray>.deepCopy(): Array<DoubleArray> = this.map { it.clone() }.toTypedArray()

// BooleanArray
inline fun Array<BooleanArray>.rotateClockwise(): Array<BooleanArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> BooleanArray(rows) { x -> this[rows - 1 - x][y] } }
}

inline fun Array<BooleanArray>.rotateCounterClockwise(): Array<BooleanArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(cols) { y -> BooleanArray(rows) { x -> this[x][cols - 1 - y] } }
}

inline fun Array<BooleanArray>.rotate180(): Array<BooleanArray> {

    var rows = this.size
    var cols = this[0].size
    return Array(rows) { y -> BooleanArray(cols) { x -> this[rows - 1 - y][cols - 1 - x] } }
}

fun Array<BooleanArray>.deepCopy(): Array<BooleanArray> = this.map { it.clone() }.toTypedArray()


