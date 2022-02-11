package solutions.day21

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SolutionKtTest {

    @Test
    fun fightTest() {
        val player = Player("player", 8, 5, 5)
        val boss = Player("boss", 12, 7, 2)
        assertEquals("player", fight(player, boss).name)
    }
}