import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.abs

class AOCDay03() {

    fun parseInputToIndividualMultipliers(input: String): List<List<Long>> {
        var searchResults = searchForStringExpression(input, Regex("mul[(]\\d+,\\d+[)]"))
        var convertedResults = extractMultiplierValues(searchResults)
        return convertedResults
    }

    fun parseInputToEnabledMultipliers(input: String): List<List<Long>> {
        var searchResults = searchForStringExpression(input,
            Regex("(do[(][)])|(don't[(][)])|(mul[(]\\d+,\\d+[)])"))
        var enabledResults = findEnabledMultipliers(searchResults)
        var convertedResults = extractMultiplierValues(enabledResults)
        return convertedResults
    }

    private fun searchForStringExpression(input: String, searchString: Regex): MutableList<String> {
        var searchResults = mutableListOf<String>()
        searchString.findAll(input).forEach { searchResults.add(it.value) }
        return searchResults
    }

    private fun extractMultiplierValues(searchResults: MutableList<String>): List<MutableList<Long>> {
        var convertedResults = searchResults
            .map { it: String -> it.replace("mul", "") }
            .map { it: String -> it.replace(Regex("[()]"), "") }
            .map { it: String -> it.split(",").toMutableList() }
            .map { it: List<String> -> mutableListOf(it[0].toLong(), it[1].toLong()) }
            .toList()
        return convertedResults
    }

    private fun findEnabledMultipliers(searchResults: MutableList<String>): MutableList<String> {
        var enabledResults = mutableListOf<String>()
        var i = 0
        var enable = true
        while (i < searchResults.size) {
            if (searchResults[i] == "do()") enable = true
            if (searchResults[i] == "don't()") enable = false
            if (enable && searchResults[i].contains(Regex("mul[(]\\d+,\\d+[)]")))
                enabledResults.add(searchResults[i])
            i++
        }
        return enabledResults
    }

    fun multiplyMultipliersAndSum(multipliers: List<List<Long>>): Long {
        return multipliers.sumOf { it[0] * it[1] }
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay03a.txt").readText()
    var multipliers = AOCDay03().parseInputToIndividualMultipliers(fileInput)
    println(AOCDay03().multiplyMultipliersAndSum(multipliers))
    multipliers = AOCDay03().parseInputToEnabledMultipliers(fileInput)
    println(AOCDay03().multiplyMultipliersAndSum(multipliers))
}