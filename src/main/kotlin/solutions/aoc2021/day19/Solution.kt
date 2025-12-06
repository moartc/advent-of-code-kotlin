package solutions.aoc2021.day19

import utils.Resources
import utils.parser.getInts
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2021, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


data class Coords(val x: Int, val y: Int, val z: Int)

fun part1(inputLines: List<String>): Int {

    val scanners = mutableListOf<MutableList<Coords>>()
    var listOfBeacons = mutableListOf<Coords>()
    for (line in inputLines) {
        if (line.contains("scanner")) {
        } else if (line.isEmpty()) {
            // skip
            scanners.add(listOfBeacons)
            listOfBeacons = mutableListOf()
        } else {
            val ints = line.getInts()
            val c = Coords(ints[0], ints[1], ints[2])
            listOfBeacons.add(c)
        }
    }
    // the last one not added in the loop above
    scanners.add(listOfBeacons)


    // list index represents the scanner, and each sublist holds the beacons it detects with coordinates relative to scanner 0
    val listOfBeaconsRelTo0 = mutableMapOf<Int, List<Coords>>()
    // add all for scanner 0
    listOfBeaconsRelTo0[0] = scanners[0]

    var foundNew = true
    while (foundNew) {
        foundNew = false
        for (scIdx0 in 0..scanners.lastIndex) {
            if (!listOfBeaconsRelTo0.contains(scIdx0)) {
                continue
            }
            val scanner0 = listOfBeaconsRelTo0[scIdx0]!!
            for (scIdx1 in 0..scanners.lastIndex) {
                if (scIdx0 == scIdx1 || listOfBeaconsRelTo0.contains(scIdx1)) {
                    continue
                }
                val scanner1 = scanners[scIdx1]
                val allDistSc0 = getAllScannerDistBetweenPoints(scanner0)
                val allDistSc1 = getAllScannerDistBetweenPoints(scanner1)
                val possiblePairToCounter = mutableMapOf<Pair<Int, Int>, Int>()
                for (e0 in allDistSc0.entries) {
                    for (e1 in allDistSc1.entries) {
                        if (e0.value == e1.value) {
                            possiblePairToCounter.merge(e0.key.first to e1.key.first, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.first to e1.key.second, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.second to e1.key.first, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.second to e1.key.second, 1, Int::plus)
                        }
                    }
                }

                val filteredMap = possiblePairToCounter.filter { e -> e.value == 11 }
                if (filteredMap.size < 12) {
                    continue
                }

                fun findAllPossibleDistancesBetweenPairs(pairs: Set<Pair<Int, Int>>): MutableMap<Pair<Int, Int>, MutableList<Coords>> {
                    val mainMap = mutableMapOf<Pair<Int, Int>, MutableList<Coords>>()
                    for (pair in pairs) {
                        val coords0 = scanner0[pair.first]
                        val coords1 = scanner1[pair.second]
                        val allCoords1Rots = getAllRotations(coords1)
                        for (item in allCoords1Rots) {
                            val rot = item.value
                            val xD = coords0.x - item.key.x
                            val yD = coords0.y - item.key.y
                            val zD = coords0.z - item.key.z
                            val singleDiffs = mutableListOf<Int>()
                            singleDiffs.add(xD)
                            singleDiffs.add(yD)
                            singleDiffs.add(zD)
                            if (!mainMap.contains(rot)) {
                                mainMap[rot] = mutableListOf<Coords>()
                            }
                            mainMap[rot]!!.add(Coords(xD, yD, zD))
                        }
                    }
                    return mainMap
                }

                val map = findAllPossibleDistancesBetweenPairs(filteredMap.keys)

                val (key, value) = map.entries.first { e -> e.value.toSet().size == 1 }
                // they all should be the same
                val correctMove = value.first()
                val (p, signIdx) = key
                val sign = signs[signIdx]
                val mapOfCoords = mutableListOf<Coords>()
                for (coords in scanners[scIdx1]) {
                    val permute = permute(coords, p)
                    val rotated = Coords(permute.x * sign[0], permute.y * sign[1], permute.z * sign[2])
                    val newX = rotated.x + correctMove.x
                    val newY = rotated.y + correctMove.y
                    val newZ = rotated.z + correctMove.z
                    foundNew = true
                    mapOfCoords.add(Coords(newX, newY, newZ))
                }
                listOfBeaconsRelTo0[scIdx1] = mapOfCoords
            }
        }
    }
    return listOfBeaconsRelTo0.flatMap { (_, b) -> b }.toSet().size
}

