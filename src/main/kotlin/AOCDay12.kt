import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay12 {

    var plots: MutableList<MutableList<Char>> = mutableListOf()
    var remainingPlots: MutableList<MutableList<Char>> = mutableListOf()
    var perimeters: MutableList<MutableList<Int>> = mutableListOf()

    fun initializePlots(input: List<String>) {
        this.plots = input.map { it.toCharArray().toMutableList() }.toMutableList()
        this.plots.forEach {
            this.remainingPlots.add(it.toMutableList())
            this.perimeters.add(MutableList(it.size) { 0 }.toMutableList())
        }
    }

    fun calculatePrice(): Long {
        determineCellPerimeters()
        var regions = findAllRegions()
        return regions.sumOf {
            it.sumOf { pair -> this.perimeters[pair.first][pair.second].toLong() } * it.size.toLong()
        }
    }

    fun determineCellPerimeters() {
        var rows = this.perimeters.size
        var cols = this.perimeters[0].size
        for (j in this.perimeters[0].indices) {
            this.perimeters[0][j] += 1
            this.perimeters[rows - 1][j] += 1
            if (j != cols - 1 && this.plots[rows - 1][j] != this.plots[rows - 1][j + 1]) {
                this.perimeters[rows - 1][j] += 1
                this.perimeters[rows - 1][j + 1] += 1
            }
        }
        for (i in this.perimeters.indices) {
            this.perimeters[i][0] += 1
            this.perimeters[i][cols - 1] += 1
            if (i != rows - 1 && this.plots[i][cols - 1] != this.plots[i + 1][cols - 1]) {
                this.perimeters[i][cols - 1] += 1
                this.perimeters[i + 1][cols - 1] += 1
            }
        }
        for (i in 0..< rows - 1) {
            for (j in 0..< cols - 1) {
                if (this.plots[i][j] != this.plots[i + 1][j]) {
                    this.perimeters[i][j] += 1
                    this.perimeters[i + 1][j] += 1
                }
                if (this.plots[i][j] != this.plots[i][j + 1]) {
                    this.perimeters[i][j] += 1
                    this.perimeters[i][j + 1] += 1
                }
            }
        }
    }

    fun findAllRegions(): MutableList<MutableSet<Pair<Int, Int>>> {
        var regions: MutableList<MutableSet<Pair<Int, Int>>> = mutableListOf()
        for (i in this.remainingPlots.indices) {
            for (j in this.remainingPlots[i].indices) {
                if (this.remainingPlots[i][j] != '-')
                    regions.add(findRegion(this.remainingPlots[i][j], i, j))
            }
        }
        return regions
    }

    fun findRegion(key: Char, row: Int, col: Int): MutableSet<Pair<Int, Int>> {
        var region = mutableSetOf<Pair<Int, Int>>()
        var queue: Queue<Pair<Int, Int>> = LinkedList()
        var rows = this.plots.size
        var cols = this.plots[0].size
        queue.add(Pair(row, col))
        region.add(Pair(row, col))
        this.remainingPlots[row][col] = '-'
        while (queue.isNotEmpty()) {
            var spot = queue.remove()
            addToRegion(spot, rows, cols, region, key, queue)
        }
        return region
    }

    private fun checksAndAddsToRegionAndQueue(neighbor: Pair<Int, Int>, key: Char, region: MutableSet<Pair<Int, Int>>, queue: Queue<Pair<Int, Int>>) {
        var neighborKey = this.plots[neighbor.first][neighbor.second]
        if (neighbor !in region && neighborKey == key) {
            region.add(neighbor)
            queue.add(neighbor)
            this.remainingPlots[neighbor.first][neighbor.second] = '-'
        }
    }

    private fun addToRegion(spot: Pair<Int, Int>, rows: Int, cols: Int, region: MutableSet<Pair<Int, Int>>, key: Char, queue: Queue<Pair<Int, Int>>) {
        if (spot.second != 0 && spot.second != cols - 1) {
            var left = Pair(spot.first, spot.second - 1)
            checksAndAddsToRegionAndQueue(left, key, region, queue)
            var right = Pair(spot.first, spot.second + 1)
            checksAndAddsToRegionAndQueue(right, key, region, queue)
        }
        if (spot.first != rows - 1) {
            var down = Pair(spot.first + 1, spot.second)
            checksAndAddsToRegionAndQueue(down, key, region, queue)
        }
        if (spot.first != 0) {
            var up = Pair(spot.first - 1, spot.second)
            checksAndAddsToRegionAndQueue(up, key, region, queue)
        }
        if (spot.second == 0) {
            var right = Pair(spot.first, 1)
            checksAndAddsToRegionAndQueue(right, key, region, queue)
        }
        if (spot.second == cols - 1) {
            var left = Pair(spot.first, cols - 2)
            checksAndAddsToRegionAndQueue(left, key, region, queue)
        }

    }

}


fun main() {
    var fileInput = Path("src/main/resources/inputDay12a.txt").readLines()
    var aoc = AOCDay12()
    aoc.initializePlots(fileInput)
    println(aoc.calculatePrice())
}