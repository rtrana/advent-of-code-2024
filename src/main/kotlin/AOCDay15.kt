import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.max
import kotlin.math.min


class AOCDay15 {

    var gridLayout: MutableList<MutableList<Char>> = mutableListOf()
    var gridState: MutableList<MutableList<Char>> = mutableListOf()
    var gridStateCopy: MutableList<MutableList<Char>> = mutableListOf()
    var origin: MutableList<Int> = mutableListOf()

    fun initializeGrids(input: List<String>, scaled: Boolean = false) {
        gridLayout = input.map { line -> line.map { char -> char }.toMutableList() }.toMutableList()
        if (!scaled) {
            gridState = input.map { line -> line.map { char -> char }.toMutableList() }.toMutableList()
        } else {
            for (line in gridLayout) {
                var scaledLine = mutableListOf<Char>()
                for (symbol in line) {
                    if (symbol == 'O')
                        scaledLine.addAll(listOf('[', ']'))
                    else if (symbol == '@')
                        scaledLine.addAll(listOf('@', '.'))
                    else
                        scaledLine.addAll(listOf(symbol, symbol))
                }
                gridState.add(scaledLine)
            }
        }
        for (i in gridState.indices) {
            var index = gridState[i].indexOf('@')
            if (index != -1) {
                origin.add(i)
                origin.add(index)
            }
        }
        gridStateCopy = gridState.map { line -> line.map { char -> char }.toMutableList() }.toMutableList()
    }

    fun moveRobot(moves: String, scaled: Boolean = false) {
        var currentPosition = origin.toMutableList()
        for (move in moves) {
            println(move)
            if (move == '<' || move == '>')
                currentPosition = moveHorizontallyIfPossible(currentPosition[0], currentPosition[1], move, scaled)
            else if (!scaled)
                currentPosition = moveVerticallyIfPossible(currentPosition[0], currentPosition[1], move)
            else
                currentPosition = moveRobotVerticallyScaled(currentPosition[0], currentPosition[1], move)
        }
    }

    fun moveRobotVerticallyScaled(i: Int, j: Int, move: Char): MutableList<Int> {
        var position = mutableListOf(i, j)
        var possibleCoords: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
        if (move == '^') {
            for (row in 0..gridState.size) {
                possibleCoords[row] = mutableSetOf()
            }
            var scaledRow = checkIfScaledVerticalUpIsPossible(i, j, possibleCoords)
            if (scaledRow != -1) {
                for (row in scaledRow + 1..< i) {
                    for (col in possibleCoords[row]!!) {
                        gridState[row - 1][col] = gridState[row][col]
                        gridState[row][col] = '.'
                    }
                }
                gridState[i][j] = '.'
                gridState[i - 1][j] = '@'
                position[0] = i - 1
            }
        } else {
            for (col in 0..gridState[i].size) {
                possibleCoords[col] = mutableSetOf()
            }
            var scaledRow = checkIfScaledVerticalDownIsPossible(i, j, possibleCoords)
            if (scaledRow != -1) {
                for (row in scaledRow - 1 downTo i + 1) {
                    for (col in possibleCoords[row]!!) {
                        gridState[row + 1][col] = gridState[row][col]
                        gridState[row][col] = '.'
                    }
                }
                gridState[i][j] = '.'
                gridState[i + 1][j] = '@'
                position[0] = i + 1
            }

        }
        return position
    }

    fun calculateGPSCoordinates(scaled: Boolean = false): Long {
        var sum = 0L
        for (i in gridState.indices) {
            for (j in gridState[i].indices) {
                if (gridState[i][j] == 'O' || gridState[i][j] == '[') {
                    if (!scaled)
                        sum += 100 * i.toLong() + j.toLong()
                    else
                        sum += 100 * i.toLong() + (j - 1).toLong()
                }
            }
        }
        return sum
    }

    fun checkIfScaledVerticalUpIsPossible(i: Int, j: Int, coords: MutableMap<Int, MutableSet<Int>>): Int {
        if (gridState[i - 1][j] == '.')
            return i - 1
        else if (gridState[i - 1][j] == '#')
            return -1

        var top = checkIfScaledVerticalUpIsPossible(i - 1, j, coords)
        if (top != -1) coords[i - 1]!!.add(j)

        var side: Int
        if (gridState[i - 1][j] == '[') {
            side = checkIfScaledVerticalUpIsPossible(i - 1, j + 1, coords)
            if (side != -1) coords[i - 1]!!.add(j + 1)
        } else {
            side = checkIfScaledVerticalUpIsPossible(i - 1, j - 1, coords)
            if (side != -1) coords[i - 1]!!.add(j - 1)
        }

        return min(top, side)
    }


    fun checkIfScaledVerticalDownIsPossible(i: Int, j: Int, coords: MutableMap<Int, MutableSet<Int>>): Int {
        if (gridState[i + 1][j] == '.')
            return i + 1
        else if (gridState[i + 1][j] == '#')
            return -1

        var bottom = checkIfScaledVerticalDownIsPossible(i + 1, j, coords)
        if (bottom != -1) coords[i + 1]!!.add(j)

        var side: Int
        if (gridState[i + 1][j] == '[') {
            side = checkIfScaledVerticalDownIsPossible(i + 1, j + 1, coords)
            if (side != -1) coords[i + 1]!!.add(j + 1)
        } else {
            side = checkIfScaledVerticalDownIsPossible(i + 1, j - 1, coords)
            if (side != -1) coords[i + 1]!!.add(j - 1)
        }

        if (bottom == -1 || side == -1) return -1
        else return max(bottom, side)
    }

    fun moveHorizontallyIfPossible(i: Int, j: Int, move: Char, scaled: Boolean = false): MutableList<Int> {
        var freeSpot = -1
        var movedSpot = mutableListOf(i, j)
        if (move == '>') {
            var foundSpace = false
            var col = j + 1
            while (col < this.gridState[i].size && gridState[i][col] != '#' && !foundSpace) {
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
                if (!scaled) {
                    for (col in (j + 2) .. freeSpot)
                        gridState[i][col] = 'O'
                } else {
                    for (col in (j + 2)..freeSpot step 2) {
                        gridState[i][col] = '['
                        gridState[i][col + 1] = ']'
                    }
                }
            }
        }
        if (move == '<') {
            var foundSpace = false
            var col = j - 1
            while (col >= 0 && gridState[i][col] != '#' && !foundSpace) {
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
                if (!scaled) {
                    for (col in (j - 2) downTo freeSpot)
                        gridState[i][col] = 'O'
                } else {
                    for (col in (j - 2) downTo freeSpot step 2) {
                        gridState[i][col] = ']'
                        gridState[i][col - 1] = '['
                    }
                }
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
            while (row < this.gridState.size && gridState[row][j] != '#' && !foundSpace) {
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
            while (row >= 0 && gridState[row][j] != '#' && !foundSpace) {
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
    var fileInputMoves = Path("src/main/resources/inputDay15-moves.txt").readText().replace("\n", "")
    var aoc = AOCDay15()
    aoc.initializeGrids(fileInputGrid, true)
    aoc.moveRobot(fileInputMoves, true)
    aoc.gridState.forEach { println(it) }
    println(aoc.calculateGPSCoordinates())
}