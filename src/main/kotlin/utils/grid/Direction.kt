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
}