package solutions.aoc2017.day23

import utils.Resources
import kotlin.math.sqrt

fun main() {
    val input = Resources.getLines(2017, 23)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {
    val registers = mutableMapOf<String, Long>()
    registers.put("a", 0)
    registers.put("b", 0)
    registers.put("c", 0)
    registers.put("d", 0)
    registers.put("e", 0)
    registers.put("f", 0)
    registers.put("g", 0)
    registers.put("h", 0)

    var mulctr = 0
    fun getValue(registers: MutableMap<String, Long>, s: String): Long {
        return if (s.toLongOrNull() != null) {
            s.toLong()
        } else {
            registers.getOrDefault(s, 0)
        }
    }

    tailrec fun step(instr: List<String>, next: Int, registers: MutableMap<String, Long>): Long {

        if (next >= 0 && next < instr.size) {
            val instrToDo = instr.get(next)
            val split = instrToDo.split(" ")
            val cmd = split[0]
            val reg = split[1]
            val regValue = getValue(registers, reg)

            if (cmd == "set") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, intVal)
            } else if (cmd == "sub") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, regValue - intVal)
            } else if (cmd == "mul") {
                mulctr++
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, regValue * intVal)
            } else if (cmd == "jnz") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                if (regValue != 0L) {
                    return step(instr, next + intVal.toInt(), registers)
                }
            }
            return step(instr, next + 1, registers)
        } else {
            return -1
        }
    }
    step(input, 0, registers)
    return mulctr
}