fun part2(inputLines: List<String>): Any {

    val allPositions = mutableMapOf<Int, Coords>()
    val scanners = mutableListOf<MutableList<Coords>>()
    var listOfBeacons = mutableListOf<Coords>()
    for (line in inputLines) {
        if (line.contains("scanner")) {
        } else if (line.isEmpty()) {
            // skip
            scanners.add(listOfBeacons)
            listOfBeacons = mutableListOf()
        } else {
            val ints = line.getInts()
            val c = Coords(ints[0], ints[1], ints[2])
            listOfBeacons.add(c)
        }
    }
    // the last one not added in the loop above
    scanners.add(listOfBeacons)


    // list index represents the scanner, and each sublist holds the beacons it detects with coordinates relative to scanner 0
    val listOfBeaconsRelTo0 = mutableMapOf<Int, List<Coords>>()
    // add all for scanner 0
    listOfBeaconsRelTo0[0] = scanners[0]

    var foundNew = true
    while (foundNew) {
        foundNew = false
        for (scIdx0 in 0..scanners.lastIndex) {
            if (!listOfBeaconsRelTo0.contains(scIdx0)) {
                continue
            }
            val scanner0 = listOfBeaconsRelTo0[scIdx0]!!
            for (scIdx1 in 0..scanners.lastIndex) {
                if (scIdx0 == scIdx1 || listOfBeaconsRelTo0.contains(scIdx1)) {
                    continue
                }
                val scanner1 = scanners[scIdx1]
                val allDistSc0 = getAllScannerDistBetweenPoints(scanner0)
                val allDistSc1 = getAllScannerDistBetweenPoints(scanner1)
                val possiblePairToCounter = mutableMapOf<Pair<Int, Int>, Int>()
                for (e0 in allDistSc0.entries) {
                    for (e1 in allDistSc1.entries) {
                        if (e0.value == e1.value) {
                            possiblePairToCounter.merge(e0.key.first to e1.key.first, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.first to e1.key.second, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.second to e1.key.first, 1, Int::plus)
                            possiblePairToCounter.merge(e0.key.second to e1.key.second, 1, Int::plus)
                        }
                    }
                }

                val filteredMap = possiblePairToCounter.filter { e -> e.value == 11 }
                if (filteredMap.size < 12) {
                    continue
                }

                fun findAllPossibleDistancesBetweenPairs(pairs: Set<Pair<Int, Int>>): MutableMap<Pair<Int, Int>, MutableList<Coords>> {
                    val mainMap = mutableMapOf<Pair<Int, Int>, MutableList<Coords>>()
                    for (pair in pairs) {
                        val coords0 = scanner0[pair.first]
                        val coords1 = scanner1[pair.second]
                        val allCoords1Rots = getAllRotations(coords1)
                        for (item in allCoords1Rots) {
                            val rot = item.value
                            val xD = coords0.x - item.key.x
                            val yD = coords0.y - item.key.y
                            val zD = coords0.z - item.key.z
                            val singleDiffs = mutableListOf<Int>()
                            singleDiffs.add(xD)
                            singleDiffs.add(yD)
                            singleDiffs.add(zD)
                            if (!mainMap.contains(rot)) {
                                mainMap[rot] = mutableListOf<Coords>()
                            }
                            mainMap[rot]!!.add(Coords(xD, yD, zD))
                        }
                    }
                    return mainMap
                }

                val map = findAllPossibleDistancesBetweenPairs(filteredMap.keys)

                val (key, value) = map.entries.first { e -> e.value.toSet().size == 1 }
                // they all should be the same
                val correctMove = value.first()

                allPositions[scIdx1] = correctMove

                val (p, signIdx) = key
                val sign = signs[signIdx]
                val mm = mutableListOf<Coords>()
                for (coords in scanners[scIdx1]) {
                    val permute = permute(coords, p)
                    val rotated = Coords(permute.x * sign[0], permute.y * sign[1], permute.z * sign[2])
                    val newX = rotated.x + correctMove.x
                    val newY = rotated.y + correctMove.y
                    val newZ = rotated.z + correctMove.z
                    foundNew = true
                    mm.add(Coords(newX, newY, newZ))
                }
                listOfBeaconsRelTo0[scIdx1] = mm
            }
        }
    }
    var maxFound = Int.MIN_VALUE
    for ((i, e1) in allPositions.entries.withIndex()) {
        for ((j, e2) in allPositions.entries.withIndex()) {
            if (j <= i) {
                continue
            }
            val p1 = abs(e1.value.x - e2.value.x)
            val p2 = abs(e1.value.y - e2.value.y)
            val p3 = abs(e1.value.z - e2.value.z)
            val sum = p1 + p2 + p3
            maxFound = max(sum, maxFound)
        }
    }
    return maxFound
}

fun getAllScannerDistBetweenPoints(scanner: List<Coords>): MutableMap<Pair<Int, Int>, Double> {
    val allDistSc1 = mutableMapOf<Pair<Int, Int>, Double>()
    for (i in 0..scanner.lastIndex) {
        val coords0 = scanner[i]
        for (j in i + 1..scanner.lastIndex) {
            val coords1 = scanner[j]
            // distance between 1 from 24 rotations for both coordinates, all 24^2 combinations
            allDistSc1[i to j] = distance(coords0, coords1)
        }
    }
    return allDistSc1
}

fun distance(a: Coords, b: Coords): Double {
    val dx = (a.x - b.x).toDouble()
    val dy = (a.y - b.y).toDouble()
    val dz = (a.z - b.z).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz)
}

fun getListOfPermutations(c: Coords): List<Coords> = listOf(
    Coords(c.x, c.y, c.z),
    Coords(c.x, c.z, c.y),
    Coords(c.y, c.x, c.z),
    Coords(c.y, c.z, c.x),
    Coords(c.z, c.x, c.y),
    Coords(c.z, c.y, c.x)
)

fun permute(c: Coords, perm: Int): Coords {
    val coords = getListOfPermutations(c)[perm]
    return coords
}

// in total 6*8 version = 48
fun getAllRotations(c: Coords): MutableMap<Coords, Pair<Int, Int>> {
    val map = mutableMapOf<Coords, Pair<Int, Int>>()
    for ((i, p) in getListOfPermutations(c).withIndex()) {
        for ((j, s) in signs.withIndex()) {
            val key = i to j
            map[Coords(p.x * s[0], p.y * s[1], p.z * s[2])] = key
        }
    }
    return map
}

val signs = listOf(
    listOf(-1, -1, -1),
    listOf(-1, -1, 1),
    listOf(-1, 1, -1),
    listOf(-1, 1, 1),
    listOf(1, -1, -1),
    listOf(1, -1, 1),
    listOf(1, 1, -1),
    listOf(1, 1, 1),
)

