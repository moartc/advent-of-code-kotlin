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


/**
 * Modular exponentiation: (base^exp) % mod
 * Useful for large number problems
 */
fun modPow(base: Long, exp: Long, mod: Long): Long {
    if (exp == 0L) return 1L
    var result = 1L
    var base = base % mod
    var exp = exp

    while (exp > 0) {
        if (exp % 2 == 1L) {
            result = (result * base) % mod
        }
        base = (base * base) % mod
        exp /= 2
    }
    return result
}

/**
 * Chinese Remainder Theorem for two congruences
 */
fun crt(a1: Long, m1: Long, a2: Long, m2: Long): Pair<Long, Long> {
    // Find x such that: x ≡ a1 (mod m1) and x ≡ a2 (mod m2)
    val (g, u, _) = extendedGcd(m1, m2)
    if ((a2 - a1) % g != 0L) error("No solution exists")

    val lcm = m1 * m2 / g
    var x = (a1 + m1 * u * ((a2 - a1) / g)) % lcm
    if (x < 0) x += lcm

    return x to lcm
}

/**
 * Extended Euclidean Algorithm
 * Returns (gcd, x, y) where ax + by = gcd(a,b)
 */
fun extendedGcd(a: Long, b: Long): Triple<Long, Long, Long> {
    if (b == 0L) return Triple(a, 1L, 0L)
    val (g, x1, y1) = extendedGcd(b, a % b)
    return Triple(g, y1, x1 - (a / b) * y1)
}

/**
 * Factorial with memoization
 */
private val factorialCache = mutableMapOf(0L to 1L, 1L to 1L)
fun factorial(n: Long): Long {
    return factorialCache.getOrPut(n) {
        n * factorial(n - 1)
    }
}

/**
 * nCr - Binomial coefficient (combinations)
 */
fun binomial(n: Long, r: Long): Long {
    if (r > n) return 0
    if (r == 0L || r == n) return 1

    // Use the smaller of r and n-r for efficiency
    val k = minOf(r, n - r)
    var result = 1L

    for (i in 0 until k) {
        result = result * (n - i) / (i + 1)
    }

    return result
}