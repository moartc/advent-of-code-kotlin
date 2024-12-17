package solutions.aoc2024.day17

import utils.Resources
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

/*
1098163944
 */

fun main() {

    val inputLines = Resources.getLines(2024, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): String {

    var a = inputLines[0].getInts()[0]
    var b = 0
    var c = 0
    val program = inputLines[4].getInts()

    var ptr = 0
    val output = mutableListOf<Int>()
    fun combo(op: Int): Int = when (op) {
        in 0..3 -> op
        4 -> a
        5 -> b
        6 -> c
        else -> throw IllegalArgumentException("wrong op=$op")
    }

    while (ptr < program.size) {
        val opcode = program[ptr]
        val op = program[ptr + 1]
        ptr += 2
        when (opcode) {
            0 -> {
                val denominator = 1 shl combo(op)
                a /= denominator
            }

            1 -> {
                b = b xor op
            }

            2 -> {
                b = combo(op) % 8
            }

            3 -> {
                if (a != 0) {
                    ptr = op
                }
            }

            4 -> {
                b = b xor c
            }

            5 -> {
                output.add(combo(op) % 8)
            }

            6 -> {
                val denominator = 1 shl combo(op)
                b = a / denominator
            }

            7 -> {
                val denominator = 1 shl combo(op)
                c = a / denominator
            }
        }
    }
    return output.joinToString(",")
}

/**
 * semi-automatic solution xD
 *
 * for suf = "" (so the regular bruteforce) it prints:
 * exp value=2 for index=0, for A=7, oct = 7
 * exp value=4 for index=1, for A=15, oct = 17
 * exp value=1 for index=2, for A=1039, oct = 2017
 * exp value=2 for index=3, for A=8177, oct = 17761
 * exp value=7 for index=4, for A=15375, oct = 36017
 * exp value=5 for index=5, for A=80911, oct = 236017
 * exp value=0 for index=6, for A=1391631, oct = 5236017
 * exp value=3 for index=7, for A=13974543, oct = 65236017
 * exp value=4 for index=8, for A=24132623, oct = 134036017
 * and it rather stops, but it looks like the oct suffix is '36017' so the next run only for ints which have such a suffix
 * in the oct representation (suf = "36017") prints:
 * exp value=2 for index=0, for A=48143, oct = 136017
 * exp value=4 for index=1, for A=48143, oct = 136017
 * exp value=1 for index=2, for A=48143, oct = 136017
 * exp value=2 for index=3, for A=80911, oct = 236017
 * exp value=7 for index=4, for A=80911, oct = 236017
 * exp value=5 for index=5, for A=80911, oct = 236017
 * exp value=0 for index=6, for A=1391631, oct = 5236017
 * exp value=3 for index=7, for A=13974543, oct = 65236017
 * exp value=4 for index=8, for A=24132623, oct = 134036017
 * exp value=7 for index=9, for A=1097874447, oct = 10134036017
 * exp value=1 for index=10, for A=44047547407, oct = 510134036017
 * exp value=7 for index=11, for A=168601598991, oct = 2350134036017
 * exp value=5 for index=12, for A=168601598991, oct = 2350134036017
 *
 * next suf update: 0134036017
 * and the last 3 missing (the initial are ofc different, but doesn't matter, at least for my inp):
 * exp value=5 for index=13, for A=1268113226767, oct = 22350134036017
 * exp value=3 for index=14, for A=27656392293391, oct = 622350134036017
 * exp value=0 for index=15, for A=190384113204239, oct = 5322350134036017
 *
 */
fun part2(inputLines: List<String>): Long {

    val program = inputLines[4].getInts()
    val printed = BooleanArray(program.size) { false }

    val suf = "0134036017"

    num@ for (i in (1..Long.MAX_VALUE)) {

        val toString = i.toString(8)
        val octalString = "$toString$suf"
        val aAtTheBeginning = octalString.toLong(8)
        var a = octalString.toLong(8)
        var b = 0L
        var c = 0L
        val output = mutableListOf<Int>()

        fun combo(op: Int): Long = when (op) {
            in 0..3 -> op.toLong()
            4 -> a
            5 -> b
            6 -> c
            else -> throw IllegalArgumentException("wrong op=$op")
        }

        var ptr = 0
        var outIdx = 0
        wl@ while (ptr < program.size) {
            val opcode = program[ptr]
            val op = program[ptr + 1]
            ptr += 2

            when (opcode) {
                0 -> {
                    val denominator = 1 shl combo(op).toInt()
                    a /= denominator
                }

                1 -> {
                    b = b.xor(op.toLong())
                }

                2 -> {
                    b = combo(op) % 8
                }

                3 -> {
                    if (a != 0L) {
                        ptr = op
                    }
                }

                4 -> {
                    b = b.xor(c)
                }

                5 -> {
                    val outResult = (combo(op) % 8).toInt()
                    output.add(outResult)
                    if (program[outIdx] == outResult) {
                        if (!printed[outIdx]) {
                            println("exp value=$outResult for index=$outIdx, for A=$aAtTheBeginning, oct = ${aAtTheBeginning.toString(8)}")
                            printed[outIdx] = true
                        }
                        outIdx++
                        if (outIdx == program.size) {
                            return aAtTheBeginning
                        } else {
                            continue@wl
                        }
                    } else {
                        continue@num
                    }
                }

                6 -> {
                    val denominator = 1 shl combo(op).toInt()
                    b = a / denominator
                }

                7 -> {
                    val denominator = 1 shl combo(op).toInt()
                    c = a / denominator
                }
            }
        }
    }

    return -1L // shouldn't happen
}
