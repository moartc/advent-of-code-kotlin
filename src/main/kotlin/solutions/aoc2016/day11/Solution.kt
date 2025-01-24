package solutions.aoc2016.day11

import utils.Resources

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)
    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}

fun part2(inputLines: List<String>): Int {
    val parsed = parseInput(inputLines)
    parsed[0].addAll(setOf("eg", "em", "dg", "dm"))
    val size = parsed.sumOf { l -> l.size }
    return solve(parsed, size)

}

fun part1(inputLines: List<String>): Int {

    val parsed = parseInput(inputLines)
    val size = parsed.sumOf { l -> l.size }
    return solve(parsed, size)
}

fun solve(map: Array<MutableSet<String>>, toMove: Int): Int {

    val cache = mutableMapOf<Pair<String, Int>, Int>()
    val cashMoveToPair = mutableMapOf<Pair<MutableSet<String>, Pair<String, String>>, Boolean>()
    val cashMoveToSingle = mutableMapOf<Pair<MutableSet<String>, String>, Boolean>()
    val cashMoveFromPair = mutableMapOf<Pair<MutableSet<String>, Pair<String, String>>, Boolean>()
    val cacheMoveFromSingle = mutableMapOf<Pair<MutableSet<String>, String>, Boolean>()
    var bestFound = Int.MAX_VALUE

    val combinationCache = mutableMapOf<List<String>, List<Pair<String, String>>>()

    fun canMoveTogether(p: Pair<String, String>): Boolean {
        val (f, s) = p
        val isValid = f[0] != s[0] && f[1] != s[1]
        return !isValid
    }

    fun allUniqueCombinations(list: List<String>): MutableList<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                result.add(list[i] to list[j])
            }
        }
        return result
    }

    fun canMoveFromFloor(map: Array<MutableSet<String>>, s: String, floor: Int): Boolean {
        val current = map[floor]
        val key = current to s
        if (cacheMoveFromSingle.containsKey(key)) {
            return cacheMoveFromSingle[key]!!
        }
        val afterRemove = current.filter { x -> x != s }
        val allComb = combinationCache.computeIfAbsent(afterRemove) { _ -> allUniqueCombinations(afterRemove) }
        val ans = !(allComb.any { (l, r) ->
            l.first() != r.first() && l[1] != r[1] && current.none { x -> l.first() == x.first() && l[1] != x[1] || r.first() == x.first() && r[1] != x[1] }
        })
        cacheMoveFromSingle[key] = ans
        return ans
    }

    fun canMoveFromFloor(map: Array<MutableSet<String>>, p: Pair<String, String>, floor: Int): Boolean {
        val current = map[floor]
        val key = current to p
        if (cashMoveFromPair.containsKey(key)) {
            return cashMoveFromPair[key]!!
        }
        val afterRemove = current.filter { x -> x != p.first && x != p.second }
        val allComb = combinationCache.computeIfAbsent(afterRemove) { _ -> allUniqueCombinations(afterRemove) }
        val ans = !(allComb.any { (l, r) ->
            l.first() != r.first() && l[1] != r[1] && current.none { x -> l.first() == x.first() && l[1] != x[1] || r.first() == x.first() && r[1] != x[1] }
        })
        cashMoveFromPair[key] = ans
        return ans
    }

    fun canMoveToFloor(map: Array<MutableSet<String>>, s: String, floor: Int): Boolean {
        val key = map[floor] to s
        if (cashMoveToSingle.containsKey(key)) {
            return cashMoveToSingle[key]!!
        }
        val current = map[floor].toMutableList()
        current.add(s)
        val allComb = combinationCache.computeIfAbsent(current) { _ -> allUniqueCombinations(current) }
        val ans = !(allComb.any { (l, r) ->
            l.first() != r.first() && l[1] != r[1] && current.none { x -> l.first() == x.first() && l[1] != x[1] || r.first() == x.first() && r[1] != x[1] }
        })
        cashMoveToSingle[key] = ans
        return ans
    }

    fun canMoveToFloor(map: Array<MutableSet<String>>, p: Pair<String, String>, floor: Int): Boolean {
        val key = map[floor] to p
        if (cashMoveToPair.containsKey(key)) {
            return cashMoveToPair[key]!!
        }
        val current = map[floor].toMutableList()
        current.add(p.first)
        current.add(p.second)

        val allComb = combinationCache.computeIfAbsent(current.sorted()) { _ -> allUniqueCombinations(current) }
        val ans = !(allComb.any { (l, r) ->
            l.first() != r.first() && l[1] != r[1] && current.none { x -> l.first() == x.first() && l[1] != x[1] || r.first() == x.first() && r[1] != x[1] }
        })
        cashMoveToPair[key] = ans
        return ans
    }

    fun allBelowEmpty(mapToCheck: Array<MutableSet<String>>, current: Int): Boolean {
        if (current == 0) {
            return false
        }
        for (i in current - 1 downTo 0) {
            if (mapToCheck[i].isNotEmpty()) {
                return false
            }
        }
        return true
    }

    // it only makes sense to move an M/G down if the corresponding G/M is below
    fun doestItMakeSenseToMoveItDown(s: String, cMap: Array<MutableSet<String>>, currFloor: Int): Boolean {

        if (currFloor == 0) {
            return false
        }
        val curr = s[0]
        for (i in currFloor - 1 downTo 0) {

            if (cMap[i].any { x -> x.first() == curr }) {
                return true
            }
        }
        return false
    }


    fun move(cMap: Array<MutableSet<String>>, currFloor: Int, moves: Int) {

        val cacheKey = cMap.joinToString(",") { x -> x.sorted().joinToString("") } to currFloor
        val currentCache = cache[cacheKey]
        if (moves >= bestFound) {
            return
        }
        if (currentCache != null && currentCache <= moves) {
            return
        }

        cache[cacheKey] = moves

        if (cMap[3].size == toMove) {
            if (bestFound > moves) {
                bestFound = moves
            }
            return
        } else {
            val allSingleOptions = cMap[currFloor]
            val allPair =
                combinationCache.computeIfAbsent(cMap[currFloor].toList().sorted()) { _ -> allUniqueCombinations(cMap[currFloor].toList()) }
                    .filter { p -> canMoveTogether(p) }

            if (currFloor < 3) {
                allPair.forEach { p ->
                    if (canMoveFromFloor(cMap, p, currFloor) && canMoveToFloor(cMap, p, currFloor + 1)) {
                        val newMap = cMap.deepClone()
                        newMap[currFloor].remove(p.first)
                        newMap[currFloor].remove(p.second)
                        newMap[currFloor + 1].add(p.first)
                        newMap[currFloor + 1].add(p.second)
                        move(newMap, currFloor + 1, moves + 1)
                    }
                }
            }

            if (currFloor > 0 && !allBelowEmpty(cMap, currFloor)) {
                allSingleOptions.forEach { s ->
                    if (doestItMakeSenseToMoveItDown(s, cMap, currFloor)
                        && canMoveFromFloor(cMap, s, currFloor)
                        && canMoveToFloor(cMap, s, currFloor - 1)
                    ) {
                        val newMap = cMap.deepClone()
                        newMap[currFloor].remove(s)
                        newMap[currFloor - 1].add(s)
                        move(newMap, currFloor - 1, moves + 1)
                    }
                }
            }
        }
    }

    move(map, 0, 0)
    return bestFound
}


