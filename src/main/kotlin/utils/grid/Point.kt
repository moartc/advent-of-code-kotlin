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


