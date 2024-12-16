import kotlin.io.path.Path
import kotlin.io.path.readLines


class AOCDay13 {

    var targets: MutableList<List<Long>> = mutableListOf()
    var buttonsA: MutableList<List<Long>> = mutableListOf()
    var buttonsB: MutableList<List<Long>> = mutableListOf()

    fun parseInputs(input: List<String>) {
        for (i in 0..input.size step 4) {
            buttonsA.add(input[i + (i % 4)].split(" ").filter { it.contains("+") }
                .map { it.replace(Regex("[XY+,]"), "").toLong() }.toMutableList())

            buttonsB.add(input[i + (i % 4) + 1].split(" ").filter { it.contains("+") }
                .map { it.replace(Regex("[XY+,]"), "").toLong() }.toMutableList())

            targets.add(input[i + (i % 4) + 2].split(" ").filter { it.contains("=") }
                .map { it.replace(Regex("[XY=,]"), "").toLong() + 10000000000000L }.toMutableList())
        }
    }

    fun calculateMinimumTokensToWin(): Long {
        var winningCosts = 0L
        for (index in targets.indices) {
            winningCosts += calculateWinningCosts(targets[index], buttonsA[index], buttonsB[index])
        }
        return winningCosts
    }

    fun calculateWinningCosts(target: List<Long>, buttonA: List<Long>, buttonB: List<Long>): Long {
        var counts = calculateOptimizedCost(target, buttonA, buttonB)
        var cost = 0L
        if (counts[0] != -1L && counts[1] != -1L) {
            cost = counts[0] * 3L + counts[1]
        }
        return cost
    }

    fun calculateOptimizedCost(target: List<Long>, buttonA: List<Long>, buttonB: List<Long>): List<Long> {
        var tokenCounts = mutableListOf(-1L, -1L)
        var numerator1 = (target[0] * buttonA[1]) - (target[1] * buttonA[0])
        var denominator1 = (buttonB[0] * buttonA[1]) - (buttonB[1] * buttonA[0])
        var numerator2 = (target[0] * buttonB[1]) - (target[1] * buttonB[0])
        var denominator2 = (buttonA[0] * buttonB[1]) - (buttonA[1] * buttonB[0])
        if (numerator1 % denominator1 == 0L && numerator2 % denominator2 == 0L) {
            tokenCounts[0] = numerator2 / denominator2
            tokenCounts[1] = numerator1 / denominator1
        }
        return tokenCounts
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay13a.txt").readLines()
    var aoc = AOCDay13()
    aoc.parseInputs(fileInput)
    println(aoc.calculateMinimumTokensToWin())
}