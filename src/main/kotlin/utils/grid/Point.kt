package utils.grid

data class Point(val y: Int, val x: Int) {
    fun left(): Point = this.copy(x = this.x - 1)
    fun right(): Point = this.copy(x = this.x + 1)
    fun up(): Point = this.copy(y = this.y - 1)
    fun down(): Point = this.copy(y = this.y + 1)

    fun <T> isInRange(grid: Collection<Collection<T>>): Boolean {
        if (grid.isEmpty()) {
            return false
        }

        val outer = grid.toList()
        return y in outer.indices && x in outer[y].toList().indices
    }

    override fun toString(): String {
        return "Point(y=$y, x=$x)"
    }

    fun moveInDir(direction: Direction): Point {
        return Point(this.y + direction.y, this.x + direction.x)
    }
}

fun Pair<Int, Int>.toPoint(): Point {
    return Point(this.first, this.second)
}

fun <T> List<List<T>>.get(point: Point): T {
    return this[point.y][point.x]
}

fun allPointsBetween(p1: Point, p2: Point): List<Point> {
    val yRange = if (p1.y <= p2.y) (p1.y..p2.y) else (p1.y downTo p2.y)
    val xRange = if (p1.x <= p2.x) (p1.x..p2.x) else (p1.x downTo p2.x)

    return yRange.flatMap { y ->
        xRange.map { x -> Point(y, x) }
    }
}

// todo review this method
fun allPointsInLineBetween(p1: Point, p2: Point): List<Point> {
    val points = mutableListOf<Point>()

    var (x1, y1) = p1.x to p1.y
    val (x2, y2) = p2.x to p2.y

    val dx = kotlin.math.abs(x2 - x1)
    val dy = kotlin.math.abs(y2 - y1)
    val sx = if (x1 < x2) 1 else -1
    val sy = if (y1 < y2) 1 else -1
    var err = dx - dy

    while (true) {
        points.add(Point(y1, x1))
        if (x1 == x2 && y1 == y2) {
            break
        }
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
