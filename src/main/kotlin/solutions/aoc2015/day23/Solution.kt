package solutions.aoc2015.day23

import utils.Resources

fun main() {
    val input = Resources.getLines(2015, 23)
    println("part1 = " + process(mapOf('a' to 0, 'b' to 0), 0, input)['b'])
    println("part2 = " + process(mapOf('a' to 1, 'b' to 0), 0, input)['b'])
}

fun process(reg: Map<Char, Int>, idx: Int, input: List<String>): Map<Char, Int> {
    if (idx == input.size) return reg
    val line = input[idx]
    val r = line[4]
    return when (line.substring(0, 3)) {
        "hlf" -> process(map(reg, r) { value -> value / 2 }, idx + 1, input)
        "tpl" -> process(map(reg, r) { value -> value * 3 }, idx + 1, input)
        "inc" -> process(map(reg, r, Int::inc), idx + 1, input)
        "jmp" -> process(reg, idx + Integer.parseInt(line.substring(4)), input)
        "jie" -> process(reg, idx + if (reg[r]!! % 2 == 0) Integer.parseInt(line.substring(7)) else 1, input)
        "jio" -> process(reg, idx + if (reg[r]!! == 1) Integer.parseInt(line.substring(7)) else 1, input)
        else -> reg
    }
}

fun map(reg: Map<Char, Int>, r: Char, op: (Int) -> Int) = reg.mapValues { if (it.key == r) op(it.value) else it.value }
