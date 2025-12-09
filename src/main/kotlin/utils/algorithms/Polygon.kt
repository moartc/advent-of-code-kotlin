package utils.algorithms

@JvmName("isPointInPolygonInt")
fun isPointInPolygon(testPoint: Pair<Int, Int>, polygon: List<Pair<Int, Int>>): Boolean {
    var result = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        if ((polygon[i].first < testPoint.first && polygon[j].first >= testPoint.first) ||
            (polygon[j].first < testPoint.first && polygon[i].first >= testPoint.first)
        ) {
            if (polygon[i].second + (testPoint.first - polygon[i].first) * (polygon[j].second - polygon[i].second) / (polygon[j].first - polygon[i].first) < testPoint.second) {
                result = !result
            }
        }
        j = i
    }
    return result
}

@JvmName("isPointInPolygonLong")
fun isPointInPolygon(testPoint: Pair<Long, Long>, polygon: List<Pair<Long, Long>>): Boolean {
    var result = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        if ((polygon[i].first < testPoint.first && polygon[j].first >= testPoint.first) ||
            (polygon[j].first < testPoint.first && polygon[i].first >= testPoint.first)
        ) {
            if (polygon[i].second + (testPoint.first - polygon[i].first) * (polygon[j].second - polygon[i].second) / (polygon[j].first - polygon[i].first) < testPoint.second) {
                result = !result
            }
        }
        j = i
    }
    return result
}


fun getEdgePoints(corners: List<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    return corners.indices.flatMapTo(mutableSetOf()) { i ->
        val start = corners[i]
        val end = corners[(i + 1) % corners.size]
        getLinePoints(start, end)
    }
}

private fun getLinePoints(start: Pair<Long, Long>, end: Pair<Long, Long>): List<Pair<Long, Long>> {
    return when {
        start.first == end.first -> {
            val x = start.first
            val yRange = minOf(start.second, end.second)..maxOf(start.second, end.second)
            yRange.map { y -> x to y }
        }

        start.second == end.second -> {
            val y = start.second
            val xRange = minOf(start.first, end.first)..maxOf(start.first, end.first)
            xRange.map { x -> x to y }
        }

        else -> emptyList() // Non-axis-aligned edges
    }
}