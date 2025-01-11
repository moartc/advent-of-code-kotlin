package solutions.aoc2016.day05

import solutions.aoc2015.day04.md5Hash
import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): String {

    val input = inputLines[0]
    var id = 0
    var ctr = 0
    var passwd = ""
    while (true) {
        val toCheck = input + id
        val md5 = md5Hash(toCheck)
        if (md5.startsWith("00000")) {
            passwd += md5[5]
            ctr++
            if (ctr == 8) {
                return passwd
            }
        }
        id++
    }
}

fun part2(inputLines: List<String>): String {

    val input = inputLines[0]
    var id = 0L
    var ctr = 0
    val passwd = Array(8) { ' ' }
    while (true) {
        val toCheck = input + id
        val md5 = md5Hash(toCheck)
        if (md5.startsWith("00000")) {
            val six = md5[5]
            if (six.isDigit() && six.digitToInt() in 0..7) {
                val pos = six.digitToInt()
                val char = md5[6]
                if (passwd[pos] == ' ') {
                    passwd[pos] = char
                    ctr++
                    if (ctr == 8) {
                        return passwd.joinToString("")
                    }
                }
            }
        }
        id++
    }
}
