package solutions.aoc2017.day16

import utils.Resources
import java.util.*

fun main() {
    val input = Resources.getLine(2017, 16)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: String): String {
    val commands = input.split(",").toList()
    val characters = (0..15).map { ('a' + it) }.toMutableList()
    return solve(commands, characters)
}


fun part2(input: String): String {
    val commands = input.split(",").toList()
    val characters = (0..15).map { ('a' + it) }.toMutableList()
    // the values are repeated with offset 30, 1000000000%30 == 10,
    // so the last value will be the same as for repetitions
    repeat(10) {
        solve(commands, characters)
    }
    return characters.joinToString("")
}

fun solve(commands: List<String>, characters: MutableList<Char>): String {
    for (command in commands) {
        if (command.startsWith('s')) {
            Collections.rotate(characters, command.substring(1).toInt())
        } else if (command.startsWith('x')) {
            val split = command.substring(1).split("/")
            val first = split[0].toInt()
            val snd = split[1].toInt()
            val toMove1 = characters[first]
            characters[first] = characters[snd]
            characters[snd] = toMove1
        } else if (command.startsWith('p')) {
            val split = command.substring(1).split("/")
            val first = split[0][0]
            val snd = split[1][0]
            val find1 = characters.indexOf(first)
            val find2 = characters.indexOf(snd)
            characters[find1] = snd
            characters[find2] = first
        }
    }
    return characters.joinToString("")
}

