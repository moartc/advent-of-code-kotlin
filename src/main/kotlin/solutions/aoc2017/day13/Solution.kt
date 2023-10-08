package solutions.aoc2017.day13

import utils.Resources

fun main() {
    val input = Resources.getLines(2017, 13)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    var firewalls = parseInput(input)
    var result = 0
    for (i in 0..firewalls.last().index) {
        if (isConflict(firewalls, i)) {
            result += i * firewalls.first { firewall -> firewall.index == i }.depth
        }
        firewalls = firewalls.map { f -> updateFirewall(f) }
    }
    return result
}

fun part2(input: List<String>): Int {
    val firewallsOriginal = parseInput(input)

    var firewallsWait = firewallsOriginal
    nextTime@ for (waitTime in 0..Integer.MAX_VALUE) {

        var firewalls = firewallsWait.toList()
        firewallsWait = firewallsWait.map { f -> updateFirewall(f) }

        for (i in 0..firewalls.last().index) {
            if (isConflict(firewalls, i)) {
                continue@nextTime
            }
            firewalls = firewalls.map { f -> updateFirewall(f) }
        }
        return waitTime
    }
    return -1
}

fun isConflict(firewalls: List<Firewall>, currentPos: Int): Boolean {
    return firewalls.any { fw -> fw.index == currentPos && fw.currentPos == 0 }
}

fun parseInput(input: List<String>): List<Firewall> {
    return input.map {
        val split = it.split(": ")
        val first = split[0].toInt()
        val second = split[1].toInt()
        Firewall(first, second, 0, true)
    }
}

fun updateFirewall(firewall: Firewall): Firewall {

    val newPos: Int
    var newDirection = firewall.goDown
    if (firewall.goDown) {
        if (firewall.currentPos < firewall.depth - 1) {
            newPos = firewall.currentPos + 1
        } else { // is the last position
            newDirection = false // should go up
            newPos = firewall.currentPos - 1
        }
    } else { // it's going up
        if (firewall.currentPos > 0) {
            newPos = firewall.currentPos - 1
        } else { // it's on top
            newPos = 1 // one below
            newDirection = true // change direction to go down
        }
    }
    return Firewall(firewall.index, firewall.depth, newPos, newDirection)
}

data class Firewall(var index: Int, var depth: Int, var currentPos: Int, var goDown: Boolean)
