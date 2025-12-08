package utils.grid

fun printGridFromPairsYX(points: List<Pair<Int, Int>>) {
    val maxY = points.maxOfOrNull { it.first } ?: 0
    val maxX = points.maxOfOrNull { it.second } ?: 0
    val minY = points.minOfOrNull { it.first } ?: 0
    val minX = points.minOfOrNull { it.second } ?: 0

    val gridHeight = maxY - minY + 1
    val gridWidth = maxX - minX + 1

    val grid = Array(gridHeight) { CharArray(gridWidth) { '.' } }

    points.forEach { (y, x) ->
        val adjustedY = y - minY
        val adjustedX = x - minX
        grid[adjustedY][adjustedX] = 'X'
    }

    grid.forEach { row ->
        println(row.joinToString(""))
    }
}

fun printGridFromPairsXY(points: List<Pair<Int, Int>>) {
    val maxY = points.maxOfOrNull { it.second } ?: 0
    val maxX = points.maxOfOrNull { it.first } ?: 0
    val minY = points.minOfOrNull { it.second } ?: 0
    val minX = points.minOfOrNull { it.first } ?: 0

    val gridHeight = maxY - minY + 1
    val gridWidth = maxX - minX + 1

    val grid = Array(gridHeight) { CharArray(gridWidth) { '.' } }

    points.forEach { (x, y) ->
        val adjustedY = y - minY
        val adjustedX = x - minX
        grid[adjustedY][adjustedX] = 'X'
    }

    grid.forEach { row ->
        println(row.joinToString(""))
    }
}

fun printGridFromPointsYX(points: List<Point>) {
    val maxY = points.maxOfOrNull { it.y } ?: 0
    val maxX = points.maxOfOrNull { it.x } ?: 0
    val minY = points.minOfOrNull { it.y } ?: 0
    val minX = points.minOfOrNull { it.x } ?: 0

    val gridHeight = maxY - minY + 1
    val gridWidth = maxX - minX + 1

    val grid = Array(gridHeight) { CharArray(gridWidth) { '.' } }

    points.forEach { (y, x) ->
        val adjustedY = y - minY
        val adjustedX = x - minX
        grid[adjustedY][adjustedX] = 'X'
    }

    grid.forEach { row ->
        println(row.joinToString(""))
    }
}

// NEW ADDITIONS

/**
 * Print a grid with custom marker
 */
fun printGridFromPoints(points: List<Point>, marker: Char = '#', empty: Char = '.') {
    if (points.isEmpty()) return

    val maxY = points.maxOf { it.y }
    val maxX = points.maxOf { it.x }
    val minY = points.minOf { it.y }
    val minX = points.minOf { it.x }

    val pointSet = points.toSet()

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            print(if (Point(y, x) in pointSet) marker else empty)
        }
        println()
    }
}

/**
 * Print a grid with custom chars for specific points
 */
fun printGridWithLabels(points: Map<Point, Char>, empty: Char = '.') {
    if (points.isEmpty()) return

    val maxY = points.keys.maxOf { it.y }
    val maxX = points.keys.maxOf { it.x }
    val minY = points.keys.minOf { it.y }
    val minX = points.keys.minOf { it.x }

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            print(points[Point(y, x)] ?: empty)
        }
        println()
    }
}

/**
 * Print List<String> grid with highlighted points
 */
fun List<String>.printWithHighlights(highlights: Set<Point>, marker: Char = '*') {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            print(if (Point(y, x) in highlights) marker else c)
        }
        println()
    }
}

/**
 * Print grid dimensions
 */
fun List<String>.printDimensions() {
    println("Grid: ${size}x${firstOrNull()?.length ?: 0}")
}