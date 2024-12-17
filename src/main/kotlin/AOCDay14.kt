import kotlin.io.path.Path
import kotlin.io.path.readLines


class AOCDay14 {

    var coordinates: MutableList<MutableList<Int>> = mutableListOf()
    var velocities: MutableList<MutableList<Int>> = mutableListOf()
    var robotMap: MutableList<MutableList<Int>> = MutableList(103){ MutableList(101) { 0 } }

    fun parseInput(input: List<String>) {
        for (line in input) {
            var parts = line.split(" ")
            coordinates.add(parts[0].replace(Regex("p="), "")
                .split(",").map { s -> s.toInt() }.toMutableList())
            velocities.add(parts[1].replace(Regex("v="), "")
                .split(",").map { s -> s.toInt() }.toMutableList())
        }

        for (coordinate in coordinates) {
            this.robotMap[coordinate[1]][coordinate[0]] += 1
        }
    }

    fun findAllRobotCoordinates(time: Int, gridX: Int, gridY: Int): MutableList<MutableList<Int>> {
        var tempCoordinates = coordinates.map { it.toMutableList() }.toMutableList()
        for (i in 1..time) {
            for (j in coordinates.indices) {
                incrementRobotCoordinate(tempCoordinates[j], velocities[j], gridX, gridY)
            }
            if (checkForOtherRobots()) {
                println(i)
                robotMap.forEach { println(it) }
            }
        }
        return tempCoordinates
    }

    fun checkForOtherRobots(): Boolean {
        for (i in 1..robotMap.size - 2) {
            for (j in 1..robotMap[i].size - 2) {
                if (robotMap[i][j] != 0 && robotMap[i][j + 1] != 0
                    && robotMap[i][j - 1] != 0 && robotMap[i + 1][j] != 0
                    && robotMap[i - 1][j] != 0 && robotMap[i + 1][j + 1] != 0
                    && robotMap[i - 1][j - 1] != 0 && robotMap[i - 1][j + 1] != 0
                    && robotMap[i + 1][j - 1] != 0) {
                    return true
                }
            }
        }
        return false
    }

    fun countRobots(time: Int, gridX: Int, gridY: Int): Long {
        var currentCoordinates = findAllRobotCoordinates(time, gridX, gridY)
        var quadrants = mutableListOf(0L, 0L, 0L, 0L)
        for (coordinate in currentCoordinates) {
            if (coordinate[0] < gridX / 2 && coordinate[1] < gridY / 2) quadrants[0]++
            if (coordinate[0] > gridX / 2 && coordinate[1] < gridY / 2) quadrants[1]++
            if (coordinate[0] < gridX / 2 && coordinate[1] > gridY / 2) quadrants[2]++
            if (coordinate[0] > gridX / 2 && coordinate[1] > gridY / 2) quadrants[3]++
        }
        return quadrants.reduce { acc, num -> acc * num  }
    }

    fun incrementRobotCoordinate(coordinate: MutableList<Int>, velocity: MutableList<Int>, xvals: Int, yvals: Int) {
        this.robotMap[coordinate[1]][coordinate[0]] -= 1
        var x = coordinate[0] + velocity[0]
        var y = coordinate[1] + velocity[1]
        if (x < 0) x += xvals
        if (y < 0) y += yvals
        if (x >= xvals) x %= xvals
        if (y >= yvals) y %= yvals
        coordinate[0] = x
        coordinate[1] = y
        this.robotMap[coordinate[1]][coordinate[0]] += 1
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay14a.txt").readLines()
    var aoc = AOCDay14()
    aoc.parseInput(fileInput)
    println(aoc.countRobots(10000, 101, 103))
}