package utils.math

import kotlin.math.pow
import kotlin.math.sqrt

fun calculateDistance(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Double {
    return sqrt((p2.first - p1.first).toDouble().pow(2.0) + (p2.second - p1.second).toDouble().pow(2.0))
}

fun checkRandomness(points: List<Pair<Int, Int>>, expectedDistance: Double): Boolean {
    var closePointsCount = 0
    val totalPoints = points.size

    for (i in 0 until totalPoints) {
        for (j in i + 1 until totalPoints) {
            val distance = calculateDistance(points[i], points[j])
            if (distance < expectedDistance) {
                closePointsCount++
            }
        }
    }
    val percentageClose = (closePointsCount.toDouble() / (totalPoints * (totalPoints - 1) / 2)) * 100

    return percentageClose > 0.5  // more than 50% is close (what rather says they are not random in this case)
}