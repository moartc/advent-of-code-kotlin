package utils


/**
 * Coordinates apply to a matrix, with indices as below (in the "yx" form):
 * 00 01 02 03
 * 10 11 12 13
 * 20 21 22 23
 * 30 31 32 33
 */
enum class Direction(val y: Int, val x: Int) {


    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    fun getOpposite(): Direction {
        return when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }

    fun turnClockwise(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun turnCounterClockwise(): Direction {
        return when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }
    }

    fun isOpposite(dir: Direction): Boolean {
        return when (dir) {
            NORTH -> this == SOUTH
            EAST -> this == WEST
            SOUTH -> this == NORTH
            WEST -> this == EAST
        }
    }
}