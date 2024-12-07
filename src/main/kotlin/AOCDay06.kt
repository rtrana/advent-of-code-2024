import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay06(fileInput: List<String>) {

    private var positionMap: List<String> = fileInput
    private var startPosition = listOf(-1, -1)

    fun findGuardStartPosition() {
        this.positionMap.indices.forEach {
            var loc = this.positionMap[it].indexOf("^")
            if (loc != -1) this.startPosition = listOf(it, loc)
        }
    }

    fun findObstructionCount(): Long {
        var mapOption = positionMap.toMutableList()
        var count = 0L
        for (i in positionMap.indices) {
            for (j in positionMap[0].indices) {
                if (positionMap[i][j] != '#' && positionMap[i][j] != '^') {
                    mapOption[i] = mapOption[i].substring(0, j) + "#" + mapOption[i].substring(j + 1)
                    if (countGuardSpots(mapOption) == 0L)
                        count++
                }
                mapOption[i] = positionMap[i]
            }
        }
        return count
    }

    fun countGuardSpots(positionMap: List<String> = this.positionMap): Long {
        var guardRoute = findGuardRoute(positionMap)
        return guardRoute.sumOf { it: String -> Regex("[*]").findAll(it).count().toLong() }
    }

    fun findGuardRoute(positionMapPossibility: List<String>): List<String> {
        var guardRoute = positionMapPossibility.toMutableList()
        var visitedDirections: MutableMap<MutableList<Int>, MutableList<Int>> =
            initializeVisitedDirections(positionMapPossibility)

        var i = this.startPosition[0]; var j = this.startPosition[1]
        var direction = 90
        while (isNotBoundary(i, j) && !visitedDirections[mutableListOf(i, j)]!!.contains(direction)) {
            visitedDirections[mutableListOf(i, j)]!!.add(direction)
            if (direction % 360 == 90) {
                updateGuardRoute(guardRoute, i, j, "*")
                if (i - 1 != -1 && positionMapPossibility[i - 1][j] == '#') {
                    direction -= 90
                } else i--
            } else if (direction % 360 == 0) {
                updateGuardRoute(guardRoute, i, j, "*")
                if (j + 1 != positionMapPossibility[0].length && positionMapPossibility[i][j + 1] == '#') {
                    direction -= 90
                } else j++
            } else if (direction % 360 == -180) {
                updateGuardRoute(guardRoute, i, j, "*")
                if (j - 1 != -1 && positionMapPossibility[i][j - 1] == '#') {
                    direction = 90
                } else j--
            } else {
                updateGuardRoute(guardRoute, i, j, "*")
                if (i + 1 != positionMapPossibility.size && positionMapPossibility[i + 1][j] == '#') {
                    direction -= 90
                } else i++
            }
        }

        if (isNotBoundary(i, j) && visitedDirections[mutableListOf(i, j)]!!.contains(direction))
            return emptyList()

        return guardRoute
    }

    private fun initializeVisitedDirections(positionMapPossibility: List<String>): MutableMap<MutableList<Int>, MutableList<Int>> {
        var visitedDirections: MutableMap<MutableList<Int>, MutableList<Int>> = mutableMapOf()
        for (i in positionMapPossibility.indices) {
            for (j in positionMapPossibility[0].indices) {
                visitedDirections[mutableListOf(i, j)] = mutableListOf()
            }
        }
        return visitedDirections
    }

    private fun isNotBoundary(i: Int, j:Int): Boolean {
        return i != -1 && j != -1 && i != this.positionMap.size && j != this.positionMap[0].length
    }

    private fun updateGuardRoute(guardRoute: MutableList<String>, i: Int, j: Int, marker: String) {
        guardRoute[i] = guardRoute[i].substring(0, j) + marker + guardRoute[i].substring(j + 1)
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay06a.txt").readLines()
    var aoc = AOCDay06(fileInput)
    aoc.findGuardStartPosition()
    println(aoc.countGuardSpots())
    println(aoc.findObstructionCount())
}