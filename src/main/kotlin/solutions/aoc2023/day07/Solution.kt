package solutions.aoc2023.day07

import utils.Resources
import utils.splitOn
import kotlin.math.log

fun main() {

    val inputLine =
        Resources.getLines(2023, 7)
//        Resources.getLinesExample(2023, 7)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part2(input: List<String>): Int {
    val vals = listOf<Char>('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    fun getValHand(hand: Map<Char, Int>): Int {

        val get = hand.get('J')

        val filter = hand.filter { k -> k.key != 'J' }.toMutableMap()


        if (filter.size == 0) {
            return 7
        }
        val sortedBy = filter.entries.sortedByDescending { it.value }.toMutableList()
        val value = sortedBy.first().value

        if (get != null) {
            sortedBy[0].setValue(value + get)
        }


        if (sortedBy[0].value == 5) {
            return 7
        }

        if (sortedBy[0].value == 4) {
            return 6
        }

        if (sortedBy[0].value == 3) { // full
            if (sortedBy[1].value == 2) {
                return 5

            } else {  // just 3
                return 4
            }
        }

        if (sortedBy[0].value == 2) {
            if (sortedBy[1].value == 2) { //2 pairs
                return 3
            } else { // pair
                return 2
            }
        }


        return 1 // single

    }

    val mComparator = Comparator<Pair<Pair<Map<Char, Int>, String>, Int>> { o1, o2 ->
        val f = getValHand(o1.first.first)
        val s = getValHand(o2.first.first)


        "compare ${o1.first.second} with ${o2.first.second}".log()
        when {
            f > s -> 1
            f < s -> -1
            else -> {
                var res = 0
                for (i in 0..4) {

                    o1.first.second[i].log("c1")
                    o2.first.second[i].log("c2")
                    val id1 = vals.indexOf(o1.first.second[i])
                    val id2 = vals.indexOf(o2.first.second[i])
                    if (id1 < id2) {
                        res = 1
                        break
                    }
                    if (id2 < id1) {
                        res = -1
                        break
                    }
                }
                "equals?".log()
                res
            }
        }
    }

    val maps = input.map { line ->

        val split = line.split(" ")
        val hand = split[0].groupingBy { it }.eachCount()
        val bid = split[1].toInt()
        (hand to split[0]) to bid
    }

    val sortedWith = maps.sortedWith(mComparator)

    sortedWith.log("sorted")

    var res = 0
    var ctr = 1
    sortedWith.forEach {
        val i = it.second * ctr
        ctr++
        it.first.log("hand check")
        res += i
    }


    res.log("result ")
    return res
}


fun part1(input: List<String>): Int {

    val vals = listOf<Char>('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    fun getValHand(hand: Map<Char, Int>): Int {


        val sortedBy = hand.entries.sortedByDescending { it.value }.toMutableList()


//251014372 not right


        if (sortedBy[0].value == 5) {
            return 7
        }

        if (sortedBy[0].value == 4) {
            return 6
        }

        if (sortedBy[0].value == 3) { // full
            if (sortedBy[1].value == 2) {
                return 5

            } else {  // just 3
                return 4
            }
        }

        if (sortedBy[0].value == 2) {
            if (sortedBy[1].value == 2) { //2 pairs
                return 3
            } else { // pair
                return 2
            }
        }
        return 1 // single

    }


    input.forEach { it.log("p1") }


    val mComparator = Comparator<Pair<Pair<Map<Char, Int>, String>, Int>> { o1, o2 ->
        val f = getValHand(o1.first.first)
        val s = getValHand(o2.first.first)


        "compare ${o1.first.second} with ${o2.first.second}".log()
        when {
            f > s -> 1
            f < s -> -1
            else -> {
                var res = 0
                for (i in 0..4) {

                    o1.first.second[i].log("c1")
                    o2.first.second[i].log("c2")
                    val id1 = vals.indexOf(o1.first.second[i])
                    val id2 = vals.indexOf(o2.first.second[i])
                    if (id1 < id2) {
                        res = 1
                        break
                    }
                    if (id2 < id1) {
                        res = -1
                        break
                    }
                }
                "equals?".log()
                res
            }
        }
    }

    val maps = input.map { line ->

        val split = line.split(" ")
        val hand = split[0].groupingBy { it }.eachCount()
        val bid = split[1].toInt()
        (hand to split[0]) to bid
    }

    val sortedWith = maps.sortedWith(mComparator)

    var res = 0
    var ctr = 1
    sortedWith.forEach {
        val i = it.second * ctr
        ctr++
        it.first.log("hand check")
        res += i
    }
    res.log("result ")
    return res
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



