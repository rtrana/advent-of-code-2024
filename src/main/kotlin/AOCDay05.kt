import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay05 {

    private var pageOrderRulesMap: MutableMap<Long, MutableList<Long>> = mutableMapOf()
    private var updates: MutableList<MutableList<Long>> = mutableListOf()

    fun parseInputToRulesMap(input: List<String>) {
        var tempPageOrderRulesMap = mutableMapOf<Long, MutableList<Long>>()
        input.forEach { it: String ->
            var pageOrder = it.split("|").map(String::toLong)
            if (pageOrder[0] in tempPageOrderRulesMap)
                tempPageOrderRulesMap[pageOrder[0]]!!.add(pageOrder[1])
            else tempPageOrderRulesMap[pageOrder[0]] = mutableListOf(pageOrder[1])
        }
        this.pageOrderRulesMap = tempPageOrderRulesMap
    }

    fun parseInputToUpdates(input: List<String>) {
        this.updates = input.map { it: String ->
            it.split(",").map(String::toLong).toMutableList() }.toMutableList()
    }

    fun isCorrectlyOrdered(pageSeq: List<Long>): Boolean {
        var i = 0
        var ordered = true
        while (i < pageSeq.size - 1 && ordered) {
            var j = i + 1
            while (j < pageSeq.size && ordered) {
                if (pageSeq[i] in this.pageOrderRulesMap &&
                    !this.pageOrderRulesMap[pageSeq[i]]!!.contains(pageSeq[j])) ordered = false
                else if (pageSeq[j] in this.pageOrderRulesMap &&
                    this.pageOrderRulesMap[pageSeq[j]]!!.contains(pageSeq[i])) ordered = false
                j++
            }
            i++
        }
        return ordered
    }

    fun fixIncorrectUpdate(update: List<Long>): List<Long> {
        var correctedUpdate = update
            .sortedWith (object : Comparator <Long> {
                override fun compare (val0: Long, val1: Long) : Int {
                    if (val0 in pageOrderRulesMap && pageOrderRulesMap[val0]!!.contains(val1)) return -1
                    if (val1 in pageOrderRulesMap && pageOrderRulesMap[val1]!!.contains(val0)) return 1
                    return 0
                }
            })
        return correctedUpdate
    }

    fun findSumOfMiddleCorrectUpdates(): Long {
        return this.updates.filter { it: MutableList<Long> -> isCorrectlyOrdered(it) }
            .sumOf { it: List<Long> -> it[it.size / 2] }
    }

    fun findSumOfFixedMiddleUpdates(): Long {
        return this.updates.filter { it: MutableList<Long> -> !isCorrectlyOrdered(it) }
            .map { fixIncorrectUpdate(it)}
            .sumOf { it: List<Long> -> it[it.size / 2] }
    }

}

fun main() {
    var aoc = AOCDay05()
    aoc.parseInputToRulesMap(Path("src/main/resources/inputDay05a.txt").readLines())
    aoc.parseInputToUpdates(Path("src/main/resources/inputDay05a-order.txt").readLines())
    println(aoc.findSumOfMiddleCorrectUpdates())
    println(aoc.findSumOfFixedMiddleUpdates())
}