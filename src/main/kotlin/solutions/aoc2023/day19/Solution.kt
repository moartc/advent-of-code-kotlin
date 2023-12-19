package solutions.aoc2023.day19

import org.junit.jupiter.api.fail
import utils.Resources
import utils.splitOnEmpty
import kotlin.math.max
import kotlin.math.min

fun main() {

    val inputLine =
        Resources.getLines(2023, 19)
//        Resources.getLinesExample(2023, 19)
//    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part2(input: List<String>): Long {

    val splitOnEmpty = input.splitOnEmpty()
    val rules = splitOnEmpty[0]
    val values = splitOnEmpty[1]
    val allRules = mutableListOf<Rule>()
    rules.forEach {
        it.log()
        val wfName = it.takeWhile { x -> x != '{' }
        wfName.log("name")
        val conditions = it.takeLastWhile { x -> x != '{' }.dropLast(1)
        val split = conditions.split(",")

        val opList = mutableListOf<Op>()
        var alt = ""
        for ((index, s) in split.withIndex()) {
            if (index != split.lastIndex) {
                val parseCond = parseCond(s)
                opList.add(parseCond)
                parseCond.log("condition parsed")
            } else {
                alt = s
            }
        }
        val newRule = Rule(wfName, opList, alt)
        allRules.add(newRule)
        newRule.log("new rule!!!!")
    }
    val answer = pp2("in", allRules, mutableListOf())

    glob.forEach { it.log("pg") }


    var tot = 0L
    glob.forEach {

        var xMax = 4000
        var mMax = 4000
        var aMax = 4000
        var sMax = 4000
        var xMin = 1
        var mMin = 1
        var aMin = 1
        var sMin = 1
        var res = 1L
        it.forEach { (v, o) ->
            val (name, opChar, valInt, _) = o
            if (v == true) {
                if (opChar == '>') {
                    if (name == "s") {
                        sMin = max(sMin, valInt + 1)
                    } else if (name == "m") {
                        mMin = max(mMin, valInt + 1)
                    } else if (name == "x") {
                        xMin = max(xMin, valInt + 1)
                    } else if (name == "a") {
                        aMin = max(aMin, valInt + 1)
                    }
                } else { // true that <
                    if (name == "s") {
                        sMax = min(valInt - 1, sMax)
                    } else if (name == "m") {
                        mMax = min(valInt - 1, mMax)
                    } else if (name == "x") {
                        xMax = min(valInt - 1, xMax)
                    } else if (name == "a") {
                        aMax = min(valInt - 1, aMax)
                    }
                }
            } else { // false
                if (opChar == '>') { // true that <= v
                    if (name == "s") {
                        sMax = min(valInt, sMax)
                    } else if (name == "m") {
                        mMax = min(valInt, mMax)
                    } else if (name == "x") {
                        xMax = min(valInt, xMax)
                    } else if (name == "a") {
                        aMax = min(valInt, aMax)
                    }

                } else { //false  that < -> true >=
                    if (name == "s") {
                        sMin = max(sMin, valInt)
                    } else if (name == "m") {
                        mMin = max(mMin, valInt)
                    } else if (name == "x") {
                        xMin = max(xMin, valInt)
                    } else if (name == "a") {
                        aMin = max(aMin, valInt)
                    }
                }

            }
        }
        res *= (mMax - mMin + 1)
        res *= (aMax - aMin + 1)
        res *= (xMax - xMin + 1)
        res *= (sMax - sMin + 1)
        println("after line s = $sMax, m = $mMax, a = $aMax, x = $xMax")
        println("after line s = $sMin, m = $mMin, a = $aMin, x = $xMin")
        println("res = $res")
        tot += res
    }
    tot.log("tot")
    return 123L
    //256000000000000
    //167409079868000
}

val glob = mutableListOf<List<Pair<Boolean, Op>>>()


