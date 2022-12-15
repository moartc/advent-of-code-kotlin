package solutions.aoc2022.day15

import utils.Resources
import utils.allPointsBetweenIntPairs
import utils.getInts
import java.lang.Math.*

fun main() {
    val input = Resources.getLines(2022, 15)

//    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}


fun part2(input: List<String>): Int {
    val map = input.map { it.getInts() }.map { (it[0] to it[1]) to (it[2] to it[3]) }

    var result = mutableListOf<MutableSet<Pair<Int, Int>>>()

    val min = minOf(map.minOf { it.first.first }, map.minOf { it.second.first })
    val max = maxOf(map.maxOf { it.first.first }, map.maxOf { it.second.first })

    min.log("min = ")
    max.log("max = ")

    val maxDist = map.map { distance(it.first, it.second) }.max() + 12
    maxDist.log("maxDist = ")

//    for(x in (0..20)) {
//        for(y in (0..20)) {
//            var dist = x * 4000000 + y
//            dist.log("dist = ")
//        }
//    }
    val maxx = 4000000


    val pointToDist = map.map { it.first to distance(it.first, it.second) }

    pointToDist.log("ptd = ")
    for (y in 0..maxx) {
        println("y = $y")
        for (x in (0..maxx)) {
            if (!pointToDist.any { p -> distance(x to y, p.first) <= p.second })
                println("found y = $y x = $x")
        }
    }
    return 12
}

//fun part1(input: List<String>): Int {
//
//
//    val map = input.map { it.getInts() }.map { (it[0].toLong() to it[1].toLong()) to (it[2].toLong() to it[3].toLong()) }
//
//    var result = mutableListOf<MutableSet<Pair<Long, Long>>>()
//
//    val min = minOf(map.minOf { it.first.first.toLong() }, map.minOf { it.second.first.toLong() })
//    val max = maxOf(map.maxOf { it.first.first.toLong() }, map.maxOf { it.second.first.toLong() })
//
//    min.log("min = ")
//    max.log("max = ")
//
//    val maxDist = map.map { distance(it.first, it.second) }.max() + 12
//    maxDist.log("maxDist = ")
//
//    var pointToCheck = 2000000L// 10L
//    for (pair in map) {
//        var set = mutableSetOf<Pair<Long, Long>>()
////        pair.log("pair = ")
//        val dist = distance(pair.first, pair.second)
//        dist.log("foundDist = ")
//
//        for (x in min - maxDist..max + maxDist) {
//            var p = x to pointToCheck
//            val dist2 = distance(pair.first, p)
////            dist2.log("dist2 = ")
//            if (dist2 <= dist) {
//                set.add(p)
//            }
//        }
//        if (pair.second.second == pointToCheck) {
//            set.remove(pair.second)
//        }
//
//
////        set.log("set = ")
//        result.add(set)
////        println("---------------")
//    }
//
//    val rr = result.flatMap { it }.toMutableSet()//.log("result set = ")
//
//    map.forEach { rr.remove(it.first) }
//
//    rr.map { it.first }.sorted()//.log("sort = ")
//    rr.size.log("size = ")
//
//    result//.log("result = ")
//// 4190019 not right
//// 4290019 not right
//    return 12
//}

// 4190019 not right


fun distance(p0: Pair<Int, Int>, p1: Pair<Int, Int>): Int = (abs(p0.first - p1.first) + abs(p1.second - p0.second))

fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }


/*

Sensor at x=3482210, y=422224: closest beacon is at x=2273934, y=-202439
Sensor at x=3679395, y=2737332: closest beacon is at x=4104213, y=2980736
Sensor at x=3173475, y=3948494: closest beacon is at x=3494250, y=3554521
Sensor at x=27235, y=3642190: closest beacon is at x=-190885, y=3635525
Sensor at x=3851721, y=1754784: closest beacon is at x=3145586, y=2167751
Sensor at x=327074, y=3250656: closest beacon is at x=-190885, y=3635525
Sensor at x=3499970, y=3186179: closest beacon is at x=3494250, y=3554521
Sensor at x=150736, y=2522778: closest beacon is at x=-85806, y=2000000
Sensor at x=3000768, y=3333983: closest beacon is at x=2564067, y=3163630
Sensor at x=1751302, y=1660540: closest beacon is at x=3145586, y=2167751
Sensor at x=2591068, y=2923079: closest beacon is at x=2564067, y=3163630
Sensor at x=48946, y=3999178: closest beacon is at x=-190885, y=3635525
Sensor at x=3695475, y=3863101: closest beacon is at x=3494250, y=3554521
Sensor at x=1504031, y=2760: closest beacon is at x=2273934, y=-202439
Sensor at x=3021186, y=2667125: closest beacon is at x=3145586, y=2167751
Sensor at x=1514629, y=3771171: closest beacon is at x=2564067, y=3163630
Sensor at x=234064, y=616106: closest beacon is at x=-85806, y=2000000
Sensor at x=3990843, y=3393575: closest beacon is at x=4104213, y=2980736
Sensor at x=768875, y=2665271: closest beacon is at x=-85806, y=2000000
 */