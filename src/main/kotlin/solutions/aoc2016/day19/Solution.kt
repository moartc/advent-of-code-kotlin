package solutions.aoc2016.day19

import utils.Resources
import java.util.*

val day = (object {}).javaClass.packageName.takeLast(2).toInt()

fun main() {

    val inputLines = Resources.getLines(2016, day)

    println("part1 = ${part1(inputLines)}")
    println("part2 = ${part2(inputLines)}")
}


fun part2(inputLines: List<String>): Int {

    val listSize = inputLines[0].toInt()

    val list1 = LinkedList<Int>()
    val list2 = LinkedList<Int>()

    repeat(listSize) {
        if (it < listSize / 2) {
            list1.add(it)
        } else {
            list2.add(it)
        }
    }

    var round = 0

    while (list1.size + list2.size > 1) {
        round++
        val first = list1.pollFirst()
        list2.removeFirst()
        list2.addLast(first)
        if (round == 2) {
            round = 0
            val toMove = list2.pollFirst()
            list1.addLast(toMove)
        }
    }
    // after one round the winning Elf from l1 is moved at the end of l2
    return list2.first() + 1

}

fun part1(inputLines: List<String>): Int {

    val listSize = inputLines[0].toInt()
    val list = LinkedList(MutableList(listSize) { it }) // looks like LinkedList is really fast here
    var remove = false
    while (list.size > 1) {
        val iter = list.iterator()
        while (iter.hasNext()) {
            iter.next()
            if (remove) {
                iter.remove()
            }
            remove = !remove
        }
    }
    return list.first() + 1
}

/*
Part 2

    example for 6 elements:
    round 1
        0
    5       1
    4       2
        3
    # 0 removes 3
    ---------------------
    Round 2:
       0
    5      1
      4  2
    # 1 removes 4
    ---------------------
    Round 3:
       0
    5     1
       2
    # 2 -> removes 0
   ---------------------
   Round 4
      1
   5    2
   # 5 removes 1
   ---------------------
   5.
   5  2
   2 removes 5 -> 2 wins
   ---------------------

   Algorithm (example for 6 since my input has an even number of elements):
   Initial list
   l1: [0, 1, 2]
   l2: [3, 4, 5]

   # 0 removes 3
   first from l1 removes first from l2
   l1: [0, 1, 2]
   l2: [4, 5]
   first from l1 (0) goes at the end of l2
   l1: [1, 2]
   l2: [4, 5, 0]

   # 1 removes 4
   first from l1 removes first from l2, and goes at the end of l2
   l1: [2]
   l2: [5, 0, 1]
   after 2 rounds, move first from l2 to the end of l1
   l1: [2, 5]
   l2: [0, 1]

   # 2 removes 0
   first from l1 removes first from l2, and goes at the end of l2
   l1: [5]
   l2: [1, 2]

   # 5 removes 1
   l1: []
   l2: [2, 5]
   again, after 2 rounds move first from l2 to the beginning of l1
   l1: [2]
   l2: [5]

   # 2 removes 5
   first from l1 removes first from l2, and goes at the end of l2
   l1: []
   l2: [2]
   */