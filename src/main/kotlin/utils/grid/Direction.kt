package utils.grid


/**
 * Coordinates apply to a matrix, with indices as below (in the "yx" form):
 * 00 01 02 03
 * 10 11 12 13
 * 20 21 22 23
 * 30 31 32 33
 */
enum class Direction(val y: Int, val x: Int) {


    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    fun getOpposite(): Direction {
        return when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    }

    fun turnClockwise(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    fun turnCounterClockwise(): Direction {
        return when (this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }
    }

    fun isOpposite(dir: Direction): Boolean {
        return when (dir) {
            UP -> this == DOWN
            RIGHT -> this == LEFT
            DOWN -> this == UP
            LEFT -> this == RIGHT
        }
    }

    fun turn180(): Direction = getOpposite()

    fun turn(turns: Int): Direction {
        var dir = this
        repeat(turns.mod(4)) {
            dir = dir.turnClockwise()
        }
        return dir
    }

    /**
     * Returns direction as a unit vector Point
     */
    fun toPoint(): Point = Point(y, x)

    companion object {
        val ALL = listOf(UP, RIGHT, DOWN, LEFT)
        val CARDINAL = ALL  // alias

        fun from(char: Char): Direction? = when (char) {
            'U', 'u', '^', 'N', 'n' -> UP
            'R', 'r', '>', 'E', 'e' -> RIGHT
            'D', 'd', 'v', 'V', 'S', 's' -> DOWN
            'L', 'l', '<', 'W', 'w' -> LEFT
            else -> null
        }

        fun from(y: Int, x: Int): Direction? = when {
            y == -1 && x == 0 -> UP
            y == 0 && x == 1 -> RIGHT
            y == 1 && x == 0 -> DOWN
            y == 0 && x == -1 -> LEFT
            else -> null
        }
    }
}

// Diagonal directions for 8-way movement
enum class Direction8(val y: Int, val x: Int) {
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    RIGHT(0, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1),
    LEFT(0, -1),
    UP_LEFT(-1, -1);

    fun toPoint(): Point = Point(y, x)

    companion object {
        val ALL = listOf(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
    }
}