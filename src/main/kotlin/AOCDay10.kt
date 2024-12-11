import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay10 {

    private var trails: MutableList<MutableList<Int>> = mutableListOf()
    private var trailHeadsStartEnd: MutableMap<MutableList<Int>, MutableSet<MutableList<Int>>> = mutableMapOf()
    private var trailHeadsRatings: MutableMap<MutableList<Int>, Long> = mutableMapOf()

    fun parseToTrails(trailInput: List<String>) {
        trailInput.forEach { line ->
            this.trails.add(line.toMutableList().map { it -> ("" + it).toInt() }.toMutableList()) }
    }

    fun initializeTrailsValues() {
        for (row in this.trails.indices) {
            for (col in this.trails[row].indices) {
                if (this.trails[row][col] == 0) {
                    this.trailHeadsStartEnd[mutableListOf(row, col)] = mutableSetOf()
                    this.trailHeadsRatings[mutableListOf(row, col)] = 0
                }
            }
        }
    }

    fun countTrailHeads(): Long {
        for (trailStart in this.trailHeadsStartEnd.keys) {
            var trailQueue: Queue<List<Int>> = LinkedList()
            trailQueue.add(trailStart)
            while (trailQueue.isNotEmpty()) {
                var current = trailQueue.remove()
                var row = current[0]
                var col = current[1]
                if (this.trails[row][col] == 9)
                    this.trailHeadsStartEnd[trailStart]!!.add(mutableListOf(row, col))
                else
                    addIncrementedByOneValuesToQueue(row, col, trailQueue)
            }
        }
        return this.trailHeadsStartEnd.values.sumOf { it.size.toLong() }
    }

    fun countRatings(): Long {
        for (trailStart in this.trailHeadsRatings.keys) {
            var trailQueue: Queue<List<Int>> = LinkedList()
            trailQueue.add(trailStart)
            while (trailQueue.isNotEmpty()) {
                var current = trailQueue.remove()
                var row = current[0]
                var col = current[1]
                if (this.trails[row][col] == 9)
                    this.trailHeadsRatings[trailStart] = this.trailHeadsRatings[trailStart]!! + 1
                else
                    addIncrementedByOneValuesToQueueWithRepeats(row, col, trailQueue)
            }
        }
        return this.trailHeadsRatings.values.sumOf { it }
    }

    private fun addIncrementedByOneValuesToQueue(row: Int, col: Int, trailQueue: Queue<List<Int>>) {
        if (row == 0) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row + 1, col)))
                trailQueue.add(listOf(row + 1, col))
        }
        if (row == this.trails.size - 1) {
            if (this.trails[row - 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row - 1, col)))
                trailQueue.add(listOf(row - 1, col))
        }
        if (col == 0) {
            if (this.trails[row][col + 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col + 1)))
                trailQueue.add(listOf(row, col + 1))
        }
        if (col == this.trails[0].size - 1) {
            if (this.trails[row][col - 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col - 1)))
                trailQueue.add(listOf(row, col - 1))
        }
        if ((row == 0 || row == this.trails.size - 1) && (col != 0 && col != this.trails[0].size - 1)) {
            if (this.trails[row][col + 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col + 1)))
                trailQueue.add(listOf(row, col + 1))
            if (this.trails[row][col - 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col - 1)))
                trailQueue.add(listOf(row, col - 1))
        }
        if ((col == 0 || col == this.trails[0].size - 1) && (row != 0 && row != this.trails.size - 1)) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row + 1, col)))
                trailQueue.add(listOf(row + 1, col))
            if (this.trails[row - 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row - 1, col)))
                trailQueue.add(listOf(row - 1, col))
        }
        if (row != 0 && col != 0 && row != this.trails.size - 1 && col != this.trails[0].size - 1) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row + 1, col)))
                trailQueue.add(listOf(row + 1, col))
            if (this.trails[row - 1][col] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row - 1, col)))
                trailQueue.add(listOf(row - 1, col))
            if (this.trails[row][col + 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col + 1)))
                trailQueue.add(listOf(row, col + 1))
            if (this.trails[row][col - 1] == this.trails[row][col] + 1 && !trailQueue.contains(listOf(row, col - 1)))
                trailQueue.add(listOf(row, col - 1))
        }
    }

    private fun addIncrementedByOneValuesToQueueWithRepeats(row: Int, col: Int, trailQueue: Queue<List<Int>>) {
        if (row == 0) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row + 1, col))
        }
        if (row == this.trails.size - 1) {
            if (this.trails[row - 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row - 1, col))
        }
        if (col == 0) {
            if (this.trails[row][col + 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col + 1))
        }
        if (col == this.trails[0].size - 1) {
            if (this.trails[row][col - 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col - 1))
        }
        if ((row == 0 || row == this.trails.size - 1) && (col != 0 && col != this.trails[0].size - 1)) {
            if (this.trails[row][col + 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col + 1))
            if (this.trails[row][col - 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col - 1))
        }
        if ((col == 0 || col == this.trails[0].size - 1) && (row != 0 && row != this.trails.size - 1)) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row + 1, col))
            if (this.trails[row - 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row - 1, col))
        }
        if (row != 0 && col != 0 && row != this.trails.size - 1 && col != this.trails[0].size - 1) {
            if (this.trails[row + 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row + 1, col))
            if (this.trails[row - 1][col] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row - 1, col))
            if (this.trails[row][col + 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col + 1))
            if (this.trails[row][col - 1] == this.trails[row][col] + 1)
                trailQueue.add(listOf(row, col - 1))
        }
    }

}


fun main() {
    var fileInput = Path("src/main/resources/inputDay10a.txt").readLines()
    var aoc = AOCDay10()
    aoc.parseToTrails(fileInput)
    aoc.initializeTrailsValues()
    println(aoc.countTrailHeads())
    println(aoc.countRatings())
}