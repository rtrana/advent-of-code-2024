import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

class AOCDay11 {

    fun parseToStoneNumbers(input: String): MutableMap<Long, Long> {
        var stoneCounts = mutableMapOf<Long, Long>()
        input.split(" ").forEach { stoneCounts[it.toLong()] = 1L }
        return stoneCounts
    }

    fun stonesAfterNBlinks(blinks: Int, stoneCounts: MutableMap<Long, Long>): Long {
        var stones = stoneCounts.toMutableMap()
        for (i in 1 .. blinks) {
            var tempStoneCounts = stones.toMutableMap()
            for ((k, v) in stones) {
                if (v == 0L) continue
                if (k == 0L) {
                    tempStoneCounts[0] = tempStoneCounts[0]!! - v
                    tempStoneCounts[1] = tempStoneCounts.getOrDefault(1, 0L) + v
                } else if (("" + k).length % 2 == 0) {
                    var stonesString = "" + k
                    var firstStone = stonesString.substring(0, stonesString.length / 2).toLong()
                    var secondStone = stonesString.substring(stonesString.length / 2).toLong()
                    tempStoneCounts[firstStone] = tempStoneCounts.getOrDefault(firstStone, 0) + v
                    tempStoneCounts[secondStone] = tempStoneCounts.getOrDefault(secondStone, 0) + v
                    tempStoneCounts[k] = tempStoneCounts[k]!! - v
                } else {
                    tempStoneCounts[k * 2024] = tempStoneCounts.getOrDefault(k * 2024, 0) + v
                    tempStoneCounts[k] = tempStoneCounts[k]!! - v
                }
            }
            stones = tempStoneCounts.toMutableMap()
        }
        return stones.values.sum()
    }

}


fun main() {
    var fileInput = Path("src/main/resources/inputDay11a.txt").readText()
    var aoc = AOCDay11()
    var stones = aoc.parseToStoneNumbers(fileInput)
    println(aoc.stonesAfterNBlinks(75, stones))
}