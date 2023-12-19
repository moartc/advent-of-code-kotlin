package utils.algorithms


fun isPointInPolygon(testPoint: Pair<Int, Int>, polygon: List<Pair<Int, Int>>): Boolean {
    var result = false;
    var j = polygon.size - 1;
    for (i in polygon.indices) {
        if (polygon[i].first < testPoint.first && polygon[j].first >= testPoint.first || polygon[j].first < testPoint.first && polygon[i].first >= testPoint.first) {
            if (polygon[i].second + (testPoint.first - polygon[i].first) / (polygon[j].first - polygon[i].first) * (polygon[j].second - polygon[i].second) < testPoint.second) {
                result = !result;
            }
        }
        j = i
    }
    return result;
}