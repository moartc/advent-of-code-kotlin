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

    val inputLines = Resources.getLines(2025, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Long {

    val allMachines = inputLines.map { line -> parse(line) }
    return allMachines.sumOf { machine ->
        fun getNeighbours(current: List<Boolean>): List<List<Boolean>> {
            return machine.buttons.map { lightIdx ->
                val next = current.toMutableList()
                for (idx in lightIdx) {
                    next[idx] = !next[idx]
                }
                next
            }
        }

        val initialState = machine.diagram.map { _ -> false }
        bfsGeneric(initialState, { it == machine.diagram }, ::getNeighbours)?.first?.toLong()!!
    }
}

fun part2(inputLines: List<String>): Long {
    val allMachines = inputLines.map { line -> parse(line) }
    return allMachines.sumOf { machine -> solve2(machine) }
}

// https://developers.google.com/optimization/cp
fun solve2(machine: Machine): Long {
    Loader.loadNativeLibraries()

    val model = CpModel()

    val vars = machine.buttons.map { j -> model.newIntVar(0, machine.joltage.max().toLong(), "$j") }.toTypedArray()

    for (joltIdx in 0 until machine.joltage.size) {
        val allSwitchesForJolt = mutableListOf<IntVar>()
        for ((swIdx, sw) in machine.buttons.withIndex()) {
            if (sw.contains(joltIdx)) {
                allSwitchesForJolt.add(vars[swIdx])
            }
        }
        val expectedValueForEq = machine.joltage[joltIdx].toLong()
        model.addEquality(LinearExpr.sum(allSwitchesForJolt.toTypedArray()), expectedValueForEq)
    }

    model.minimize(LinearExpr.sum(vars))

    val solver = CpSolver()
    solver.solve(model)
    return vars.sumOf { solver.value(it) }
}

fun parse(line: String): Machine {
    val parts = line.split(" ")
    val diagram = parts[0].trim('[', ']').map { it == '#' }
    val buttons = parts.drop(1).dropLast(1).map { q -> q.getInts().toSet() }
    val joltage = parts.last().getInts()
    return Machine(diagram, buttons, joltage)
}

data class Machine(val diagram: List<Boolean>, val buttons: List<Set<Int>>, val joltage: List<Int>)