fun Array<MutableSet<String>>.deepClone(): Array<MutableSet<String>> {
    return Array(this.size) { index ->
        this[index].toMutableSet()
    }
}

fun parseInput(inputLines: List<String>): Array<MutableSet<String>> {
    return inputLines.map { line ->
        line.split(" a ").drop(1).map { it.split(" ") }.map { x -> x[0].first() + "" + x[1].first() }.toMutableSet()
    }.toTypedArray()
}

/*
part1 full path:
[pg, pm]
[tg, rg, rm, cg, cm, sg, sm]
[tm]
[]
------------------
[pg, pm]
[rm, cg, cm, sg, sm]
[tm, tg, rg]
[]
------------------
[pg, pm]
[rm, cg, cm, sg, sm]
[rg]
[tm, tg]
------------------
[pg, pm]
[rm, cg, cm, sg, sm]
[rg, tg]
[tm]
------------------
[pg, pm]
[rm, cg, cm, sg, sm]
[]
[tm, tg, rg]
------------------
[pg, pm]
[rm, cg, cm, sg, sm]
[tm]
[tg, rg]
------------------
[pg, pm]
[rm, cg, cm, sg, sm, tm]
[]
[tg, rg]
------------------
[pg, pm]
[cg, sg, sm, tm]
[rm, cm]
[tg, rg]
------------------
[pg, pm]
[cg, sg, sm, tm, cm]
[rm]
[tg, rg]
------------------
[pg, pm]
[sg, sm, tm]
[rm, cg, cm]
[tg, rg]
------------------
[pg, pm]
[sg, sm, tm]
[rm]
[tg, rg, cg, cm]
------------------
[pg, pm]
[sg, sm, tm]
[rm, rg]
[tg, cg, cm]
------------------
[pg, pm]
[sg, sm, tm]
[]
[tg, cg, cm, rg, rm]
------------------
[pg, pm]
[sg, sm, tm]
[tg]
[cg, cm, rg, rm]
------------------
[pg, pm]
[sg, sm, tm, tg]
[]
[cg, cm, rg, rm]
------------------
[pg, pm]
[sg, sm]
[tm, tg]
[cg, cm, rg, rm]
------------------
[pg, pm]
[sg, sm]
[]
[cg, cm, rg, rm, tm, tg]
------------------
[pg, pm]
[sg, sm]
[cg]
[cm, rg, rm, tm, tg]
------------------
[pg, pm]
[sg, sm, cg]
[]
[cm, rg, rm, tm, tg]
------------------
[pg, pm]
[cg]
[sg, sm]
[cm, rg, rm, tm, tg]
------------------
[pg, pm]
[cg]
[]
[cm, rg, rm, tm, tg, sg, sm]
------------------
[pg, pm]
[cg]
[cm]
[rg, rm, tm, tg, sg, sm]
------------------
[pg, pm]
[cg, cm]
[]
[rg, rm, tm, tg, sg, sm]
------------------
[pg, pm]
[]
[cg, cm]
[rg, rm, tm, tg, sg, sm]
------------------
[pg, pm]
[]
[]
[rg, rm, tm, tg, sg, sm, cg, cm]
------------------
[pg, pm]
[]
[rg]
[rm, tm, tg, sg, sm, cg, cm]
------------------
[pg, pm]
[rg]
[]
[rm, tm, tg, sg, sm, cg, cm]
------------------
[pg, pm, rg]
[]
[]
[rm, tm, tg, sg, sm, cg, cm]
------------------
[rg]
[pg, pm]
[]
[rm, tm, tg, sg, sm, cg, cm]
------------------
[rg]
[]
[pg, pm]
[rm, tm, tg, sg, sm, cg, cm]
------------------
[rg]
[]
[]
[rm, tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[rg]
[]
[rm]
[tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[rg]
[rm]
[]
[tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[rg, rm]
[]
[]
[tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[]
[rg, rm]
[]
[tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[]
[]
[rg, rm]
[tm, tg, sg, sm, cg, cm, pg, pm]
------------------
[]
[]
[]
[tm, tg, sg, sm, cg, cm, pg, pm, rg, rm]

conclusions and improvements to part2:
1. neve move 2 elements down
2. never move only 1 element up
3. it only makes sense to move an M/G down if the corresponding G/M is below
------------------
 */