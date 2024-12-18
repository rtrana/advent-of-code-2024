import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText


class AOCDay15 {

    var gridLayout: MutableList<MutableList<Char>> = mutableListOf()
    var gridState: MutableList<MutableList<Char>> = mutableListOf()
    var origin: MutableList<Int> = mutableListOf()

    fun initializeGrids(input: List<String>) {
        gridLayout = input.map { line -> line.map { char -> char }.toMutableList() }.toMutableList()
        gridState = input.map { line -> line.map { char -> char }.toMutableList() }.toMutableList()
        for (i in input.indices) {
            var index = input[i].indexOf('@')
            if (index != -1) {
                origin.add(i)
                origin.add(index)
            }
        }
    }

    fun moveRobot(moves: String) {
        var currentPosition = origin.toMutableList()
        for (move in moves) {
            if (move == '<' || move == '>')
                currentPosition = moveHorizontallyIfPossible(currentPosition[0], currentPosition[1], move)
            else
                currentPosition = moveVerticallyIfPossible(currentPosition[0], currentPosition[1], move)
        }
    }

    fun calculateGPSCoordinates(): Long {
        var sum = 0L
        for (i in gridState.indices) {
            for (j in gridState[i].indices) {
                if (gridState[i][j] == 'O') {
                    sum += 100 * i.toLong() + j.toLong()
                }
            }
        }
        return sum
    }

    fun moveHorizontallyIfPossible(i: Int, j: Int, move: Char): MutableList<Int> {
        var freeSpot = -1
        var movedSpot = mutableListOf(i, j)
        if (move == '>') {
            var foundSpace = false
            var col = j + 1
            while (col < this.gridLayout[i].size && gridLayout[i][col] != '#' && !foundSpace) {
                if (gridState[i][col] == '.') {
                    foundSpace = true
                    freeSpot = col
                }
                col++
            }
            if (foundSpace) {
                gridState[i][j] = '.'
                gridState[i][j + 1] = '@'
                movedSpot = mutableListOf(i, j + 1)
                for (col in (j + 2) .. freeSpot)
                    gridState[i][col] = 'O'
            }
        }
        if (move == '<') {
            var foundSpace = false
            var col = j - 1
            while (col >= 0 && gridLayout[i][col] != '#' && !foundSpace) {
                if (gridState[i][col] == '.') {
                    freeSpot = col
                    foundSpace = true
                }
                col--
            }
            if (foundSpace) {
                gridState[i][j] = '.'
                gridState[i][j - 1] = '@'
                movedSpot = mutableListOf(i, j - 1)
                for (col in (j - 2) downTo freeSpot)
                    gridState[i][col] = 'O'
            }
        }
        return movedSpot
    }

    fun moveVerticallyIfPossible(i: Int, j: Int, move: Char): MutableList<Int> {
        var freeSpot = -1
        var movedSpot = mutableListOf(i, j)
        if (move == 'v') {
            var foundSpace = false
            var row = i + 1
            while (row < this.gridLayout.size && gridLayout[row][j] != '#' && !foundSpace) {
                if (gridState[row][j] == '.') {
                    freeSpot = row
                    foundSpace = true
                }
                row++
            }
            if (foundSpace) {
                gridState[i][j] = '.'
                movedSpot = mutableListOf(i + 1, j)
                gridState[i + 1][j] = '@'
                for (row in (i + 2) .. freeSpot)
                    gridState[row][j] = 'O'

            }
        }
        if (move == '^') {
            var foundSpace = false
            var row = i - 1
            while (row >= 0 && gridLayout[row][j] != '#' && !foundSpace) {
                if (gridState[row][j] == '.') {
                    foundSpace = true
                    freeSpot = row
                }
                row--
            }
            if (foundSpace) {
                gridState[i][j] = '.'
                gridState[i - 1][j] = '@'
                movedSpot = mutableListOf(i - 1, j)
                for (row in (i - 2) downTo freeSpot)
                    gridState[row][j] = 'O'
            }
        }

        return movedSpot
    }

}

fun main() {
    var fileInputGrid = Path("src/main/resources/inputDay15-grid.txt").readLines()
    var fileInputMoves = Path("src/main/resources/inputDay15-moves.txt").readText()
    var aoc = AOCDay15()
    aoc.initializeGrids(fileInputGrid)
    aoc.moveRobot(fileInputMoves)
    aoc.gridState.forEach {
        println(it)
    }
    println(aoc.calculateGPSCoordinates())
}