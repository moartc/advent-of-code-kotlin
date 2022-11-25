package solutions.aoc2015.day25

import utils.Resources

fun main() {
    val input = getRowAndColumn(Resources.getLine(2015, 25))
    println("final part = " + getCode(input[0].toInt(), input[1].toInt()))
}

fun getCode(row: Int, col: Int) = generateSequence(20151125L) { (it * 252533L) % 33554393L }.take(getNumber(row, col)).last()
fun getNumber(row: Int, col: Int) = ((1 until row + col).sum() - row + 1)
fun getRowAndColumn(input: String) = Regex(""".*row (\d+), column (\d+)""").find(input)!!.groupValues.takeLast(2)