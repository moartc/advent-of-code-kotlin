package solutions.aoc2025.day10

import com.google.ortools.Loader
import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.IntVar
import com.google.ortools.sat.LinearExpr
import utils.Resources
import utils.algorithms.bfsGeneric
import utils.parser.getInts

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines =
//        Resources.getLinesExample(2025, day)
        Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


// https://developers.google.com/optimization/cp
fun solve2(s: S): Long {
    Loader.loadNativeLibraries()

    // Declare the model
    val model = CpModel()

    // Create the variables - switches are my variables
    val vars = s.switches.map { j -> model.newIntVar(0, s.joltage.max().toLong(), "$j") }.toTypedArray()

    for (joltIdx in 0 until s.joltage.size) {
        val allswitchesForJolt = mutableListOf<IntVar>()
        for ((swIdx, sw) in s.switches.withIndex()) {
            if (sw.contains(joltIdx)) {
                allswitchesForJolt.add(vars[swIdx])
            }
        }
        val asTyped = allswitchesForJolt.toTypedArray()
        val expValForEq = s.joltage[joltIdx].toLong()
        model.addEquality(LinearExpr.sum(asTyped), expValForEq)
    }

    model.minimize(LinearExpr.sum(vars))

    val solver = CpSolver()
    val status = solver.solve(model)
    // ignore status, jsut get the numb
    val q = vars.sumOf { solver.value(it) }
    return q
}

fun part2(inputLines: List<String>): Long {

    val all = mutableListOf<S>()
    inputLines.forEach { i ->
        val p = parse(i)
        all.add(p)
    }
    all.log("all")
    var r = 0L
    for (s in all) {
        val nos = solve2(s)
        nos.log("single one")
        r += nos
    }
    return r
}

fun parse(line: String): S {
    val parts = line.split(" ")
    val pattern = parts[0].trim('[', ']').map { it == '#' }
    val switches = parts.drop(1).dropLast(1).map { q -> q.getInts().toSet() }
    val joltage = parts.last().getInts()
    return S(pattern, switches, joltage)
}


data class S(val pattern: List<Boolean>, val switches: List<Set<Int>>, val joltage: List<Int>)


fun switch1(state: List<Boolean>, s: S): Long {
    fun getNeighbours(current: List<Boolean>): List<List<Boolean>> {
        return s.switches.map { swidx ->
            val next = current.toMutableList()
            for (idx in swidx) {
                next[idx] = !next[idx]
            }
            next
        }
    }

    val result = bfsGeneric(state, { it == s.pattern }, ::getNeighbours)
    return result?.first?.toLong()!!
}


fun part1(inputLines: List<String>): Long {

    inputLines.log("inp")

    val all = mutableListOf<S>()
    inputLines.forEach { i ->
        val p = parse(i)
        all.add(p)
    }

    all.log("all")

    var r = 0L
    for (s in all) {
        val curr = s.pattern.map { i -> false }
        val nos = switch1(curr, s)
        r += nos
    }


    println("x")

    return r
}


private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }

