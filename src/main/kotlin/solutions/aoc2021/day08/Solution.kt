package solutions.aoc2021.day08

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    return inputLines.sumOf {
        val (_, r) = it.split(" | ")
        r.split(" ").count { dig ->
            dig.length in listOf(2, 4, 3, 7)
        }
    }
}

fun part2(inputLines: List<String>): Any {

    fun find(vals: List<String>): MutableList<Set<Char>> {

        val one = vals.first { it.length == 2 }.toSet()
        val four = vals.first { it.length == 4 }.toSet()
        val seven = vals.first { it.length == 3 }.toSet()
        val eight = vals.first { it.length == 7 }.toSet()

        val allSix = vals.filter { it.length == 6 }
        val zeroNineOrSix = allSix.filter { x -> eight.toSet().minus(x.toSet()).size == 1 }

        val sixProb = zeroNineOrSix.filter { x -> seven.minus(x.toSet()).size == 1 }.toSet()
        val six = sixProb.first()

        val zeroOrNine = zeroNineOrSix.minus(six)
        val nine = zeroOrNine.filter { x -> four.minus(x.toSet()).isEmpty() }.first()

        val probZero = zeroOrNine.minus(nine)
        val zero = probZero.first().toSet()

        val middleHorizontalLine = eight.toSet().minus(zero.toSet()).first()
        val sevenPlusMiddle = seven + middleHorizontalLine
        val allFive = vals.filter { it.length == 5 }
        val three = allFive.first { x -> x.toSet().minus(sevenPlusMiddle.toSet()).size == 1 }.toSet()

        val five = allFive.first { x -> six.toSet().minus(x.toSet()).size == 1 }.toSet()

        val two = allFive.first { x -> x.toSet() != five.toSet() && x.toSet() != three }.toSet()

        return mutableListOf(zero, one, two, three, four, five, six.toSet(), seven, eight, nine.toSet())
    }


    return inputLines.sumOf {
        val (l, r) = it.split(" | ")
        val left = l.split(" ")
        val right = r.split(" ")

        var answer = ""
        val listOfVals = find(left)
        right.forEach { num ->
            for ((index, chars) in listOfVals.withIndex()) {
                if (num.toSet() == chars) {
                    answer += index
                }
            }
        }
        answer.toInt()
    }
}

