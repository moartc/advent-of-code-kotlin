package solutions.aoc2022.day20

import utils.Resources
import kotlin.math.abs

fun main() {
    val input = Resources.getLines(2022, 20)

    println("part1 = ${part1(input)}")
//    println("part2 = ${part2(input)}")
}

fun part2(input: List<String>): Long {

    val initial = input.map { it.toLong()*811589153 }


    val myList = (0..initial.size).zip(initial).map {
        MyPair(it.first.toLong(), it.second.toLong())
    }.toMutableList()


    initial.size.log("size = ")
    initial.toSet().size.log("set size = ")


    var updated = myList
    repeat(10) {
        repeat(initial.size) {
            updated = update(updated, it)
//            "update".log("upd ")
        }
        updated.sortedBy { it.first }.map { it.second }.log("vals = ")

    }

    updated.log("end = ")

    val val0Idx = updated.find { it.second == 0L }!!.first
    val idx1000 = (val0Idx + 1000) % initial.size
    val idx2000 = (val0Idx + 2000)% initial.size
    val idx3000 = (val0Idx + 3000)% initial.size
    val v1 = updated.find { it.first == idx1000 }!!.second
    val v2 = updated.find { it.first == idx2000 }!!.second
    val v3 = updated.find { it.first == idx3000 }!!.second
    return v1 + v2 + v3
}
//
fun part1(input: List<String>): Long {

    val initial = input.map { it.toInt() }


    val myList = (0..initial.size).zip(initial).map {
        MyPair(it.first.toLong(), it.second.toLong())
    }.toMutableList()


    initial.size.log("size = ")
    initial.toSet().size.log("set size = ")


    var updated = myList
    repeat(initial.size) {
        updated = update(updated, it)
//        updated.log("after = ")
        updated.sortedBy { it.first }.map { it.second }.log("vals = ")
    }
    updated.log("end = ")

    val val0Idx = updated.find { it.second == 0L }!!.first
    val idx1000 = (val0Idx + 1000) % initial.size
    val idx2000 = (val0Idx + 2000)% initial.size
    val idx3000 = (val0Idx + 3000)% initial.size
    val v1 = updated.find { it.first == idx1000 }!!.second
    val v2 = updated.find { it.first == idx2000 }!!.second
    val v3 = updated.find { it.first == idx3000 }!!.second
    return v1 + v2 + v3
}
////-18172 wrong
//// too high 4206
////if (this < 0) (this % size + size) % size
////        else this % size
fun update(list: MutableList<MyPair>, idx: Int): MutableList<MyPair> {

    var el = list[idx]
    val prev = el.first
    val moveSize = el.second % list.size
    val move = if(el.second > 0) getNextPositive(el.first, moveSize.toInt(), list.size.toLong()) else getNextNegative(el.first, moveSize.toInt()+1, list.size.toLong())
    val after = move

    if(prev > after) {
        val filtered = list.filter { it.first < prev && it.first >= after }//.log("filtered = ")
        filtered.forEach {
//            it.log("want to update = ")
            it.updateRight(el.second, list.size.toLong())
        }
    } else {
        val filtered = list.filter { it.first > prev && it.first <= after }//.log("filtered = ")
        filtered.forEach {
//            it.log("want to update = ")
            it.updateLeft(el.second, list.size.toLong())
        }
    }

    el.first = after

    return list

}

fun getNextPositive(current : Long, move : Int, size : Long): Long {
    var ret = current
    repeat(move) {
        if(ret+1 == size) {
            ret = 1
        } else {
            ret = ret+1
        }
    }
    return ret
}
fun getNextNegative(current : Long, move : Int, size : Long): Long {
    var ret = current
    repeat(abs(move)) {
        if(ret == 0L) {
            ret = size-2
        } else if(ret == 1L) {
            ret = size - 1
        }else {
            ret = ret-1
        }
    }
    return ret
}
fun mod_floor(a: Long, n: Long): Long {
    return (a % n + n) % n
}
class MyPair(public var first: Long, public var second: Long) {
    public fun updateLeft(v: Long, size : Long) {
        this.first = mod_floor(first - 1, size)
    }
    public fun updateRight(v: Long, size : Long) {
        this.first = mod_floor(first + 1, size)
    }

    fun list() {
        this.second
    }

    override fun toString(): String {

        return "($first, $second)"

    }
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }