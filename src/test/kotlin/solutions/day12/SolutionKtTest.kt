package solutions.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class SolutionKtTest {

    @TestFactory
    fun generatorTest() = listOf(
        "[1,2,3]" to 6,
        """{"a":2,"b":4}""" to 6,
        "[[[3]]]" to 3,
        """{"a":{"b":4},"c":-1}""" to 3,
        "[]" to 0,
        "{}" to 0,
        "[11,22,33]" to 66,
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("part 1 answer for $input should be $expected") {
            assertEquals(expected, part1(input))
        }
    }

    @TestFactory
    fun generatorTest2() = listOf(
        "[1,2,3]" to 6,
        """[1,{"c":"red","b":2},3]""" to 4,
        """{"d":"red","e":[1,2,3,4],"f":5}""" to 0,
        """[1,"red",5]""" to 6,
        """{1,{"c":"red","b":2},3]}""" to 4,
        """{"c":"yellow","a":"orange","b":{"a":"violet","b":["orange","orange","violet",{"e":"red","a":"red","d":163,"c":153,"h":"green","b":6,"g":"blue","f":17,"i":63},163,[164,-41,"violet","violet",126]]},"d":-38}""" to 374,
        """{"e":86,"c":23,[][][[[]]]"a":,"g":"yellow",{}{}{{{}}}"b":["yellow"],{"d":"red"},"f":-19}""" to 90,
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("part 2 answer for $input should be $expected") {
            assertEquals(expected, part2(input))
        }
    }
}