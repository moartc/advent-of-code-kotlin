package solutions.aoc2023.day15

import utils.Resources

fun main() {

    val inputLine = Resources.getLines(2023, 15)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part1(input: List<String>): Int {

    val split = input[0].split(",")
    return split.sumOf { hash(it) }
}

fun part2(input: List<String>): Int {

    val split = input[0].split(",")
    val boxes = Array(256) { x -> mutableListOf<String>() }

    for ( s in split) {
        if (s.contains("=")) {
            val split = s.split("=")
            val label = split[0]
            val value = split[1].toInt()
            val box = hash(label)
            val newToSet = "$label $value"
            if (boxes[box].any { x -> x.startsWith(label) }) { // replace
                val indexOfFirst = boxes[box].indexOfFirst { x -> x.startsWith(label) }
                boxes[box][indexOfFirst] = newToSet
            } else {
                boxes[box].add(newToSet)
            }
        } else { // cont -
            val split = s.split("-")
            val label = split[0]
            val box = hash(label)
            boxes[box].removeIf { x -> x.startsWith(label) }
        }

    }
    var sum = 0
    for ((boxNr, strings) in boxes.withIndex()) {
        for ((slotNr, s1) in strings.withIndex()) {
            var result = (boxNr + 1)
            result *= (slotNr + 1)
            result *= s1.split(" ")[1].toInt()
            sum += result
        }
    }
    return sum
}

fun hash(str: String): Int {
    var hashValue = 0
    for (c in str) {
        hashValue += c.code
        hashValue *= 17
        hashValue %= 256
    }
    return hashValue
}




