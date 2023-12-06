package solutions.aoc2023.day06

import utils.Resources

fun main() {

    val inputLine =
//        Resources.getLines(2023, 6)
        Resources.getLinesExample(2023, 6)
    println("part1 = ${part1(inputLine)}")
    println("part1 = ${part2(inputLine)}")

}
fun part1(input: List<String>): Int {

    fun getRanges(time: Int, dist: Int): MutableList<Int> {
        var totDist = dist
        var list = mutableListOf<Int>()
        for (hold in 1..time) {
            var res = (time - hold) * hold
            list.add(res)
        }
        return list
    }

    input.forEach { it.log("p1") }

//    var inpS = listOf(7, 15, 30)
//    var inpD = listOf(9, 40, 200)
    var inpS = listOf(  53   ,  71    , 78    , 80)
    var inpD = listOf(275, 1181, 1215, 1524)

    // time allow the best distance

    var res = 0;
    var ans = 1
    repeat(4) {

        val ranges = getRanges(time = inpS[it], dist = inpD[it]).filter { r -> r > inpD[it] }.size
        res += ranges
        ranges.log()
        ans *= ranges
    }

    ans.log("answer")

    return 12
}





fun part2(input: List<String>): Int {

    fun getRanges(time: Int, dist: Long): Int {
        var totDist = dist
        var list = mutableListOf<Int>()
        var ctr = 0
        for (hold in 1..<time) {
            var holdBig = hold.toBigInteger()
            var res = (time - hold).toBigInteger()
            var sec = res.times(holdBig)
            if (sec.compareTo(dist.toBigInteger()) == 1) {
//            hold.log()
                ctr++
            }
        }
        return ctr
    }


    input.forEach { it.log("p1") }

//    var inpS = listOf(7, 15, 30)
//    var inpD = listOf(9, 40, 200)
//    var inpS = listOf(53   ,  71   ,  78    , 80)
//    var inpD = listOf(275, 1181, 1215, 1524)

    // time allow the best distance

    var res = 0;
    var ans = 1
    275118112151524
    val ranges = getRanges(53717880, 275118112151524)
//    val ranges = getRanges(71530, 940200)
    ranges.log()
    ans *= ranges

    ans.log("answer")

    return 12
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



