package solutions.aoc2023.day12

import utils.Resources

fun main() {

    val inputLine = Resources.getLines(2023, 12)

    println("part1 = ${part1(inputLine)}")
    println("part2 = ${part2(inputLine)}")
}

fun part1(input: List<String>): Long {

    return input.sumOf {
        val split1 = it.split(" ")
        val left = split1[0]
        var nums = split1[1].split(",").map { it.toInt() }.toMutableList()
        if (left.first() == '#') {
            processHash(left.substring(1), nums, 0, 1)
        } else {
            processDefault(left, nums, 0)
        }
    }
}

fun part2(input: List<String>): Long {
    return input.sumOf {
        val split1 = it.split(" ")
        val left = split1[0]
        var nums = split1[1].split(",").map { it.toInt() }.toMutableList()
        val left2 = "$left?$left?$left?$left?$left"
        val nums2 = nums + nums + nums + nums + nums
        if (left2.first() == '#') {
            processHash(left2.substring(1), nums2, 0, 1)
        } else {
            processDefault(left2, nums2, 0)
        }
    }
}

fun processHash(current: String, nums: List<Int>, result: Long, currentHashCount: Int): Long {

    if (nums.isEmpty()) { // there is a hash sequence but should not
        return 0
    }
    if (current == "") {
        if (currentHashCount == nums.first() && nums.size == 1) { // it's end, hash length fits and it's the last remaining group
            return 1
        } else {
            return 0
        }
    }

    // just a single hash
    if (nums.first() == currentHashCount) {
        if (current.length == 1) {
            if (nums.size == 1 && current[0] != '#') {
                return 1
            } else {
                return 0
            }
        } else if (current.length >= 2) { // hash, we can finish if the next one will be dot, so it should be either . or ?
            if (current[0] == '.' || current[0] == '?') {
                return processDefault(current.drop(1), nums.drop(1), result)
            } else {
                // otherwise it means that the next one is #, so it's 0
                return 0
            }
        }
    }

    val currC = current.first()
    if (currC == '#') {
        return processHash(current.drop(1), nums, result, currentHashCount + 1)
    } else if (currC == '.') { // we finish searching on dot
        if (currentHashCount == nums[0]) {
            return processDefault(current.drop(1), nums.drop(1), result)
        } else {
            // we finished group incorrectly
            return 0
        }
    } else { // it's ?
        if (currentHashCount == nums.first()) { // we can finish with .
            if (current.length == 1) {
                if (nums.size == 1) {
                    return 1
                } else {
                    return 0
                }
            } else if (current.length >= 2) { // hash, we can finish if the next one will be dot, so it should be either . or ?
                if (current.first() == '.' || current.first() == '?') {
                    return processDefault(current.drop(1), nums.drop(1), result)
                } else {
                    // otherwise it means that the next one is #
                    return 0
                }
            }
        } else { // it's smaller so do hash
            return processHash(current.drop(1), nums, result, currentHashCount + 1)
        }
    }
    return 0
}

// cache for part 2
val cache = mutableMapOf<Pair<String, List<Int>>, Long>()
tailrec fun processDefault(current: String, nums: List<Int>, result: Long): Long {

    // last char
    if (current == "") {
        if (nums.isEmpty()) {
            return 1
        } else {
            return 0
        }
    }

    val keyToCache = Pair(current, nums)
    val currC = current.first()
    if (currC == '.') {
        if (cache.containsKey(keyToCache)) {
            return cache[keyToCache]!!
        } else {
            val processDefault = processDefault(current.drop(1), nums, result)
            cache[keyToCache] = processDefault
            return processDefault
        }
    } else if (currC == '#') {
        if (cache.containsKey(keyToCache)) {
            return cache[keyToCache]!!
        } else {
            val processDefault = processHash(current.drop(1), nums, result, 1)
            cache[keyToCache] = processDefault
            return processDefault
        }
    } else { // currC == ?
        if (cache.containsKey(keyToCache)) {
            return cache[keyToCache]!!
        } else {
            var toRet = processDefault(current.drop(1), nums, result) // version for dot
            toRet += processHash(current.drop(1), nums, result, 1)
            cache[keyToCache] = toRet
            return toRet
        }
    }
}
