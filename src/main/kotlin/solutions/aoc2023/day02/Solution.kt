package solutions.aoc2023.day02

import utils.Resources
import java.lang.Integer.max

fun main() {

    val inputLines = Resources.getLines(2023, 2)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {

    val red = 12
    val green = 13
    val blue = 14

    fun valColorPossible(valColors: String): Boolean {
        for (valColor in valColors.trim().split(", ")) {
            val value = valColor.split(" ")[0].toInt()
            val color = valColor.split(" ")[1]
            when {
                color == "red" && value > red -> return false
                color == "blue" && value > blue -> return false
                color == "green" && value > green -> return false
            }
        }
        return true
    }

    var possibleGameIds = 0
    for (game in inputLines) {
        val indexOf = game.indexOf(":")
        val cubesPart = game.substring(indexOf + 1)
        val gameId = game.substring(5, indexOf).toInt()
        val valColors = cubesPart.replace(";", ",")
        if (valColorPossible(valColors)) {
            possibleGameIds += gameId
        }
    }
    return possibleGameIds
}

fun part2(inputLines: List<String>): Int {

    return inputLines.sumOf { game ->
        val indexOf = game.indexOf(":")
        val cubesPart = game.substring(indexOf + 1)
        val sets = cubesPart.split(";")
        var maxGreen = 0
        var maxBlue = 0
        var maxRed = 0
        sets.forEach { singleSet ->
            val valColors = singleSet.trim().split(", ")
            valColors.forEach { valColor ->
                if (valColor.contains("red")) {
                    val rV = valColor.split(" ")[0].toInt()
                    maxRed = max(rV, maxRed)
                } else if (valColor.contains("blue")) {
                    val bV = valColor.split(" ")[0].toInt()
                    maxBlue = max(bV, maxBlue)
                } else if (valColor.contains("green")) {
                    val gV = valColor.split(" ")[0].toInt()
                    maxGreen = max(gV, maxGreen)
                }
            }
        }
        maxGreen * maxBlue * maxRed
    }
}