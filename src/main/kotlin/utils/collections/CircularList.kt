package utils.collections

class CircularList<out T>(private val list: List<T>) : List<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size
}