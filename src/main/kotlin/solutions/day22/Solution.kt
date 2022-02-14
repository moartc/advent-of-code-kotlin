package solutions.day22

import utils.Resources

fun main() {
    val input = Resources.getLines(22)
    println("part1 = ${fight(Player(), getBoss(input), emptyList(), 0, true, 1, mutableListOf()).minOrNull()} ")
    println("part2 = ${fight(Player(), getBoss(input), emptyList(), 0, true, 2, mutableListOf()).minOrNull()}")
}

fun fight(
    player: Player,
    boss: Boss,
    spells: List<Spell>,
    cost: Int,
    isPlayerTurn: Boolean,
    part: Int,
    results: MutableList<Int>
): List<Int> {
    return if (cost > (results.minOrNull() ?: Integer.MAX_VALUE) || player.hit <= 0) {
        return results
    } else if (boss.hit <= 0) {
        if (cost < (results.minOrNull() ?: Integer.MAX_VALUE)) {
            results.add(cost)
        }
        return results
    } else if (isPlayerTurn) {
        val playerResults = playerTurn(player, boss, spells, cost, part)
        playerResults.flatMap { res -> fight(res.player, res.boss, res.spells, res.cost, false, part, results) }
    } else {
        val (playerAfterBossAttack, bossAfterSpells, updatedSpells) = bossTurn(player, boss, spells)
        fight(playerAfterBossAttack, bossAfterSpells, updatedSpells, cost, true, part, results)
    }
}

fun playerTurn(player: Player, boss: Boss, activeSpells: List<Spell>, cost: Int, part: Int): List<TurnResult> {
    val updatedPlayer = activeSpells.fold(if (part == 2) player.copy(hit = player.hit - 1) else player) { acc, spell ->
        acc.takeSpell(spell)
    }
    val updatedBoss = activeSpells.fold(boss) { acc, spell -> acc.takeSpell(spell) }
    val updatedSpells = activeSpells.map { i -> i.copy(timer = i.timer - 1) }.filter { it.timer != 0 }
    return getAllPossibleSpells(updatedPlayer, updatedSpells).map { newSpell ->
        val playerAfterSpell = updatedPlayer.copy(mana = updatedPlayer.mana - newSpell.cost)
        val newCost = cost + newSpell.cost
        if (newSpell.timer == -1) {
            TurnResult(playerAfterSpell.takeSpell(newSpell), updatedBoss.takeSpell(newSpell), updatedSpells, newCost)
        } else {
            TurnResult(playerAfterSpell, updatedBoss, updatedSpells + newSpell, newCost)
        }
    }
}

data class TurnResult(val player: Player, val boss: Boss, val spells: List<Spell>, val cost: Int)

fun bossTurn(player: Player, boss: Boss, activeSpells: List<Spell>): Triple<Player, Boss, List<Spell>> {
    val playerAfterSpells = activeSpells.fold(player) { acc, spell -> acc.takeSpell(spell) }
    val bossAfterSpells = activeSpells.fold(boss) { acc, spell -> acc.takeSpell(spell) }
    val updatedSpells = activeSpells.map { i -> i.copy(timer = i.timer - 1) }.filter { it.timer != 0 }
    val shield = activeSpells.firstOrNull { it.cost == 113 && it.timer > 0 }?.armor ?: 0
    val playerAfterBossAttack =
        playerAfterSpells.copy(hit = if (boss.damage - shield <= 1) playerAfterSpells.hit - 1 else playerAfterSpells.hit - (boss.damage - shield))
    return Triple(playerAfterBossAttack, bossAfterSpells, updatedSpells)
}

data class Player(val hit: Int = 50, val armor: Int = 0, val mana: Int = 500) {
    fun takeSpell(spell: Spell) = Player(hit + spell.heal, armor, mana + spell.mana)
}

data class Boss(val hit: Int, val damage: Int) {
    fun takeSpell(spell: Spell) = Boss(hit - spell.damage, damage)
}

fun getAllPossibleSpells(player: Player, currentlyActive: List<Spell>) =
    spells.filter { filtered -> currentlyActive.none { it.cost == filtered.cost && it.timer > 0 } && player.mana >= filtered.cost }

val spells = listOf(
    Spell(53, 4, 0, 0, 0, -1),
    Spell(73, 2, 2, 0, 0, -1),
    Spell(113, 0, 0, 7, 0, 6),
    Spell(173, 3, 0, 0, 0, 6),
    Spell(229, 0, 0, 0, 101, 5)
)

data class Spell(val cost: Int, val damage: Int, val heal: Int, val armor: Int, val mana: Int, val timer: Int)

fun getBoss(input: List<String>) = Boss(getValue(input, 0), getValue(input, 1))
fun getValue(input: List<String>, index: Int) = input[index].substringAfter(": ").toInt()