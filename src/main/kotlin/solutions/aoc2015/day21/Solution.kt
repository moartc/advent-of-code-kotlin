package solutions.aoc2015.day21

import utils.Resources

fun main() {
    val input = Resources.getLines(2015, 21)
    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")
}

fun part1(input: List<String>) = getAnswer(input, "player", Integer.MAX_VALUE) { x, y -> x < y }
fun part2(input: List<String>) = getAnswer(input, "boss", Integer.MIN_VALUE) { x, y -> x > y }

fun getAnswer(input: List<String>, win: String, init: Int, predicate: (x: Int, y: Int) -> Boolean): Int {
    return weapons.flatMap { weapon ->
        armors.flatMap { armor ->
            rings.flatMapIndexed { rIdx1, ring1 ->
                rings.filterIndexed { rIdx2, _ -> !(rIdx1 != 0 && rIdx1 == rIdx2) }
                    .map { ring2 ->
                        val totalCost = weapon.cost + armor.cost + ring1.cost + ring2.cost
                        Player("player", 100, weapon, armor, ring1, ring2) to totalCost
                    }
            }
        }
    }.fold(init) { acc, pair ->
        if (fight(pair.first, getBoss(input)).name == win && predicate(pair.second, acc)) pair.second else acc
    }
}

fun fight(player: Player, boss: Player): Player {
    return if (boss.hit <= 0) player
    else if (player.hit <= 0) boss
    else fight(player.getHit(boss.damage), boss.getHit(player.damage))
}

class Player(val name: String, val hit: Int, val damage: Int, val armor: Int) {
    constructor(name: String, hit: Int, weapon: Item, armor: Item, ring1: Item, ring2: Item) :
            this(name, hit, weapon.damage + ring1.damage + ring2.damage, armor.armor + ring1.armor + ring2.armor)

    fun getHit(damage: Int) = Player(name, hit - (damage - armor), this.damage, armor)
}

data class Item(val cost: Int, val damage: Int, val armor: Int)

val weapons = listOf(Item(8, 4, 0), Item(10, 5, 0), Item(25, 6, 0), Item(40, 7, 0), Item(74, 8, 0))
val armors = listOf(Item(0, 0, 0), Item(13, 0, 1), Item(31, 0, 2), Item(53, 0, 3), Item(75, 0, 4), Item(102, 0, 5))
val rings = listOf(
    Item(0, 0, 0),
    Item(25, 1, 0),
    Item(50, 2, 0),
    Item(100, 3, 0),
    Item(20, 0, 1),
    Item(40, 0, 2),
    Item(80, 0, 3)
)

fun getBoss(input: List<String>) = Player("boss", getValue(input, 0), getValue(input, 1), getValue(input, 2))
fun getValue(input: List<String>, index: Int) = input[index].substringAfter(": ").toInt()