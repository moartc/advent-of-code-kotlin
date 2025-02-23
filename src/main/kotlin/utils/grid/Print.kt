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
        println(row.joinToString(" "))
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
        println(row.joinToString(" "))
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
        println(row.joinToString(" "))
    }
}