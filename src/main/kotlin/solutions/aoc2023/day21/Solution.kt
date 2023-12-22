package solutions.aoc2023.day21

import utils.Resources
import utils.algorithms.withoutDiagonal

fun main() {

    val inputLine =
        Resources.getLines(2023, 21)
//        Resources.getLinesExample(2023, 21)
//    println("part1 = ${part1(inputLine)}")
    println("part1 = ${part2(inputLine)}")
}


fun part2(input: List<String>): Int {




    fun locBfs(
        grid: List<List<Char>>,
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Pair<Int, Int>, Pair<Int, Int>>,
    ): Int {


        var step = 0

        fun mapMove(curr: Pair<Pair<Int, Int>, Pair<Int, Int>>, move: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
            val possibleY = (curr.first.first + move.first) % grid.size
            val possibleX = (curr.first.second + move.second) % grid[0].size
            val nextX = if (possibleX == -1) grid[0].lastIndex else possibleX
            val nextY = if (possibleY == -1) grid.lastIndex else possibleY
            val inGrid = nextY to nextX
            val realPosY = (curr.second.first + move.first)
            val realPosX = (curr.second.second + move.second)

            return inGrid to (realPosY to realPosX)
        }

        fun getNext(current: Pair<Pair<Int, Int>, Pair<Int, Int>>) = possibleMoves
            .map { mapMove(current, it) }
            .filter { n -> grid[n.first.first][n.first.second] in ".S" }


        fun totStepsInFirstGrid(current: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
            return current.count { p -> p.second.first in (0..130) && p.second.second in 0..130 }
        }

        fun totStepsInRightGrid(current: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
            return current.count { p -> p.second.first in (0..130) && p.second.second in 131..262 }
        }
        fun totStepsInRightGrid2(current: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
            return current.count { p -> p.second.first in (0..130) && p.second.second in 263..394 }
        }
        fun totStepsInRightGrid4(current: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
            return current.count { p -> p.second.first in (0..130) && p.second.second in 395..526 }

        }

        // in grid to full ss
        var queue = LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val listDiff = mutableListOf<Int>()
        var prevPl = 0

        var diffUneven = 0
        var add = 0
        var skip = 0

        var prevDiff = 0
        val evenPoints = LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val oddPoints = LinkedHashSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        var prevDiffDiff = 0
        evenPoints.add(start)
        while (true) {
//            queue.forEach {it.first.log()}
//            println("---------------")

//            if (step % 2 == 1) {

//            }

            if (step % 2 == 0) { // is even
                queue = evenPoints
            } else {
                queue = oddPoints
            }


            queue.forEach { s ->
                val nextMoves = getNext(s)
                nextMoves.forEach { m ->
                    if (step % 2 == 0) { // is even step so I can add to the next list which is odd
                        if (!oddPoints.contains(m)) {
                            oddPoints.add(m)
                        }
                    } else {
                        if (!evenPoints.contains(m)) {
                            evenPoints.add(m)
                        }
                    }
                }
            }
//            if(step % 131 ) {

            if(step % grid.size== 65) {

                println("after ${String.format("%4d", step)} steps it is in ${queue.map { it.first }.toSet().size}")
                println("unique ${String.format("%4d", step)} steps it is in ${queue.map { it.second }.toSet().size}")
                val currPl = queue.map { it.second }.size
                var diff = currPl - prevPl
                println("with ${currPl} places and diff = ${diff}")
                println("diff diff = ${diff-prevDiff}")
                prevPl = currPl
                if(prevDiffDiff == diff-prevDiff) {

                    var valStart = queue.map { it.first }.toSet().size
                    var it = step

                    while(it <= 26501365) {
                        it.log(">>>it and next ${it+step} is eq? ${it+step == 26501365}")
                        it += grid.size
                        valStart += diff
                    }
                    valStart.log("val start")
                    return valStart
                }
                prevDiffDiff = diff-prevDiff
                prevDiff = diff

//                println("tot steps 1st: ${totStepsInFirstGrid(queue.toList())}")
//                println("tot steps 2nd: ${totStepsInRightGrid(queue.toList())}")
//                println("tot steps 3rd: ${totStepsInRightGrid2(queue.toList())}")
//                println("tot steps 4th: ${totStepsInRightGrid4(queue.toList())}")
                println("----------------------")
            }

            /*
            after   65 steps it is in 3877
            unique   65 steps it is in 3877
            with 3877 places and diff = 3877

            after  196 steps it is in 15344
            unique  196 steps it is in 34674
            with 34674 places and diff = 30797

            after  327 steps it is in 15344
            unique  327 steps it is in 96159
            with 96159 places and diff = 61485

            after  458 steps it is in 15344
            unique  458 steps it is in 188332
            with 188332 places and diff = 92173 / 2 with diff 3986

-------------------------


             */


            if (step == 1000) {
                if (step % 2 == 0) {
                    return evenPoints.size
                } else {
                    return oddPoints.size
                }
            }



            step++

        }
        return -1
    }
    /*
    tot steps 1st: 7656 - stable after 129 steps - tot 15105 | *4 diff 922
    diff
    tot steps 2nd: 7753 - stable after 261 steps - to  61342  | *2 diff 16085
    tot steps 3rd: 7715 - stable after 393 steps - tot 138769
    tot steps 4th: 7744 - stable after 525 steps - tot 247364  | * 2 diff −30174

    examples:
    1594        mod 131 = 22
    6536        mod 131 = 117
    167004      mod 131 = 110
    668697      mod 131 = 73

     */
    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }

    val locBfs = locBfs(map, withoutDiagonal, start to start)
    locBfs.log("visited with size")


    return 12
}

fun part1(input: List<String>): Int {


    fun locBfs(
        grid: List<List<Char>>,
        possibleMoves: Array<Pair<Int, Int>>,
        start: Pair<Int, Int>,
    ): Set<Pair<Int, Int>> {

        val sizeY = grid.size - 1
        val sizeX = grid[0].size - 1

        var step = 0

        fun isPosValid(nextPos: Pair<Int, Int>): Boolean {
            return !(nextPos.first < 0 || nextPos.first > sizeY || nextPos.second < 0 || nextPos.second > sizeX)
        }

        fun getNext(current: Pair<Int, Int>) = possibleMoves
            .map { move -> current.first + move.first to current.second + move.second }
            .filter { n -> isPosValid(n) && grid[n.first][n.second] in ".S" }


        var queue = LinkedHashSet<Pair<Int, Int>>()
        queue.add(start)
        while (!queue.isEmpty()) {
            if (step == 64) {
                return queue.toSet()
            }
            step++

            val newQueue = LinkedHashSet<Pair<Int, Int>>()
            queue.forEach { s ->
                val nextMoves = getNext(s)
                newQueue.addAll(nextMoves)
            }
            queue = newQueue
        }
        return emptySet()

    }

    val map = input.map { it.toList() }
    val start = input.indices.flatMap { y -> input.indices.map { x -> y to x } }.first { p -> map[p.first][p.second] == 'S' }

    val locBfs = locBfs(map, withoutDiagonal, start)
    locBfs.size.log("visited with size")


    return 12
}

private fun <T> T.log(): T = also { println("%s".format(this)) }
private fun <T> T.log(comment: String): T = also { println("%s: %s".format(comment, this)) }




