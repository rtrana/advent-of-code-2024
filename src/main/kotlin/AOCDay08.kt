import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class AOCDay08(fileInput: List<String>) {

    var fileInput = fileInput
    var antennaLocations: MutableMap<String, MutableList<MutableList<Int>>> = mutableMapOf()

    fun getAntennaLocations() {
        var i = 0
        this.fileInput.forEach {
            for (j in it.indices) {
                var antenna = "" + it[j]
                var regex = Regex("[a-zA-Z0-9]")
                if (antenna.matches(regex) && antenna in this.antennaLocations)
                    this.antennaLocations[antenna]!!.add(mutableListOf(j, -i))
                else if (antenna.matches(regex))
                    this.antennaLocations[antenna] = mutableListOf(mutableListOf(j, -i))
            }
            i++
        }
    }

    fun getCountOfAllAntiNodeLocations(includeInline: Boolean = false): Long {
        var antiNodeLocationSet: MutableSet<MutableList<Int>> = mutableSetOf()
        for (locations in this.antennaLocations.values) {
            if (includeInline) {
                antiNodeLocationSet.addAll(getInlineAntiNodeLocationsForAntenna(locations))
            } else
                antiNodeLocationSet.addAll(getAntiNodeLocationsForAntenna(locations))
        }
        return antiNodeLocationSet.size.toLong()
    }

    fun getInlineAntiNodeLocationsForAntenna(antennaLocations: List<List<Int>>): MutableList<MutableList<Int>> {
        var locations: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 .. antennaLocations.size - 2) {
            for (j in i + 1..< antennaLocations.size) {
                var run = antennaLocations[i][0] - antennaLocations[j][0]
                var rise = antennaLocations[i][1] - antennaLocations[j][1]
                var xstart = antennaLocations[i][0]; var ystart = antennaLocations[i][1]
                var x = xstart; var y = ystart
                if (run > 0 && rise > 0) {
                    while (x >= 0 && abs(y) <= this.fileInput.size - 1) {
                        var antiNode = mutableListOf(x, y)
                        if (isWithinBoundaries(antiNode)) locations.add(antiNode)
                        x -= run
                        y -= rise
                    }
                    x = xstart; y = ystart
                    while (x <= this.fileInput[0].length - 1 && y <= 0) {
                        var antiNode = mutableListOf(x, y)
                        if (isWithinBoundaries(antiNode)) locations.add(antiNode)
                        x += run
                        y += rise
                    }
                } else {
                    while (x >= 0 && y <= 0) {
                        var antiNode = mutableListOf(x, y)
                        if (isWithinBoundaries(antiNode)) locations.add(antiNode)
                        x += run
                        y += rise
                    }
                    x = xstart; y = ystart
                    while (x <= this.fileInput[0].length - 1 && abs(y) <= this.fileInput.size - 1) {
                        var antiNode = mutableListOf(x, y)
                        if (isWithinBoundaries(antiNode)) locations.add(antiNode)
                        x -= run
                        y -= rise
                    }
                }
            }
        }

        return locations
    }

    fun getAntiNodeLocationsForAntenna(antennaLocations: List<List<Int>>): MutableList<MutableList<Int>> {
        var locations: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 .. antennaLocations.size - 2) {
            for (j in i + 1..< antennaLocations.size) {
                var xDiff = antennaLocations[i][0] - antennaLocations[j][0]
                var yDiff = antennaLocations[i][1] - antennaLocations[j][1]
                var firstAntiNode = mutableListOf<Int>()
                var secondAntiNode = mutableListOf<Int>()
                findAntiNodes(xDiff, yDiff, firstAntiNode, antennaLocations, i, secondAntiNode, j)
                if (isWithinBoundaries(firstAntiNode)) locations.add(firstAntiNode)
                if (isWithinBoundaries(secondAntiNode)) locations.add(secondAntiNode)
            }
        }

        return locations
    }

    private fun findAntiNodes(xDiff: Int, yDiff: Int, firstAntiNode: MutableList<Int>, antennaLocations: List<List<Int>>,
        i: Int, secondAntiNode: MutableList<Int>, j: Int) {
        if (xDiff > 0 && yDiff > 0) {
            firstAntiNode.add(antennaLocations[i][0] + xDiff)
            firstAntiNode.add(antennaLocations[i][1] + yDiff)
            secondAntiNode.add(antennaLocations[j][0] - xDiff)
            secondAntiNode.add(antennaLocations[j][1] - yDiff)
        } else {
            firstAntiNode.add(antennaLocations[i][0] - abs(xDiff))
            firstAntiNode.add(antennaLocations[i][1] + abs(yDiff))
            secondAntiNode.add(antennaLocations[j][0] + abs(xDiff))
            secondAntiNode.add(antennaLocations[j][1] - abs(yDiff))
        }
    }

    fun isWithinBoundaries(antiNode: List<Int>): Boolean {
        if (antiNode[0] < 0 || antiNode[0] > this.fileInput[0].length - 1)
            return false
        else if (antiNode[1] > 0 || abs(antiNode[1]) > this.fileInput.size - 1)
            return false
        return true
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay08a.txt").readLines()
    var aoc = AOCDay08(fileInput)
    aoc.getAntennaLocations()
    println(aoc.getCountOfAllAntiNodeLocations(true))

}