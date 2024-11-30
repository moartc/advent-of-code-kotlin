package solutions.aoc2017.day18

import utils.Resources

fun main() {
    val input = Resources.getLines(2017, 18)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Long {

    var registers = mutableMapOf<String, Long>()
    var lastSound = -1L

    fun getValue(registers: MutableMap<String, Long>, s: String): Long {
        return if (s.toLongOrNull() != null) {
            s.toLong()
        } else {
            registers.getOrDefault(s, 0)
        }
    }

    tailrec fun step(instr: List<String>, next: Int, registers: MutableMap<String, Long>): Long {

        if (next >= 0 && next <= instr.size) {

            var instrToDo = instr.get(next)
            val split = instrToDo.split(" ")
            var cmd = split[0]
            var reg = split[1]
            var regValue = getValue(registers, reg)

            if (cmd.equals("snd")) {
                lastSound = regValue.toLong()
            } else if (cmd.equals("set")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, value)
            } else if (cmd.equals("add")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) + value)
            } else if (cmd.equals("mul")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) * value)
            } else if (cmd.equals("mod")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) % value)
            } else if (cmd.equals("rcv")) {
                if (regValue != 0L) {
                    registers.put(reg, lastSound)
                    return lastSound
                }
            } else if (cmd.equals("jgz")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                if (regValue > 0L) {
                    return step(instr, next + value.toInt(), registers)
                }
            }
        }
        return step(instr, next + 1, registers)
    }
    step(input, 0, registers)
    return lastSound
}


fun part2(input: List<String>): Int {

    var registers0 = mutableMapOf("p" to 0L)
    var registers1 = mutableMapOf("p" to 1L)

    var msgQueue0: ArrayDeque<Long> = ArrayDeque()
    var msgQueue1: ArrayDeque<Long> = ArrayDeque()

    fun getValue(registers: MutableMap<String, Long>, s: String): Long {
        return if (s.toLongOrNull() != null) {
            s.toLong()
        } else {
            registers.getOrDefault(s, 0)
        }
    }

    var failedReceive0 = false
    var failedReceive1 = false

    var sendCtr = 0

    fun step(instr: List<String>, current: Int, registers: MutableMap<String, Long>, versionProg: Int): Int {
        if (current >= 0 && current <= instr.size) {
            var instrToDo = instr.get(current)
            val split = instrToDo.split(" ")
            var cmd = split[0]
            var reg = split[1]

            var regValue = getValue(registers, reg)
            if (versionProg == 0) {
                failedReceive0 = false
            } else {
                failedReceive1 = false
            }
            if (cmd.equals("snd")) {
                if (versionProg == 0) {
                    val value = getValue(registers0, reg)
                    msgQueue1.add(value)
                } else {
                    val value = getValue(registers1, reg)
                    sendCtr++
                    msgQueue0.add(value)
                }
            } else if (cmd.equals("set")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, value)
            } else if (cmd.equals("add")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) + value)
            } else if (cmd.equals("mul")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) * value)
            } else if (cmd.equals("mod")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                registers.put(reg, registers.getOrDefault(reg, 0) % value)
            } else if (cmd.equals("rcv")) {
                if (versionProg == 0) {
                    val firstOrNull = msgQueue0.removeFirstOrNull()
                    if (firstOrNull != null) {
                        registers0.put(reg, firstOrNull)
                        return current + 1
                    } else {
                        failedReceive0 = true
                        return current
                    }
                } else {
                    val firstOrNull = msgQueue1.removeFirstOrNull()
                    if (firstOrNull != null) {
                        registers1.put(reg, firstOrNull)
                        return current + 1
                    } else {
                        // couldn't receive
                        failedReceive1 = true
                        return current
                    }
                }
            } else if (cmd.equals("jgz")) {
                var valueTwo = split[2]
                var value = getValue(registers, valueTwo)
                if (regValue.toLong() > 0L) {
                    return current + value.toInt()
                }
            }
        }
        return current + 1
    }

    var idx0 = 0
    var idx1 = 0
    while (true) {

        idx0 = step(input, idx0, registers0, 0)
        idx1 = step(input, idx1, registers1, 1)

        if (failedReceive1 && failedReceive0) {
            return sendCtr
        }
    }
}