fun pp2(currIdx: String, rules: List<Rule>, currVal: List<Pair<Boolean, Op>>) {

    currIdx.log("process for $currIdx")
    val rtp = rules.first { r -> r.rn == currIdx }
    val add = currVal.toMutableList()
    for ((index, op) in rtp.ops.withIndex()) {
        val (name, opc, cv, ift) = op
        if (ift == "R") {
        } else if (ift == "A") {
            val cp = add.toMutableList()// rocess something
            cp.add(true to op)
            glob.add(cp)
        } else {
            val cp = add.toMutableList()// rocess something
            cp.add(true to op)
            pp2(ift, rules, cp)
        }
        add.add(false to op)
    }
    if (rtp.alt == "R") {
    } else if (rtp.alt == "A") {
        glob.add(add)
    } else { //can process something
        pp2(rtp.alt, rules, add)
    }
}

fun part1(input: List<String>): Int {

    val splitOnEmpty = input.splitOnEmpty()
    val rules = splitOnEmpty[0]
    val values = splitOnEmpty[1]
    val allRules = mutableListOf<Rule>()
    rules.forEach {
        it.log()
        val wfName = it.takeWhile { x -> x != '{' }
        wfName.log("name")
        val conditions = it.takeLastWhile { x -> x != '{' }.dropLast(1)
        val split = conditions.split(",")

        val opList = mutableListOf<Op>()
        var alt = ""
        for ((index, s) in split.withIndex()) {
            if (index != split.lastIndex) {
                val parseCond = parseCond(s)
                opList.add(parseCond)
                parseCond.log("condition parsed")
            } else {
                alt = s
            }
        }
        val newRule = Rule(wfName, opList, alt)
        allRules.add(newRule)
        newRule.log("new rule!!!!")
    }
    val allValues = mutableListOf<Map<Char, Int>>()
    values.forEach { it ->
        val noPar = it.dropLast(1).drop(1)
        var pairs = mutableListOf<Pair<Char, Int>>()
        val map = noPar.split(",").map { single ->
            val split = single.split("=")
            val cV = split[0][0]
            val vInt = split[1].toInt()
            (cV to vInt)
        }.toMap()
        allValues.add(map)
    }

    allValues.forEach { it.log("vvp") }

    var totSum = 0
    allValues.forEach {
        var result = 0

        "will process".log()
        val answer = process("in", allRules, it)
        answer.log("answer")
        if (answer == "A") {
            result += it.values.sum()
        }
        totSum += result
    }
        totSum.log("tot")
    return totSum
}


fun process(currIdx: String, rules: List<Rule>, values: Map<Char, Int>): String {

    currIdx.log("process for $currIdx")
    val rtp = rules.first { r -> r.rn == currIdx }
    for ((index, op) in rtp.ops.withIndex()) {
        val (name, op, cv, ift) = op
        if (op == '<') {
            if (values[name[0]]!! < cv) {
                if (ift in "AR") {
                    return ift
                } else {
                    return process(ift, rules, values)
                }
            } else {
                if (index != rtp.ops.lastIndex) {
                    continue
                } else {
                    if (rtp.alt in "AR") {
                        return rtp.alt
                    } else {
                        return process(rtp.alt, rules, values)
                    }

                }
            }
        } else { // >
            if (values[name[0]]!! > cv) {
                if (ift in "AR") {
                    return ift
                } else {
                    return process(ift, rules, values)
                }
            } else {
                if (index != rtp.ops.lastIndex) {
                    continue
                } else {
                    if (rtp.alt in "AR") {
                        return rtp.alt
                    } else {
                        return process(rtp.alt, rules, values)
                    }
                }
            }
        }
    }
    fail("err1")
    return rtp.alt
}


fun parseCond(cond: String): Op {
    val s = cond.takeWhile { x -> (x != '<' && x != '>') }
    val op = cond.first { x -> x == '<' || x == '>' }
    val s2 = cond.takeLastWhile { x -> (x != '<' && x != '>') }
    val op2 = s2.split(":")
    val valueToCond = op2[0].toInt()
    val altern = op2[1]

    return Op(s, op, valueToCond, altern)
}

data class Op(val name: String, val op: Char, val cv: Int, val ift: String)
data class Rule(var rn: String, var ops: MutableList<Op>, var alt: String)

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }



