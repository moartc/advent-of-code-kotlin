package utils

fun printGridFromPoints(points: List<Pair<Int, Int>>) {
    val maxX = points.maxOfOrNull { it.first } ?: 0
    val maxY = points.maxOfOrNull { it.second } ?: 0
    val minX = points.minOfOrNull { it.first } ?: 0
    val minY = points.minOfOrNull { it.second } ?: 0

    val gridWidth = maxX - minX + 1
    val gridHeight = maxY - minY + 1

    val grid = Array(gridHeight) { CharArray(gridWidth) { '.' } }

    points.forEach { (x, y) ->
        val adjustedX = x - minX
        val adjustedY = y - minY
        grid[adjustedY][adjustedX] = 'X'
    }

    grid.forEach { row ->
        println(row.joinToString(" "))
    }
}