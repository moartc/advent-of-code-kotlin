package solutions.aoc2023.day05

import utils.Resources
import utils.splitOn
import java.math.BigInteger
import kotlin.math.log


fun main() {

    val inputLine =
        Resources.getLines(2023, 5)
    println("part1 = ${part1(inputLine)}")
    println("part1 = ${part2(inputLine)}")

}

fun part1(input: List<String>): Long {

    input.forEach { it.log("p1") }


    val splitOn = input.splitOn { it.isEmpty() }

    val seeds = splitOn[0][0].getLongs()

    var seedToSoil = splitOn[1].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var soilToFertilizer = splitOn[2].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var fertilizerToWater = splitOn[3].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var waterTtoLight = splitOn[4].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var lightToTemperature = splitOn[5].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var temperatureToHumidity = splitOn[6].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var humidityToLocation = splitOn[7].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }


    val minBy = seeds.minOf { seed ->

        val soil = foo(seedToSoil, seed)
        val fer = foo(soilToFertilizer, soil)
        val water = foo(fertilizerToWater, fer)
        val light = foo(waterTtoLight, water)
        val temp = foo(lightToTemperature, light)
        val hum = foo(temperatureToHumidity, temp)
        val loc = foo(humidityToLocation, hum)

        loc


    }
    return minBy
}

fun part2(input: List<String>): Long {


    val splitOn = input.splitOn { it.isEmpty() }

    val seeds = splitOn[0][0].getLongs()

    var seedToSoil = splitOn[1].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var soilToFertilizer = splitOn[2].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var fertilizerToWater = splitOn[3].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var waterTtoLight = splitOn[4].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }
    //seed, soil, fertilizer

    var lightToTemperature = splitOn[5].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var temperatureToHumidity = splitOn[6].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var humidityToLocation = splitOn[7].filterIndexed { index, s ->
        index != 0
    }.map { it.getLongs() }

    var totMin = Long.MAX_VALUE
    val windowed = seeds.windowed(2, 2).map { it[0]..it[0] + it[1] - 1 }


    windowed.forEach { it ->

        var minInWin = Long.MAX_VALUE
        (it.first..it.last).forEach() { seed ->
//            seed.log("seed")

//            if(map.containsKey(seed)) {
//                map[seed]
//            }
            val soil = foo(seedToSoil, seed)
            val fer = foo(soilToFertilizer, soil)
            val water = foo(fertilizerToWater, fer)
            val light = foo(waterTtoLight, water)
            val temp = foo(lightToTemperature, light)
            val hum = foo(temperatureToHumidity, temp)
            val loc = foo(humidityToLocation, hum)
//            map[seed] = loc
            if (loc < minInWin) {
                minInWin = loc
                println("new minInWin = $minInWin")
            }
        }

        if (minInWin < totMin) {
            totMin = minInWin
            println("new totMin = $totMin")
        }
    }

    return totMin
}

fun foo(map: List<List<Long>>, value: Long): Long {
    var fertilizerRange = map.firstOrNull() { soil -> value in soil[1]..soil[1] + (soil[2]) - 1 }
    if (fertilizerRange == null) {
        return value
    }
    var diff2 = value - fertilizerRange[1]
    return fertilizerRange[0] + diff2
}


//20191103 wrong
//20191103


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

fun String.getLongs(): List<Long> = """-?\d+""".toRegex().findAll(this).map(MatchResult::value).map(String::toLong).toList()

