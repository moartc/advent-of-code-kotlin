package solutions.aoc2016.day23

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    return solve(inputLines, 7)
}

fun part2(inputLines: List<String>): Int {
    return solve(inputLines, 12)
}

fun solve(inputLines: List<String>, initValForA: Int): Int {
    val splitParam = inputLines.map { it.split(" ") }
    val registers = mutableMapOf<Char, Int>()
    splitParam.forEach {
        if (it[1].toIntOrNull() == null) {
            registers[(it[1][0])] = 0
        }
        if (it.size > 2 && it[2].toIntOrNull() == null) {
            registers[(it[2][0])] = 0
        }
    }
    registers['a'] = initValForA
    val split = splitParam.toMutableList()
    var index = 0
    while (index <= split.lastIndex) {
        val input = split[index]
        val instr = input[0]
        val inpX = input[1]

        /*
        commented out because this 'improvement' is now included in the next improvement below
        if(index == 5) {
            // when I reach instr 5 and 6 they repeat
            // inc a
            // dec c
            // until c != 0, so I can already add value of 'c' to 'a', set 'c' to 0 and skip 2 indices
            registers['a'] = registers['a']!! + registers['c']!!
            registers['c'] = 0
            index = 7
            }
         *//*
            then 6, 9 and 10 repeat
            until d != 0
            copy b to c
            increase a by c - included below
            decrease d
        */
        if (index == 8) {

            repeat(registers['d']!! - 1) {
                //cpy b c
                registers['c'] = registers['b']!!
                registers['a'] = registers['a']!! + registers['c']!!    // part from the first improvement
                registers['c'] = 0
            }
            registers['d'] = 0
            index = 9
        }


        if (instr == "cpy") {
            val inpY = input[2]
            if (inpX.toIntOrNull() != null) {
                if (inpY.toIntOrNull() != null) {
                    index++
                    continue
                } else {
                    registers[inpY.first()] = inpX.toIntOrNull()!!
                }
            } else {
                val valToCpy = registers[inpX.first()]!!
                registers[inpY.first()] = valToCpy
            }
        } else if (instr == "inc") {
            registers[inpX.first()] = registers[inpX.first()]!! + 1

        } else if (instr == "dec") {
            registers[inpX.first()] = registers[inpX.first()]!! - 1
        } else if (instr == "jnz") {
            if (inpX.toIntOrNull() != null) {
                if (inpX.toIntOrNull()!! != 0) {
                    if (input[2].toIntOrNull() != null) {
                        index += input[2].toInt()
                    } else {
                        val toJump = registers[input[2].first()]!!
                        index += toJump
                    }
                    continue
                }
            } else {
                if (registers[inpX.first()]!! != 0) {
                    if (input[2].toIntOrNull() != null) {
                        index += input[2].toInt()
                    } else {
                        val toJump = registers[input[2].first()]!!
                        index += toJump
                    }
                    continue
                }
            }
        } else if (instr == "tgl") {
            val idx: Int = if (inpX.toIntOrNull() != null) {
                inpX.toIntOrNull()!!
            } else {
                registers[inpX.first()]!!
            }
            toggle(index + idx, split, registers)
        }
        index++
    }
    return registers['a']!!
}

fun toggle(idx: Int, instr: MutableList<List<String>>, registers: MutableMap<Char, Int>) {
    if (idx > instr.lastIndex) {
        return
    }
    val toCheck = instr[idx]
    if (toCheck[0] == "inc" && toCheck.size == 2) {
        instr[idx] = listOf("dec", toCheck[1])
    } else if (toCheck.size == 2) {
        instr[idx] = listOf("inc", toCheck[1])
    } else if (toCheck[0] == "jnz" && toCheck.size == 3) {
        instr[idx] = listOf("cpy", toCheck[1], toCheck[2])
    } else if (toCheck.size == 3) {
        println("instr = ${toCheck}")
        instr[idx] = listOf("jnz", toCheck[1], toCheck[2])
    } else if (toCheck[0] == "tgl") {
        val f = toCheck[1]
        val aVal: Int = if (f.toIntOrNull() != null) {
            f.toInt()
        } else {
            registers[f.first()]!!
        }
        if (aVal == 0) {
            instr[idx] = listOf("inc", toCheck[1])
        }
    }

}