package solutions.aoc2023.day04

import utils.Resources
import utils.getInt
import utils.getInts

fun main() {

    val inputLine =
        Resources.getLines(2023, 4)
//        Resources.getLinesExample(2023, 4)
//    println("part1 = " + part1(inputLine))
    println("part2 = " + part2(inputLine))
}

fun part1(input: List<String>): Int {

    var winning = mutableListOf<Int>()
    var myList = mutableListOf<Int>()

    var lines = mutableListOf<Line>()

    var totRes = 0
    input.forEach {
        val split1 = it.split(":")
        var cardNUm = split1[0]
        var rest = split1[1]
        val split2 = rest.split("|")
        winning = split2[0].getInts().toMutableList()
        myList = split2[1].getInts().toMutableList()

        var cardRes = 0
        var matched = false
        myList.forEachIndexed{index, cardVal ->
            if(winning.contains(cardVal)) {
                cardVal.log("contain")
                if(!matched) {
                    cardRes= 1
                    matched = true
                } else {
                    cardRes *= 2
                }
            }
        }
        cardRes.log("card res for $cardNUm")

        totRes += cardRes

        val line = Line(cardNUm.getInt()!!, winning, myList)
        lines.add(line)
    }

    totRes.log("answer")




    return 12
}

data class Line(val copies: Int, val winning: MutableList<Int>, val myLis: MutableList<Int>)

fun part2(input: List<String>): Int {

    var winning = mutableListOf<Int>()
    var myList = mutableListOf<Int>()

    var lines = mutableListOf<Line>()

    var totRes = 0

    input.forEach {
        val split1 = it.split(":")
        var cardNUm = split1[0]
        var rest = split1[1]
        val split2 = rest.split("|")
        winning = split2[0].getInts().toMutableList()
        myList = split2[1].getInts().toMutableList()

        var line = Line(1, winning, myList)
        lines.add(line)
    }

    lines.forEachIndexed { index, line ->

        index.log("idx")
        var cardRes = 0
        var matched = false
        val winningList = line.winning
        val myList = line.myLis
        val multip = line.copies
        var wins = 0
        myList.forEachIndexed{index, cardVal ->
            if(winningList.contains(cardVal)) {
                wins++
            }
        }
        if(wins > 0) {

            for(toAdd in 1 .. wins) {
                if(index +toAdd < lines.size){
                    val line1 = lines[index + toAdd]
                    val copy = line1.copy(copies = line1.copies + line.copies)
                    lines[index+toAdd] = copy

                }
            }
        }
    }

    /*



        lines.add(line)
     */

    lines.sumOf { it.copies }.log("res")




    return 12
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



