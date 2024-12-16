import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay12 {

    var plots: MutableList<MutableList<Char>> = mutableListOf()
    var remainingPlots: MutableList<MutableList<Char>> = mutableListOf()

    fun initializePlots(input: List<String>) {
        this.plots = input.map { it.toCharArray().toMutableList() }.toMutableList()
        this.plots.forEach {
            this.remainingPlots.add(it.toMutableList())
        }
    }

    fun calculatePrice(): Long {
//        var perimeters = determineCellPerimeters() // Part I
        var perimeters = determineCellPerimetersForBulkDiscount() // Part II
        var regions = findAllRegions()

        return regions.sumOf {
            it.sumOf { pair -> perimeters[pair.first][pair.second].toLong() } * it.size.toLong()
        }
    }

    private fun initializePerimeters(): MutableList<MutableList<Int>> {
        var perimeters: MutableList<MutableList<Int>> = mutableListOf()
        this.plots.forEach {
            perimeters.add(MutableList(it.size) { 0 }.toMutableList())
        }
        return perimeters
    }

    fun determineCellPerimetersForBulkDiscount(): MutableList<MutableList<Int>> {
        var perimeters = initializePerimeters()
        var rows = perimeters.size
        var cols = perimeters[0].size
        for (i in 1..rows - 2) {
            for (j in 1..cols - 2) {
                perimeters[i][j] = cornerCountsInterior(i, j)
            }
        }
        for (j in 0..<cols) {
            perimeters[0][j] = cornerCountsTop(0, j)
            perimeters[rows - 1][j] = cornerCountsBottom(rows - 1, j)
        }
        for (i in 1..rows - 2) {
            perimeters[i][0] = cornerCountsSidesInterior(i, 0)
            perimeters[i][cols - 1] = cornerCountsSidesInterior(i, cols - 1)
        }
        return perimeters
    }

    fun cornerCountsSidesInterior(i: Int, j: Int): Int {
        var count = 0
        if (j == 0) {
            if (this.plots[i][j] != this.plots[i - 1][j]) count++ // Top left
            if (this.plots[i][j] != this.plots[i + 1][j]) count++ // Bottom left
            count += checkTopRight(i, j)
            count += checkBottomRight(i, j)
        }
        if (j == this.plots[0].size - 1) {
            if (this.plots[i][j] != this.plots[i - 1][j]) count++ // Top right
            if (this.plots[i][j] != this.plots[i + 1][j]) count++
            count += checkTopLeft(i, j)
            count += checkBottomLeft(i, j)
        }
        return count
    }

    fun cornerCountsTop(i: Int, j: Int): Int {
        var count = 0
        if (i == 0 && j == 0) {
            count++ // Top left corner
            if (this.plots[i][j] != this.plots[i + 1][j]) count++ // Bottom left
            if (this.plots[i][j] != this.plots[i][j + 1]) count++ // Top right
            count += checkBottomRight(i, j)
        }
        if (i == 0 && j == this.plots[0].size - 1) {
            count++ // Top right corner
            if (this.plots[i][j] != this.plots[i + 1][j]) count++ // Bottom right
            if (this.plots[i][j] != this.plots[i][j - 1]) count++ // Top left
            count += checkBottomLeft(i, j)
        }
        if (i == 0 && (j != 0 && j != this.plots[0].size - 1)) {
            if (this.plots[i][j] != this.plots[i][j - 1]) count++ // Top left
            if (this.plots[i][j] != this.plots[i][j + 1]) count++ // Top right
            count += checkBottomLeft(i, j)
            count += checkBottomRight(i, j)
        }
        return count
    }

    fun cornerCountsBottom(i: Int, j: Int): Int {
        var count = 0
        if (i == this.plots.size - 1 && j == 0) {
            count++ // Bottom left corner
            if (this.plots[i][j] != this.plots[i - 1][j]) count++ // Top left
            if (this.plots[i][j] != this.plots[i][j + 1]) count++ // Bottom right
            count += checkTopRight(i, j)
        }
        if (i == this.plots.size - 1 && j == this.plots[0].size - 1) {
            count++ // Bottom right corner
            if (this.plots[i][j] != this.plots[i - 1][j]) count++ // Top Right
            if (this.plots[i][j] != this.plots[i][j - 1]) count++ // Bottom left
            count += checkTopLeft(i, j)
        }
        if (i == this.plots.size - 1 && (j != 0 && j != this.plots[0].size - 1)) {
            if (this.plots[i][j] != this.plots[i][j - 1]) count++ // Bottom left
            if (this.plots[i][j] != this.plots[i][j + 1]) count++ // Bottom right
            count += checkTopLeft(i, j)
            count += checkTopRight(i, j)
        }
        return count
    }

    fun cornerCountsInterior(i: Int, j: Int): Int {
        var count = 0
        count += checkTopLeft(i, j)
        count += checkTopRight(i, j)
        count += checkBottomLeft(i, j)
        count += checkBottomRight(i, j)

        return count
    }

    private fun checkTopLeft(i: Int, j: Int): Int {
        var count = 0
        if ((this.plots[i][j] == this.plots[i][j - 1] && this.plots[i][j] == this.plots[i - 1][j]
                    && this.plots[i][j] != this.plots[i - 1][j - 1]) || (this.plots[i][j] != this.plots[i][j - 1]
                    && this.plots[i][j] != this.plots[i - 1][j])
        )
            count++
        return count
    }

    private fun checkTopRight(i: Int, j: Int): Int {
        var count = 0
        if ((this.plots[i][j] == this.plots[i - 1][j] && this.plots[i][j] == this.plots[i][j + 1]
                    && this.plots[i][j] != this.plots[i - 1][j + 1]) || (this.plots[i][j] != this.plots[i - 1][j]
                    && this.plots[i][j] != this.plots[i][j + 1])
        )
            count++
        return count
    }

    private fun checkBottomRight(i: Int, j: Int, ): Int {
        var count = 0
        if ((this.plots[i][j] == this.plots[i][j + 1] && this.plots[i][j] == this.plots[i + 1][j]
                    && this.plots[i][j] != this.plots[i + 1][j + 1]) || (this.plots[i][j] != this.plots[i][j + 1]
                    && this.plots[i][j] != this.plots[i + 1][j])
        )
            count++
        return count
    }

    private fun checkBottomLeft(i: Int, j: Int): Int {
        var count = 0
        if ((this.plots[i][j] == this.plots[i][j - 1] && this.plots[i][j] == this.plots[i + 1][j]
                    && this.plots[i][j] != this.plots[i + 1][j - 1]) || (this.plots[i][j] != this.plots[i][j - 1]
                    && this.plots[i][j] != this.plots[i + 1][j])
        )
            count++
        return count
    }

    fun determineCellPerimeters(): MutableList<MutableList<Int>> {
        var perimeters = initializePerimeters()
        var rows = perimeters.size
        var cols = perimeters[0].size
        for (j in perimeters[0].indices) {
            perimeters[0][j] += 1
            perimeters[rows - 1][j] += 1
            if (j != cols - 1 && this.plots[rows - 1][j] != this.plots[rows - 1][j + 1]) {
                perimeters[rows - 1][j] += 1
                perimeters[rows - 1][j + 1] += 1
            }
        }
        for (i in perimeters.indices) {
            perimeters[i][0] += 1
            perimeters[i][cols - 1] += 1
            if (i != rows - 1 && this.plots[i][cols - 1] != this.plots[i + 1][cols - 1]) {
                perimeters[i][cols - 1] += 1
                perimeters[i + 1][cols - 1] += 1
            }
        }
        for (i in 0..<rows - 1) {
            for (j in 0..<cols - 1) {
                if (this.plots[i][j] != this.plots[i + 1][j]) {
                    perimeters[i][j] += 1
                    perimeters[i + 1][j] += 1
                }
                if (this.plots[i][j] != this.plots[i][j + 1]) {
                    perimeters[i][j] += 1
                    perimeters[i][j + 1] += 1
                }
            }
        }
        return perimeters
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

    private fun checksAndAddsToRegionAndQueue(
        neighbor: Pair<Int, Int>,
        key: Char,
        region: MutableSet<Pair<Int, Int>>,
        queue: Queue<Pair<Int, Int>>
    ) {
        var neighborKey = this.plots[neighbor.first][neighbor.second]
        if (neighbor !in region && neighborKey == key) {
            region.add(neighbor)
            queue.add(neighbor)
            this.remainingPlots[neighbor.first][neighbor.second] = '-'
        }
    }

    private fun addToRegion(
        spot: Pair<Int, Int>,
        rows: Int,
        cols: Int,
        region: MutableSet<Pair<Int, Int>>,
        key: Char,
        queue: Queue<Pair<Int, Int>>
    ) {
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
    var fileInput = Path("src/main/resources/inputDay12.txt").readLines()
    var aoc = AOCDay12()
    aoc.initializePlots(fileInput)
    println(aoc.calculatePrice())
}