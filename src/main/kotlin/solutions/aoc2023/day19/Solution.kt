package solutions.aoc2023.day19

import utils.Resources
import utils.collections.extensions.splitOnEmpty
import kotlin.math.max
import kotlin.math.min

fun main() {

    val inputLine = Resources.getLines(2023, 19)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}


fun part1(input: List<String>): Int {

    val splitOnEmpty = input.splitOnEmpty()
    val workflowsPart = splitOnEmpty[0]
    val valuesPart = splitOnEmpty[1]
    val workflows = parseWorkflows(workflowsPart)
    val allValues = parseValues(valuesPart)


    fun process(currentName: String, workflows: List<Workflow>, valMap: Map<Char, Int>): String {

        if (currentName in "AR") {
            return currentName
        }
        val workflowToProcess = workflows.first { r -> r.name == currentName }

        workflowToProcess.rules.forEachIndexed { index, rule ->
            val (cat, op, intVal, ifTrue) = rule
            val condition = if (op == '<') valMap[cat]!! < intVal else valMap[cat]!! > intVal
            if (condition) {
                return process(ifTrue, workflows, valMap)
            } else if (index == workflowToProcess.rules.lastIndex) {
                return process(workflowToProcess.noMatch, workflows, valMap)
            }
        }
        error("cannot finish")
    }

    return allValues
        .map { value -> value to process("in", workflows, value) }
        .filter { p -> p.second == "A" }
        .sumOf { p -> p.first.values.sum() }
}


fun part2(input: List<String>): Long {

    val splitOnEmpty = input.splitOnEmpty()
    val workflowsPart = splitOnEmpty[0]
    val workflows = parseWorkflows(workflowsPart)
    val processResult = processP2(workflows)

    data class Range(var min: Int, var max: Int)

    return processResult.sumOf {

        val s = Range(1, 4000)
        val x = Range(1, 4000)
        val m = Range(1, 4000)
        val a = Range(1, 4000)

        val ranges = mutableMapOf(
            's' to s,
            'a' to a,
            'm' to m,
            'x' to x
        )

        fun updateMin(c: Char, valInt: Int) {
            ranges[c]!!.min = max(ranges[c]!!.min, valInt)
        }

        fun updateMax(c: Char, valInt: Int) {
            ranges[c]!!.max = min(ranges[c]!!.max, valInt)
        }

        it.forEach { (isTrue, rule) ->
            val (name, opChar, valInt, _) = rule
            if (isTrue) {
                if (opChar == '>') {
                    updateMin(name, valInt + 1)
                } else {
                    updateMax(name, valInt - 1)
                }
            } else {
                if (opChar == '>') {
                    updateMax(name, valInt)
                } else {
                    updateMin(name, valInt)
                }
            }
        }
        ranges.values.map { r -> r.max - r.min + 1L }.reduce { f, s -> f * s }
    }
}

fun processP2(workflows: List<Workflow>): MutableList<List<Pair<Boolean, Rule>>> {
    val foundRules = mutableListOf<List<Pair<Boolean, Rule>>>()

    fun process(currIdx: String, rules: List<Workflow>, currentRules: MutableList<Pair<Boolean, Rule>>) {

        val currentWorkflow = rules.first { r -> r.name == currIdx }
        for (rule in currentWorkflow.rules) {
            if (rule.ifTrue == "A") {
                val newList = currentRules.toMutableList()
                newList.add(true to rule)
                foundRules.add(newList)
            } else if (rule.ifTrue != "R") {
                val newList = currentRules.toMutableList()
                newList.add(true to rule)
                process(rule.ifTrue, rules, newList)
            }
            currentRules.add(false to rule)
        }
        if (currentWorkflow.noMatch == "A") {
            foundRules.add(currentRules)
        } else if (currentWorkflow.noMatch != "R") { //can process something
            process(currentWorkflow.noMatch, rules, currentRules)
        }
    }

    process("in", workflows, mutableListOf())
    return foundRules

}


fun parseWorkflows(list: List<String>): List<Workflow> {

    fun parseRule(ruleStr: String): Rule {
        val cat = ruleStr.first()
        val op = ruleStr[1]
        val afterOp = ruleStr.drop(2)
        val split = afterOp.split(":")
        val valueInt = split[0].toInt()
        val noMatch = split[1]
        return Rule(cat, op, valueInt, noMatch)
    }

    return list.map {
        val wfName = it.takeWhile { x -> x != '{' }
        val conditions = it.takeLastWhile { x -> x != '{' }.dropLast(1)
        val rulesString = conditions.split(",")
        val parsedRules = rulesString.dropLast(1).map { ruleString -> parseRule(ruleString) }
        Workflow(wfName, parsedRules, rulesString.last())
    }
}

fun parseValues(list: List<String>): List<Map<Char, Int>> {
    return list.map { it ->
        val noPar = it.dropLast(1).drop(1)
        val map = noPar.split(",").associate { single ->
            val split = single.split("=")
            split[0][0] to split[1].toInt()
        }
        map
    }
}


data class Rule(val cat: Char, val op: Char, val intVal: Int, val ifTrue: String)
data class Workflow(var name: String, var rules: List<Rule>, var noMatch: String)



