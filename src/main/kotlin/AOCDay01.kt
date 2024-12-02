import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class AOCDay01 {

    fun getInputAsLongs(input: List<String>): List<List<Long>> {
        var lists = input.map { it: String ->
            it.split("   ") }.toMutableList()
            .map { it: List<String> ->  listOf(it[0].toLong(), it[1].toLong()) }.toMutableList()
        var firstList = mutableListOf<Long>()
        var secondList = mutableListOf<Long>()
        lists.forEach { it: List<Long> ->
            firstList.add(it[0])
            secondList.add(it[1])
        }
        return listOf(firstList, secondList)
    }

    fun getDifferenceSumOfOrderedLists(lists: List<List<Long>>): Long {
        var sortedFirstList = lists[0].sorted()
        var sortedSecondList = lists[1].sorted()
        var differenceSum = sortedFirstList
            .zip(sortedSecondList) { first, second -> abs(first - second) }
            .sum()
        return differenceSum
    }
}

fun main() {
    val fileInput = Path("src/main/resources/inputDay01a.txt").readLines()
    val input = AOCDay01().getInputAsLongs(fileInput)
    println(AOCDay01().getDifferenceSumOfOrderedLists(input))
}