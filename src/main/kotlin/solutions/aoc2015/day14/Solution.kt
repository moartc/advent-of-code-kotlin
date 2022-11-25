package solutions.aoc2015.day14

import utils.Resources

fun main() {

    val inputLines = Resources.getLines(2015, 14)
    val reindeerList = parseReindeerList(inputLines)
    println("part1 = ${part1(reindeerList, 2503)}")
    println("part2 = ${part2(reindeerList, 2503)}")
}

fun part1(reindeerList: List<Reindeer>, duration: Int): Int {
    val distances = reindeerList.map { r -> countDistanceAfter(r, duration) }
    return distances.maxOrNull()!!
}

fun part2(reindeerList: List<Reindeer>, duration: Int): Int {
    return (1..duration).map { second ->
        reindeerList.groupBy { r -> countDistanceAfter(r, second) }.maxByOrNull { it.key }!!.value
    }.flatten().groupingBy { it }.eachCount().maxByOrNull { it.value }!!.value
}

fun countDistanceAfter(reindeer: Reindeer, seconds: Int): Int {
    val moveAndRestTime = reindeer.flyTime + reindeer.restTime
    val numberOfFullSeries = seconds / moveAndRestTime
    val secondsToCount = seconds - numberOfFullSeries * moveAndRestTime
    val moveDistance = reindeer.speed * reindeer.flyTime
    return if (secondsToCount > reindeer.flyTime) {
        (numberOfFullSeries + 1) * moveDistance
    } else {
        (numberOfFullSeries * moveDistance) + secondsToCount * reindeer.speed
    }
}

fun parseReindeerList(input: List<String>) =
    input.map { line -> """.+ (\d+) .+ (\d+) .* (\d+) .*""".toRegex().matchEntire(line) }
        .map { matchResult -> matchResult!!.groups }
        .map { group -> Reindeer(group) }

class Reindeer(val speed: Int, val flyTime: Int, val restTime: Int) {
    constructor(mgc: MatchGroupCollection) : this(
        mgc[1]!!.value.toInt(), mgc[2]!!.value.toInt(), mgc[3]!!.value.toInt()
    )
}