package utils.point

data class Point3D(val x: Long, val y: Long, val z: Long) {

    constructor(x: Int, y: Int, z: Int) : this(x.toLong(), y.toLong(), z.toLong())
    constructor(x: Double, y: Double, z: Double) :
            this(x.toLong(), y.toLong(), z.toLong())

    operator fun plus(other: Point3D) =
        Point3D(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point3D) =
        Point3D(x - other.x, y - other.y, z - other.z)

    operator fun times(scalar: Long) =
        Point3D(x * scalar, y * scalar, z * scalar)

    operator fun div(scalar: Long) =
        Point3D(x / scalar, y / scalar, z / scalar)

    operator fun unaryMinus() = Point3D(-x, -y, -z)

    infix fun dot(other: Point3D): Long =
        x * other.x + y * other.y + z * other.z

    infix fun cross(other: Point3D) =
        Point3D(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        )

    fun distanceTo(other: Point3D): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return kotlin.math.sqrt(dx * dx + dy * dy + dz * dz)
    }

    // Manhattan distance
    fun manhattanDistanceTo(other: Point3D): Long =
        kotlin.math.abs(x - other.x) + kotlin.math.abs(y - other.y) + kotlin.math.abs(z - other.z)

    // Squared distance - useful when you don't need the sqrt (faster)
    fun squaredDistanceTo(other: Point3D): Long {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }

    // Magnitude/length from origin
    fun magnitude(): Double = distanceTo(ZERO)

    fun manhattanMagnitude(): Long = kotlin.math.abs(x) + kotlin.math.abs(y) + kotlin.math.abs(z)

    // Get all 6 adjacent neighbors (useful for 3D grid problems)
    fun neighbors(): List<Point3D> = listOf(
        Point3D(x + 1, y, z),
        Point3D(x - 1, y, z),
        Point3D(x, y + 1, z),
        Point3D(x, y - 1, z),
        Point3D(x, y, z + 1),
        Point3D(x, y, z - 1)
    )

    // All 26 neighbors (including diagonals)
    fun neighborsWithDiagonals(): List<Point3D> = buildList {
        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    if (dx != 0 || dy != 0 || dz != 0) {
                        add(Point3D(x + dx, y + dy, z + dz))
                    }
                }
            }
        }
    }

    companion object {
        val ZERO = Point3D(0L, 0L, 0L)

        // Common direction vectors
        val UP = Point3D(0, 0, 1)
        val DOWN = Point3D(0, 0, -1)
        val LEFT = Point3D(-1, 0, 0)
        val RIGHT = Point3D(1, 0, 0)
        val FORWARD = Point3D(0, 1, 0)
        val BACKWARD = Point3D(0, -1, 0)
    }
}
