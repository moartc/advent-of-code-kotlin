package utils.algorithms


// returns all points visited from the starting point (y,x),
// you can pass external visited
fun <T> floodFill(grid: List<List<T>>, y: Int, x: Int, visited :  Array<BooleanArray> = Array(grid.size) { BooleanArray(grid[0].size) }): List<Pair<Int, Int>> {
    val valueToVisit = grid[y][x]
    val rows = grid.size
    val cols = grid[0].size
    val result = mutableListOf<Pair<Int, Int>>()

    fun isValid(row: Int, col: Int): Boolean {
        return row in 0 until rows &&
                col in 0 until cols &&
                 !visited[row][col] &&
                grid[row][col] == valueToVisit
    }
    fun dfs(row: Int, col: Int) {
        visited[row][col] = true
        result.add(Pair(row, col))
        for ((dr, dc) in withoutDiagonal) {
            val newRow = row + dr
            val newCol = col + dc
            if (isValid(newRow, newCol)) {
                dfs(newRow, newCol)
            }
        }
    }
    dfs(y, x)
    return result
}

// returns List T : List<Pair<Int, Int>>
// can't use Map because T value might occur multiple time
fun <T> floodAll(grid : List<List<T>>): MutableList<Pair<T, List<Pair<Int, Int>>>> {

    val results = mutableListOf<Pair<T, List<Pair<Int, Int>>>>()
    val visited = Array(grid.size) { BooleanArray(grid[0].size) }

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            val elem = grid[y][x]
            if (!visited[y][x]) {
                // not visited yet
                val floodedPoints = floodFill(grid, y ,x, visited)
                results.add(elem to floodedPoints)
            }
        }
    }
    return results

}