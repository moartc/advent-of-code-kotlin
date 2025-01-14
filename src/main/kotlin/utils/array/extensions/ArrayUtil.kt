package utils.array.extensions

fun printDoubleArray(array: Array<Array<Char>>) {
    for (rows in array) {
        for (v in rows) {
            print("$v ")
        }
        println()
    }
}