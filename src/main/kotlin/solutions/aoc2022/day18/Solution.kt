package solutions.aoc2022.day18

import utils.Resources


fun main() {
    val input = Resources.getLines(2022, 18)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>): Int {

    val cubeFP = input.map { it -> it.split(",").map { it.toInt() } }
    val cubes = cubeFP.map { l -> Cube(l[0], l[1], l[2]) }
    return cubes.sumOf { totalExposed(it, cubes, emptySet()) }
}

fun part2(input: List<String>): Int {

    val cubes = input.map { it -> it.split(",").map { it.toInt() } }.map { l -> Cube(l[0], l[1], l[2]) }

    val xRange = cubes.minOf { it.x } - 1 until cubes.maxOf { it.x } + 1
    val yRange = cubes.minOf { it.y } - 1 until cubes.maxOf { it.y } + 1
    val zRange = cubes.minOf { it.z } - 1 until cubes.maxOf { it.z } + 1

    val start = Cube(xRange.first, yRange.first, zRange.first)
    val airCollection = mutableSetOf<Cube>()

    val queue = mutableSetOf(start)
    while (queue.isNotEmpty()) {
        val current = queue.first().also { queue.remove(it) }
        airCollection.add(current)
        queue.addAll(getNeighbour(current).filter { it !in airCollection && it.inRange(xRange, yRange, zRange) && it !in cubes })
    }
    val allCubes = xRange.flatMap { x -> yRange.flatMap { y -> zRange.map { z -> Cube(x, y, z) } } }.toMutableSet()
    allCubes.removeAll(airCollection)
    allCubes.removeAll(cubes.toSet())

    return cubes.sumOf { totalExposed(it, cubes, allCubes) }
}

fun totalExposed(c1: Cube, l: List<Cube>, t: Set<Cube>): Int {

    var ctr = 6
    val x1 = Cube(c1.x - 1, c1.y, c1.z)
    val x2 = Cube(c1.x + 1, c1.y, c1.z)
    val y1 = Cube(c1.x, c1.y + 1, c1.z)
    val y2 = Cube(c1.x, c1.y - 1, c1.z)
    val z1 = Cube(c1.x, c1.y, c1.z + 1)
    val z2 = Cube(c1.x, c1.y, c1.z - 1)

    if (l.contains(x1) || t.contains(x1)) ctr -= 1
    if (l.contains(x2) || t.contains(x2)) ctr -= 1
    if (l.contains(y1) || t.contains(y1)) ctr -= 1
    if (l.contains(y2) || t.contains(y2)) ctr -= 1
    if (l.contains(z1) || t.contains(z1)) ctr -= 1
    if (l.contains(z2) || t.contains(z2)) ctr -= 1

    return ctr
}

fun getNeighbour(c1: Cube): List<Cube> {
    return listOf(
        Cube(c1.x - 1, c1.y, c1.z),
        Cube(c1.x + 1, c1.y, c1.z),
        Cube(c1.x, c1.y + 1, c1.z),
        Cube(c1.x, c1.y - 1, c1.z),
        Cube(c1.x, c1.y, c1.z + 1),
        Cube(c1.x, c1.y, c1.z - 1)
    )
}

data class Cube(val x: Int, val y: Int, val z: Int) {
    fun inRange(xR: IntRange, yR: IntRange, zR: IntRange): Boolean = this.x in xR && y in yR && z in zR
}
