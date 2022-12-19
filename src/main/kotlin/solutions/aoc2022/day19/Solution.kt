package solutions.aoc2022.day19

import utils.Resources
import utils.getInts

fun main() {
    val input = Resources.getLines(2022, 19)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

var cacheMap = mutableMapOf<DataToCache, Short>()
var bestFound = 0
fun part1(input: List<String>): Int {

    val blueprints = input.map { it.getInts() }.map { l -> Blueprint(l[0], l[1], l[2], l[3] to l[4], l[5] to l[6]) }
    return blueprints.sumOf {
        cacheMap = mutableMapOf()
        bestFound = 0
        val maxOreToSpend = listOf(it.oreCost, it.clayCost, it.obsidianCost.first, it.geodeCost.first).max()
        val clayToSpend = it.obsidianCost.second
        val obsidianToSpend = it.geodeCost.second
        nextRound(State(0, 0, 0, 0, 1), Robots(1, 0, 0, 0), it, maxOreToSpend, clayToSpend, obsidianToSpend, 25)
        bestFound * it.idx
    }
}

fun part2(input: List<String>): Int {

    val blueprints = input.map { it.getInts() }.map { l -> Blueprint(l[0], l[1], l[2], l[3] to l[4], l[5] to l[6]) }
    val sublist = blueprints.subList(0, 3)
    return sublist.map {
        cacheMap = mutableMapOf()
        bestFound = 0
        val maxOreToSpend = listOf(it.oreCost, it.clayCost, it.obsidianCost.first, it.geodeCost.first).max()
        val clayToSpend = it.obsidianCost.second
        val obsidianToSpend = it.geodeCost.second
        nextRound(State(0, 0, 0, 0, 1), Robots(1, 0, 0, 0), it, maxOreToSpend, clayToSpend, obsidianToSpend, 33)
        bestFound
    }.reduce { acc, i -> acc * i }
}

// everything except time because I accept the same state with less time
data class DataToCache(
    var ore: Short,
    var clay: Short,
    var obsidian: Short,
    var geode: Short,
    var oreR: Short,
    var clayR: Short,
    var obsidianR: Short,
    var geodeR: Short
)
fun bestPossibleResult(currentOre: Int, geodeRobot: Int, roundsToEnd: Int): Int {
    val sumRob = (0..roundsToEnd).sumOf { geodeRobot + it }
    return currentOre + sumRob
}

fun nextRound(state: State, robots: Robots, blueprint: Blueprint, maxOreToSpend: Int, clayToSpend: Int, obsidianToSpend: Int, max: Int): Int {

    if (state.time == max) {
        if (state.geode > bestFound) {
            bestFound = state.geode
        }
        return bestFound
    }

    if (bestPossibleResult(robots.geode, state.geode, max - state.time) < bestFound) {
        return bestFound
    }

    val cache = DataToCache(
        minOf(100, state.ore.toShort()), // random 100 to limit the amount of data, I don't even know if it works
        minOf(100, state.clay.toShort()),
        minOf(100, state.obsidian.toShort()),
        state.geode.toShort(),
        robots.ore.toShort(),
        robots.clay.toShort(),
        robots.obs.toShort(),
        robots.geode.toShort()
    )
    if (cacheMap.contains(cache)) {
        if (state.time >= cacheMap[cache]!!) {
            return bestFound
        } else {
            cacheMap[cache] = state.time.toShort()
        }
    } else {
        cacheMap[cache] = state.time.toShort()
    }
    val newOre = robots.ore
    val newObs = robots.obs
    val newClay = robots.clay
    val newGeode = robots.geode
    if (state.canBuyGeode(blueprint)) {
        val s1 = state.update(newOre, newClay, newObs, newGeode)
        val newRob = robots.copy(geode = robots.geode + 1)
        s1.ore -= blueprint.geodeCost.first
        s1.obsidian -= blueprint.geodeCost.second
        nextRound(s1, newRob, blueprint, maxOreToSpend, clayToSpend, obsidianToSpend, max)
    }
    if (state.canBuyObsidian(blueprint) && robots.obs < obsidianToSpend) {
        val s1 = state.update(newOre, newClay, newObs, newGeode)
        val newRob = robots.copy(obs = robots.obs + 1)
        s1.ore -= blueprint.obsidianCost.first
        s1.clay -= blueprint.obsidianCost.second
        nextRound(s1, newRob, blueprint, maxOreToSpend, clayToSpend, obsidianToSpend, max)
    }
    if (state.canBuyClay(blueprint) && robots.clay < clayToSpend) {
        val s1 = state.update(newOre, newClay, newObs, newGeode)
        val newRob = robots.copy(clay = robots.clay + 1)
        s1.ore -= blueprint.clayCost
        nextRound(s1, newRob, blueprint, maxOreToSpend, clayToSpend, obsidianToSpend, max)
    }
    if (state.canBuyOre(blueprint) && robots.ore < maxOreToSpend) {
        val s1 = state.update(newOre, newClay, newObs, newGeode)
        val newRob = robots.copy(ore = robots.ore + 1)
        s1.ore -= blueprint.oreCost
        nextRound(s1, newRob, blueprint, maxOreToSpend, clayToSpend, obsidianToSpend, max)
    }
    // don't buy
    val stateNext = state.update(newOre, newClay, newObs, newGeode)
    return nextRound(stateNext, robots, blueprint, maxOreToSpend, clayToSpend, obsidianToSpend, max)
}

data class Robots(var ore: Int, var clay: Int, var obs: Int, var geode: Int)

data class State(var ore: Int, var clay: Int, var obsidian: Int, var geode: Int, var time: Int) {

    fun canBuyGeode(blueprint: Blueprint) = ore >= blueprint.geodeCost.first && obsidian >= blueprint.geodeCost.second
    fun canBuyObsidian(blueprint: Blueprint) = ore >= blueprint.obsidianCost.first && clay >= blueprint.obsidianCost.second
    fun canBuyClay(blueprint: Blueprint) = ore >= blueprint.clayCost
    fun canBuyOre(blueprint: Blueprint) = ore >= blueprint.oreCost

    fun update(newOre: Int, newClay: Int, newObs: Int, newGeode: Int): State {
        return State(ore + newOre, clay + newClay, obsidian + newObs, geode + newGeode, time + 1)
    }
}

data class Blueprint(val idx: Int, val oreCost: Int, val clayCost: Int, val obsidianCost: Pair<Int, Int>, val geodeCost: Pair<Int, Int>)