package utils

class Resources {
    companion object Input {
        fun getLines(year: Int, day: Int): List<String> {
            val dayNb = "%02d".format(day)
            return this::class.java.getResourceAsStream("/aoc${year}/day$dayNb.txt").bufferedReader()
                .readLines()
        }

        fun getLine(year: Int, day: Int) = getLines(year, day)[0]
    }
}
