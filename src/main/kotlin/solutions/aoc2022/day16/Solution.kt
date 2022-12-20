package solutions.aoc2022.day16

import utils.Resources

fun main() {
    val input = Resources.getLines(2022, 16)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

data class Valve(val name: String, val value: Int, val list: List<String>)


var part1Answer = 0
fun part1(input: List<String>): Int {
    val valves = input.map {
        val groups = "Valve ([A-Z]+) has flow rate=(\\d+); .* valves? (.*)".toRegex().matchEntire(it)!!.groups
        val valveName = groups[1]!!.value
        val value = groups[2]!!.value.toInt()
        val connections = "Valve ([A-Z]+) has flow rate=(\\d+); .* valves? (.*)".toRegex().matchEntire(it)!!.groups[3]!!.value.split(", ")
        Valve(valveName, value, connections)
    }.toSet()
    val start = valves.first { it.name == "AA" }

    traverse(start, 1, 0, valves, mutableListOf(), valves.filter { it.value > 0 }.map { it.name }.toSet(), 30)

    return part1Answer
}

var part2Answer = 0
fun part2(input: List<String>): Int {

    val valves = input.map {
        val groups = "Valve ([A-Z]+) has flow rate=(\\d+); .* valve[s]? (.*)".toRegex().matchEntire(it)!!.groups
        val valveName = groups[1]!!.value
        val value = groups[2]!!.value.toInt()
        val connections = "Valve ([A-Z]+) has flow rate=(\\d+); .* valve[s]? (.*)".toRegex().matchEntire(it)!!.groups[3]!!.value.split(", ")
        Valve(valveName, value, connections)
    }.toSet()

    val start = valves.first { it.name == "AA" }
    traverse2(start, start, 1, 0, valves, mutableListOf(), valves.filter { it.value > 0 }.map { it.name }.toSet(), 26)
    return part2Answer
}

fun restToUnlock(stillOpen: Set<String>, valves: Set<Valve>): Int {
    return valves.filter { stillOpen.contains(it.name) }.sumOf { it.value }
}

fun traverse(valve: Valve, time: Int, points: Int, valves: Set<Valve>, listTimePoints: MutableList<Int>, stillToOpen: Set<String>, limit: Int) {
    listTimePoints.add(points)
    if (part1Answer > listTimePoints.sum() + (limit - time) * (listTimePoints.last() + restToUnlock(stillToOpen, valves)) - stillToOpen.size) {
        return
    }
    val state = State(valve.name, listTimePoints, stillToOpen)
    if (map.contains(state)) {
        return
    } else {
        map.add(state)
    }
    if (stillToOpen.isEmpty()) {
        val rest = (limit - time) * listTimePoints.last()
        val newSum = listTimePoints.sum() + rest
        if (newSum > part1Answer) {
            part1Answer = newSum
        }
        return
    }
    if (time == limit) {
        val newSum = listTimePoints.sum()
        if (newSum > part1Answer) {
            part1Answer = newSum
        }
        return
    }
    if (stillToOpen.contains(valve.name) && valve.value > 0) {
        val newStillToOpen = stillToOpen.toMutableSet()
        newStillToOpen.remove(valve.name)
        traverse(valve, time + 1, points + valve.value, valves, listTimePoints.toMutableList(), newStillToOpen, limit)

    }
    for (name in valve.list.sortedWith(compareBy({ stillToOpen.contains(it) }, { valves.first { v -> v.name == it }.value })).reversed()) {
        traverse(valves.first { it.name == name }, time + 1, points, valves, listTimePoints.toMutableList(), stillToOpen.toMutableSet(), limit)
    }
}

fun traverse2(
    valve1: Valve,
    valve2: Valve,
    time: Int,
    points: Int,
    valves: Set<Valve>,
    listTimePoints: MutableList<Int>,
    stillToOpen: Set<String>,
    limit: Int
) {

    listTimePoints.add(points)
    if (part2Answer > listTimePoints.sum() + ((limit - time) * (listTimePoints.last() + restToUnlock(stillToOpen, valves))) - stillToOpen.size) {
        return
    }
    val state = State2(setOf(valve1.name, valve2.name), time, stillToOpen)
    if (map2.contains(state)) {
        return
    } else {
        map2.add(state)
    }
    if (stillToOpen.isEmpty()) {
        val rest = (limit - time) * listTimePoints.last()
        val newSum = listTimePoints.sum() + rest
        if (newSum > part2Answer) {
            part2Answer = newSum
        }
        return
    }
    if (time == limit) {
        val newSum = listTimePoints.sum()
        if (newSum > part2Answer) {
            part2Answer = newSum
        }
        return
    }
    if (stillToOpen.contains(valve1.name) && valve1.value > 0 && stillToOpen.contains(valve2.name) && valve2.value > 0) { // both open
        val newStillToOpen = stillToOpen.toMutableSet()
        var newPoints = points

        if (newStillToOpen.remove(valve1.name)) {
            newPoints += valve1.value
        }
        if (newStillToOpen.remove(valve2.name)) {
            newPoints += valve2.value
        }
        traverse2(valve1, valve2, time + 1, newPoints, valves, listTimePoints.toMutableList(), newStillToOpen, limit)
    }
    if (stillToOpen.contains(valve1.name) && valve1.value > 0) { // s1 open
        val newStillToOpen = stillToOpen.toMutableSet()
        newStillToOpen.remove(valve1.name)

        for (next in valve2.list.sortedWith(compareBy({ stillToOpen.contains(it) }, { valves.first { v -> v.name == it }.value })).reversed()) {
            traverse2(
                valve1,
                valves.first { it.name == next },
                time + 1,
                points + valve1.value,
                valves,
                listTimePoints.toMutableList(),
                newStillToOpen.toMutableSet(),
                limit
            )
        }
    }
    if (stillToOpen.contains(valve2.name) && valve2.value > 0) { // s2 open
        val newStillToOpen = stillToOpen.toMutableSet()
        newStillToOpen.remove(valve2.name)

        for (next in valve1.list.sortedWith(compareBy({ stillToOpen.contains(it) }, { valves.first { v -> v.name == it }.value })).reversed()) {
            traverse2(
                valves.first { it.name == next },
                valve2,
                time + 1,
                points + valve2.value,
                valves,
                listTimePoints.toMutableList(),
                newStillToOpen.toMutableSet(),
                limit
            )
        }
    }
    for (next1 in valve1.list.sortedWith(compareBy({ stillToOpen.contains(it) }, { valves.first { v -> v.name == it }.value })).reversed()) {
        for (next2 in valve2.list.sortedWith(compareBy({ stillToOpen.contains(it) }, { valves.first { v -> v.name == it }.value })).reversed()) {
            if (next1 != next2) {
                traverse2(
                    valves.first { it.name == next1 },
                    valves.first { it.name == next2 },
                    time + 1,
                    points,
                    valves,
                    listTimePoints.toMutableList(),
                    stillToOpen.toMutableSet(),
                    limit
                )
            }
        }
    }
}

data class State(val valveName: String, val time: List<Int>, val stillToOpen: Set<String>)
data class State2(val valveNames: Set<String>, val time: Int, val stillToOpen: Set<String>)

var map2 = mutableSetOf<State2>()
var map = mutableSetOf<State>()
