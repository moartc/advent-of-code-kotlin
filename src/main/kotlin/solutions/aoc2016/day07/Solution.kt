package solutions.aoc2016.day07

import utils.Resources


fun main() {

    val inputLines = Resources.getLines(2016, 7)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part1(inputLines: List<String>): Int {

    fun hasAbba(s: String): Boolean {
        for (i in 0..s.lastIndex - 3) {
            if (s[i] != s[i + 1] && s[i + 1] == s[i + 2] && s[i] == s[i + 3]) {
                return true
            }
        }
        return false
    }

    fun split(s: String): Pair<MutableList<String>, MutableList<String>> {
        val outside = mutableListOf<String>()
        val inside = mutableListOf<String>()
        var current = ""
        for (c in s) {
            if (c == '[') {
                outside.add(current)
                current = ""
            } else if (c == ']') {
                inside.add(current)
                current = ""
            } else {
                current += c
            }
        }
        if (current != "") {
            outside.add(current)
        }
        return inside to outside
    }

    fun support(s: String): Boolean {
        val (inside, outside) = split(s)
        val isAnyIn = inside.any { hasAbba(it) }
        if (isAnyIn) {
            return false
        }
        val isAnyOut = outside.any { hasAbba(it) }
        return isAnyOut
    }
    return inputLines.count { l -> support(l) }
}


fun part2(inputLines: List<String>): Int {

    fun getAba(s: String): MutableList<String> {
        val abas = mutableListOf<String>()
        for (i in 0..s.lastIndex - 2) {
            if (s[i] != s[i + 1] && s[i] == s[i + 2]) {
                val a = s[i]
                val b = s[i + 1]
                val c = s[i + 2]
                abas.add("$a$b$c")
            }
        }
        return abas
    }

    fun split(s: String): Pair<MutableList<String>, MutableList<String>> {
        val outside = mutableListOf<String>()
        val inside = mutableListOf<String>()
        var current = ""
        for (c in s) {
            if (c == '[') {
                outside.add(current)
                current = ""
            } else if (c == ']') {
                inside.add(current)
                current = ""
            } else {
                current += c
            }
        }
        if (current != "") {
            outside.add(current)
        }
        return inside to outside
    }

    fun support(s: String): Boolean {
        val (inside, outside) = split(s)
        val abasOut = outside.flatMap { getAba(it) }
        if (abasOut.isEmpty()) {
            return false
        }
        val toFind = abasOut.map { i -> "${i[1]}${i[0]}${i[1]}" }
        return inside.any { i ->
            toFind.any { tf -> i.contains(tf) }
        }
    }
    return inputLines.count { l -> support(l) }
}
