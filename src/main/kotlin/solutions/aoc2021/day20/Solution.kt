package solutions.aoc2021.day20

import utils.Resources
import utils.collections.extensions.splitOn
import kotlin.math.max
import kotlin.math.min

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    return parseAndCalc(inputLines, 2)
}

fun part2(inputLines: List<String>): Any {
    return parseAndCalc(inputLines, 50)
}

fun parseAndCalc(inputLines: List<String>, numOfEnhancement: Int): Int {
    val (enh, img) = inputLines.splitOn { x -> x.isEmpty() }
    val fulLEnh = enh.joinToString("")

    val grid = img.map { l -> l.toCharArray() }
    var gr = mutableMapOf<Pair<Int, Int>, Char>()
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var minY = Int.MAX_VALUE
    var maxY = Int.MIN_VALUE
    for ((y, chars) in grid.withIndex()) {
        minY = min(minY, y)
        maxY = max(maxY, y)
        for ((x, ch) in chars.withIndex()) {
            minX = min(minX, x)
            maxX = max(maxX, x)
            gr[(y to x)] = ch
        }
    }

    var charOutside = 0
    repeat(numOfEnhancement) {
        val newMap = mutableMapOf<Pair<Int, Int>, Char>()
        for (y in minY - 1..maxY + 1) {
            for (x in minX - 1..maxX + 1) {
                val sb = StringBuilder()
                for (ny in y - 1..y + 1) {
                    for (nx in x - 1..x + 1) {
                        val k = ny to nx
                        if (!gr.contains(k)) {
                            sb.append(charOutside)
                        } else {
                            val c = gr.get(k)!!
                            if (c == '.') {
                                sb.append(0)
                            } else {
                                sb.append(1)
                            }
                        }
                    }
                }
                val int = sb.toString().toInt(2)
                newMap[y to x] = fulLEnh[int]
            }
        }
        gr = newMap
        minX -= 1
        minY -= 1
        maxX += 1
        maxY += 1
        charOutside = if (charOutside == 0) 1 else 0
    }
    return gr.values.count { x -> x == '#' }
}