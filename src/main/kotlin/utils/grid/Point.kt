package utils.grid

import kotlin.math.abs

data class Point(val y: Int, val x: Int) {
    fun left(): Point = this.copy(x = this.x - 1)
    fun right(): Point = this.copy(x = this.x + 1)
    fun up(): Point = this.copy(y = this.y - 1)
    fun down(): Point = this.copy(y = this.y + 1)

    fun <T> isInRange(grid: Collection<Collection<T>>): Boolean {
        if (grid.isEmpty()) return false
        val outer = grid.toList()
        return y in outer.indices && x in outer[y].toList().indices
    }

    fun isInRange(height: Int, width: Int): Boolean {
        return y in 0 until height && x in 0 until width
    }

    override fun toString(): String = "Point(y=$y, x=$x)"

    fun moveInDir(direction: Direction): Point {
        return Point(this.y + direction.y, this.x + direction.x)
    }

    // NEW ADDITIONS

    operator fun plus(other: Point) = Point(y + other.y, x + other.x)
    operator fun minus(other: Point) = Point(y - other.y, x - other.x)
    operator fun times(scalar: Int) = Point(y * scalar, x * scalar)

    fun move(direction: Direction, steps: Int = 1): Point {
        return Point(y + direction.y * steps, x + direction.x * steps)
    }

    fun move(dy: Int, dx: Int): Point = Point(y + dy, x + dx)

    /**
     * Manhattan distance to another point
     */
    fun manhattanDistanceTo(other: Point): Int {
        return abs(y - other.y) + abs(x - other.x)
    }

    /**
     * Euclidean distance to another point
     */
    fun distanceTo(other: Point): Double {
        val dy = (y - other.y).toDouble()
        val dx = (x - other.x).toDouble()
        return kotlin.math.sqrt(dy * dy + dx * dx)
    }

    /**
     * Get all 4 orthogonal neighbors
     */
    fun neighbors(): List<Point> = Direction.ALL.map { moveInDir(it) }

    /**
     * Get all 4 orthogonal neighbors that are in range
     */
    fun <T> neighborsInRange(grid: Collection<Collection<T>>): List<Point> {
        return neighbors().filter { it.isInRange(grid) }
    }

    /**
     * Get all 8 neighbors (including diagonals)
     */
    fun neighbors8(): List<Point> = buildList {
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dy != 0 || dx != 0) {
                    add(Point(y + dy, x + dx))
                }
            }
        }
    }

    /**
     * Get all 8 neighbors that are in range
     */
    fun <T> neighbors8InRange(grid: Collection<Collection<T>>): List<Point> {
        return neighbors8().filter { it.isInRange(grid) }
    }

    companion object {
        val ZERO = Point(0, 0)
    }
}

fun Pair<Int, Int>.toPoint(): Point = Point(this.first, this.second)

fun <T> List<List<T>>.get(point: Point): T = this[point.y][point.x]

fun <T> List<List<T>>.getOrNull(point: Point): T? {
    return if (point.isInRange(this)) this[point.y][point.x] else null
}

fun List<String>.get(point: Point): Char = this[point.y][point.x]

fun List<String>.getOrNull(point: Point): Char? {
    return if (point.y in indices && point.x in this[point.y].indices) {
        this[point.y][point.x]
    } else null
}

fun allPointsBetween(p1: Point, p2: Point): List<Point> {
    val yRange = if (p1.y <= p2.y) (p1.y..p2.y) else (p1.y downTo p2.y)
    val xRange = if (p1.x <= p2.x) (p1.x..p2.x) else (p1.x downTo p2.x)

    return yRange.flatMap { y ->
        xRange.map { x -> Point(y, x) }
    }
}

/**
 * Bresenham's line algorithm - all points on a line between two points
 */
fun allPointsInLineBetween(p1: Point, p2: Point): List<Point> {
    val points = mutableListOf<Point>()

    var (x1, y1) = p1.x to p1.y
    val (x2, y2) = p2.x to p2.y

    val dx = abs(x2 - x1)
    val dy = abs(y2 - y1)
    val sx = if (x1 < x2) 1 else -1
    val sy = if (y1 < y2) 1 else -1
    var err = dx - dy

    while (true) {
        points.add(Point(y1, x1))
        if (x1 == x2 && y1 == y2) break

        val e2 = 2 * err
        if (e2 > -dy) {
            err -= dy
            x1 += sx
        }
        if (e2 < dx) {
            err += dx
            y1 += sy
        }
    }
    return points
}

/**
 * Generate all points in a grid
 */
fun allPointsInGrid(height: Int, width: Int): List<Point> {
    return (0 until height).flatMap { y ->
        (0 until width).map { x -> Point(y, x) }
    }
}

/**
 * Find all points in a grid that match a predicate
 */
fun <T> List<List<T>>.findPoints(predicate: (T) -> Boolean): List<Point> {
    return flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, value ->
            if (predicate(value)) Point(y, x) else null
        }
    }
}

fun List<String>.findPoints(char: Char): List<Point> {
    return flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == char) Point(y, x) else null
        }
    }
}

fun List<String>.findPoint(char: Char): Point? {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (c == char) return Point(y, x)
        }
    }
    return null
}