fun part2(input: List<String>): Int {
    // see the very bottom. I left the method that I used to solve this problem, though.
    val registers = mutableMapOf<String, Long>()
    registers.put("a", 1)
    registers.put("b", 0)
    registers.put("c", 0)
    registers.put("d", 0)
    registers.put("e", 0)
    registers.put("f", 0)
    registers.put("g", 0)
    registers.put("h", 0)

    var mulctr = 0
    fun getValue(registers: MutableMap<String, Long>, s: String): Long {
        return if (s.toLongOrNull() != null) {
            s.toLong()
        } else {
            registers.getOrDefault(s, 0)
        }
    }

    var iterctr = 1

    tailrec fun step(instr: List<String>, next: Int, registers: MutableMap<String, Long>): Long {
        val dBefore = getValue(registers, "d")
        var regValueG = getValue(registers, "g")
        var regValueH = getValue(registers, "h")

        iterctr++
        if (next >= 0 && next < instr.size) {

            val instrToDo = instr.get(next)

            val split = instrToDo.split(" ")
            val cmd = split[0]
            val reg = split[1]
            val regValue = getValue(registers, reg)

            if (cmd == "set") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, intVal)
            } else if (cmd == "sub") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, regValue - intVal)
            } else if (cmd == "mul") {
                mulctr++
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                registers.put(reg, regValue * intVal)
            } else if (cmd == "jnz") {
                val valueTwo = split[2]
                val intVal = getValue(registers, valueTwo)
                if (regValue != 0L) {
                    return step(instr, next + intVal.toInt(), registers)
                }
            }
            val dAfter = getValue(registers, "d")
            val hAfter = getValue(registers, "h")
            println("step: $instrToDo")
            println("after step: $next register: $registers")
            if (dBefore != dAfter) {
                println("step: $instrToDo")
                println("after step: $next register: $registers")
                println("d has changed from $dBefore to $dAfter")
            }
            if (regValueH != hAfter) {
                println("step: $instrToDo")
                println("after step: $next register: $registers")
                println("h has changed from $regValueH to $hAfter")
            }
            return step(instr, next + 1, registers)
        } else {
            return -1
        }
    }

    /*
        it looks like it doesn't change a, b, c,
        and now, read it from the bottom up (though it changed a few times during implementation, so it might be outdated)
        00: set b 67
        01: set c b
        02: jnz a 2
        03: jnz 1 5
        04: mul b 100
        05: sub b -100000
        06: set c b
        07: sub c -17000
        08: set f 1
        09: set d 2     <- this line is probably not visited during the iteration
        10: set e 2     <- this line is probably not visited during the iteration because line 19 jumps only 8 steps
        11: set g d     <- d * e != b
        12: mul g e     <- prev g * e != b
        13: sub g b     <- g - b != 0 -> g != b
        14: jnz g 2     <- when g == 0 here, I don't reset f to 0
        15: set f 0
        16: sub e -1
        17: set g e
        18: sub g b
        19: jnz g -8    <- I don't want g here == 0
        20: sub d -1    <- d == g+1 == b+1
        21: set g d     <- below, g == b, so here g == d == b
        22: sub g b     <- below, g==0, so here g == b
        23: jnz g -13   <- I cannot return from this line, so g == 0
        24: jnz f 2     <- f must be zero
        25: sub h -1    <- this doesn't matter
        26: set g b     <- g has the same value as b, so b should be == 123700
        27: sub g c     <- g must equal c, c after iteration is always 123700, so g == 123700
        28: jnz g 2     <- I must go through this line, so g == 0
        29: jnz 1 3     <- I must be here
        30: sub b -17   <- this doesn't matter
        31: jnz 1 -23   <- I can't reach this line

        ---
        Output analysis:
        A few logs from the iteration where 'd' gets a new value:
        after step: 20 register: {a=1, b=106700, c=123700, d=24, e=106700, f=0, g=0, h=0}
        after step: 20 register: {a=1, b=106700, c=123700, d=25, e=106700, f=0, g=0, h=0}
        after step: 20 register: {a=1, b=106700, c=123700, d=26, e=106700, f=0, g=0, h=0}

        d changes when e == b, and then it moves to step 21
        For step 21, I want d == b
        I start with the register:
        register: {a=1, b=106700, c=123700, d=106699, e=106700, f=0, g=0, h=0}, and the next step 21

        the next update is for h:
        after step: 25 register: {a=1, b=106700, c=123700, d=106700, e=106700, f=0, g=0, h=1}

        and then there are 2 random updates for the value of d:
        after step: 20 register: {a=1, b=106717, c=123700, d=98, e=106717, f=0, g=0, h=1}
        after step: 20 register: {a=1, b=106717, c=123700, d=99, e=106717, f=0, g=0, h=1}

        h changes when b == d. Then b increases by 17
        The next check for h (to check if it increases to 2) for the following register:
        register: {a=1, b=106717, c=123700, d=106716, e=106717, f=0, g=0, h=1}, and the next step 21

        output:
        after step: 25 register: {a=1, b=106717, c=123700, d=106717, e=106717, f=0, g=0, h=2}
        after step: 25 register: {a=1, b=106717, c=123700, d=106717, e=106717, f=0, g=0, h=2}
        and then another iterations
        after step: 20 register: {a=1, b=106734, c=123700, d=20, e=106734, f=0, g=0, h=2}
        after step: 20 register: {a=1, b=106734, c=123700, d=21, e=106734, f=0, g=0, h=2}

        h really changes every 17, but the answer is not (c - original b / 17) as I initially thought,
        That gives me 1000 or 999 which is too high.

        The next value where I get h = 3, but f == 1 here
        after step: 20 register: {a=1, b=106751, c=123700, d=17, e=106751, f=1, g=0, h=3}
        after step: 20 register: {a=1, b=106751, c=123700, d=18, e=106751, f=1, g=0, h=3}
        The value of h remains the same, but f changes back to 0
        after step: 20 register: {a=1, b=106768, c=123700, d=527, e=106768, f=0, g=0, h=3}
        after step: 20 register: {a=1, b=106768, c=123700, d=528, e=106768, f=0, g=0, h=3}
        then I get:
        after step: 20 register: {a=1, b=106785, c=123700, d=41, e=106785, f=0, g=0, h=4}
        after step: 20 register: {a=1, b=106785, c=123700, d=42, e=106785, f=0, g=0, h=4}
        then:
        after step: 20 register: {a=1, b=106802, c=123700, d=40, e=106802, f=0, g=0, h=5}
        after step: 20 register: {a=1, b=106802, c=123700, d=41, e=106802, f=0, g=0, h=5}
        then:
        after step: 20 register: {a=1, b=106819, c=123700, d=34, e=106819, f=1, g=0, h=6}
        after step: 20 register: {a=1, b=106819, c=123700, d=35, e=106819, f=1, g=0, h=6}
        then - again, without updated h, when f == 1 then update is skipped:
        after step: 20 register: {a=1, b=106836, c=123700, d=38, e=106836, f=0, g=0, h=6}
        after step: 20 register: {a=1, b=106836, c=123700, d=39, e=106836, f=0, g=0, h=6}

        Back to operations - in line 14 I need g == 0 during the iteration,
        with this, g - b == 0 -> g == b
        prev g * e == b
        d * e == b
        d changes from 2 to b, and e is more or less similar. Essentially I need to check if it's a prime number,
        If so, then don't increase the counter
       */
    fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        val sqrt = sqrt(num.toDouble()).toInt()
        return (2..sqrt).none { num % it == 0 }
    }

    var res = 0
    // this could be changed because it doesn't even use my input 'dynamically'. it's hardcoded for it instead
    for (b in 106700..123700 step 17) {
        if (!isPrime(b)) {
            res++
        }
    }
    return res
}


