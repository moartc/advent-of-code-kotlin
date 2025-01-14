package solutions.aoc2016.day10

import utils.Resources
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

class Bot(var l: Int = -1, var h: Int = -1) {

    fun canGet(): Boolean {
        return l == -1 || h == -1
    }

    fun set(v: Int) {
        if (l == -1) {
            l = v
        } else if (h == -1) {
            if (v < l) {
                h = l
                l = v
            } else {
                h = v
            }
        }
    }

    fun canGive(): Boolean {
        return l != -1 && h != -1
    }
}

fun part1(inputLines: List<String>): Int {

    val bots = mutableMapOf<Int, Bot>()
    val outputs = mutableMapOf<Int, Int>()
    val instructions = inputLines.toList()
    val instrDone = mutableSetOf<Int>()
    var instructionIdx = 0
    while (true) {
        if (!instrDone.contains(instructionIdx)) {
            val instr = instructions[instructionIdx]
            if (instr.contains("goes")) {
                val (value, bot) = instr.getInts()
                if (bots.containsKey(bot) && bots[bot]!!.canGet()) {
                    instrDone.add(instructionIdx)
                    bots[bot]!!.set(value)
                } else if (!bots.containsKey(bot)) {
                    instrDone.add(instructionIdx)
                    bots[bot] = Bot(value)
                }

            } else if (instr.contains("gives")) {
                val split = instr.split(" ")
                val bot = split[1].toInt()
                val ltb = split[5] == "bot"
                val low = split[6].toInt()
                val htb = split[10] == "bot"
                val high = split[11].toInt()

                if (bots.containsKey(bot) && bots[bot]!!.canGive()) {

                    instrDone.add(instructionIdx)

                    val isLowOk = if (ltb) {
                        !bots.containsKey(low) || bots[low]!!.canGet()
                    } else {
                        true
                    }

                    val isHighOk = if (htb) {
                        !bots.containsKey(high) || bots[high]!!.canGet()
                    } else {
                        true
                    }

                    if (isLowOk && isHighOk) {
                        val bl = bots[bot]!!.l
                        val bh = bots[bot]!!.h

                        if (bl == 17 && bh == 61) {
                            return bot
                        }

                        if (ltb) {
                            if (bots.containsKey(low)) {
                                bots[low]!!.set(bl)
                            } else {
                                bots[low] = Bot(bl)
                            }
                        } else {
                            outputs[low] = bl
                        }

                        if (htb) {
                            if (bots.containsKey(high)) {
                                bots[high]!!.set(bh)
                            } else {
                                bots[high] = Bot(bh)
                            }
                        } else {
                            outputs[high] = bh
                        }
                        // reset current bot
                        bots[bot] = Bot()
                    }
                }
            }
        }
        if (instructionIdx == instructions.lastIndex) {
            instructionIdx = 0
        } else {
            instructionIdx++
        }
    }
}

fun part2(inputLines: List<String>): Int {
    val bots = mutableMapOf<Int, Bot>()
    val outputs = mutableMapOf<Int, Int>()
    val instructions = inputLines.toList()
    val instrDone = mutableSetOf<Int>()
    var instructionIdx = 0
    while (true) {
        if (!instrDone.contains(instructionIdx)) {
            val instr = instructions[instructionIdx]
            if (instr.contains("goes")) {
                val (value, bot) = instr.getInts()
                if (bots.containsKey(bot) && bots[bot]!!.canGet()) {
                    instrDone.add(instructionIdx)
                    bots[bot]!!.set(value)
                } else if (!bots.containsKey(bot)) {
                    instrDone.add(instructionIdx)
                    bots[bot] = Bot(value)
                }

            } else if (instr.contains("gives")) {
                val split = instr.split(" ")
                val bot = split[1].toInt()
                val ltb = split[5] == "bot"
                val low = split[6].toInt()
                val htb = split[10] == "bot"
                val high = split[11].toInt()

                if (bots.containsKey(bot) && bots[bot]!!.canGive()) {

                    instrDone.add(instructionIdx)

                    val isLowOk = if (ltb) {
                        !bots.containsKey(low) || bots[low]!!.canGet()
                    } else {
                        true
                    }

                    val isHighOk = if (htb) {
                        !bots.containsKey(high) || bots[high]!!.canGet()
                    } else {
                        true
                    }

                    if (isLowOk && isHighOk) {
                        val bl = bots[bot]!!.l
                        val bh = bots[bot]!!.h

                        if (outputs.containsKey(0) && outputs.containsKey(1) && outputs.containsKey(2)) {
                            return outputs[0]!! * outputs[1]!! * outputs[2]!!
                        }

                        if (ltb) {
                            if (bots.containsKey(low)) {
                                bots[low]!!.set(bl)
                            } else {
                                bots[low] = Bot(bl)
                            }
                        } else {
                            outputs[low] = bl
                        }

                        if (htb) {
                            if (bots.containsKey(high)) {
                                bots[high]!!.set(bh)
                            } else {
                                bots[high] = Bot(bh)
                            }
                        } else {
                            outputs[high] = bh
                        }
                        // reset current bot
                        bots[bot] = Bot()
                    }
                }
            }
        }
        if (instructionIdx == instructions.lastIndex) {
            instructionIdx = 0
        } else {
            instructionIdx++
        }
    }
}

