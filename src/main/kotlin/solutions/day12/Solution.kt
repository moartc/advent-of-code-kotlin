package solutions.day12


import utils.Resources

fun main() {
    val inputLine = Resources.getLine(12)
    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: String) = "[-]*\\d+".toRegex().findAll(input).map(MatchResult::value).map(String::toInt).sum()

fun part2(input: String) = part1(removeNodeWithRed(input, 0))

fun removeNodeWithRed(input: String, startIndex: Int): String {
    val index = input.indexOf("""":"red"""", startIndex)
    return if (index == -1) {
        input
    } else {
        val prevIndexOfSquare = findPrevIndex(input.substring(0, index + 1), index, '[', 0)
        val prevIndexOfCurly = findPrevIndex(input.substring(0, index + 1), index, '{', 0)
        if (prevIndexOfSquare in prevIndexOfCurly..index) {
            removeNodeWithRed(input, index)
        } else {
            removeNodeWithRed(input.removeRange(prevIndexOfCurly, getCloseBracketIndex(input, index, 0) + 1), 0)
        }
    }
}

fun getCloseBracketIndex(input: String, index: Int, ctr: Int): Int {
    return if (input[index] == '}') if (ctr == 0) index else getCloseBracketIndex(input, index + 1, ctr - 1)
    else getCloseBracketIndex(input, index + 1, if (input[index] == '{') ctr + 1 else ctr)
}

tailrec fun findPrevIndex(input: String, index: Int, bracket: Char, ctr: Int): Int {
    return if (index < 0) {
        -1
    } else if (input[index] == bracket) {
        if (ctr == 0) index else findPrevIndex(input, index - 1, bracket, ctr - 1)
    } else {
        findPrevIndex(input, index - 1, bracket, if (input[index] == getReverse(bracket)) ctr + 1 else ctr)
    }
}

fun getReverse(bracket: Char) = if (bracket == '[') ']' else '}'
