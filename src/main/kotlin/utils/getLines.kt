package utils

class Resources {
    companion object Input {
        fun getLines(day: Int): List<String> {
            val dayNb = "%02d".format(day)
            return this::class.java.getResourceAsStream("/solutions/day$dayNb.txt").bufferedReader().readLines()
        }

        fun getLine(day: Int) = getLines(day)[0]
    }
}
