package utils.algorithms

fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b // % is remainder
        a = temp
    }
    return a
}

fun gcd(input: LongArray): Long {
    var result = input[0]
    for (i in 1 until input.size) result = gcd(result, input[i])
    return result
}

fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

fun lcm(input: LongArray): Long {
    var result = input[0]
    for (i in 1 until input.size) result = lcm(result, input[i])
    return result
}