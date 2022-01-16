package solutions.day01

import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

private object Resources {
    var url: URL = Resources.javaClass.getResource("/solutions/day01.txt")
    fun getLines(): MutableList<String> = Files.readAllLines(Paths.get(url.path))
}

fun main() {

    val inputLine = Resources.getLines()[0]
    println("solutions.day01.part1 = " + part1(inputLine))
    println("solutions.day01.part2 = " + part2(inputLine))
}

fun part1(input: String): Int {
    return input.fold(0) { acc, next -> if (next == '(') acc + 1 else acc - 1 }
}


fun part2(input: String): Int {
    return input.runningFold(0) { acc, next -> if (next == '(') acc + 1 else acc - 1 }
        .indexOfFirst { it == -1 }
}