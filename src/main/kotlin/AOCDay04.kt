import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay04(input: List<String>) {

    private val inputLines: List<String> = input

    fun getBackwardDiagonalCount(): Long {
        var backwardCount = 0L
        for (i in 0..this.inputLines.size - 4) {
            for (j in this.inputLines[i].length - 1 downTo 3) {
                var word = this.inputLines[i][j] + "" + this.inputLines[i + 1][j - 1] + "" +
                        this.inputLines[i + 2][j - 2] + "" + this.inputLines[i + 3][j - 3]
                if (word == "XMAS" || word == "SAMX") backwardCount++
            }
        }
        return backwardCount
    }

    fun getForwardDiagonalCount(): Long {
        var forwardCount = 0L
        for (i in 0..this.inputLines.size - 4) {
            for (j in 0..this.inputLines[i].length - 4) {
                var word = this.inputLines[i][j] + "" + this.inputLines[i + 1][j + 1] + "" +
                        this.inputLines[i + 2][j + 2] + "" + this.inputLines[i + 3][j + 3]
                if (word == "XMAS" || word == "SAMX") forwardCount++
            }
        }
        return forwardCount
    }

    fun getHorizontalCount(): Long {
        val horizontalCount = this
            .inputLines.sumOf { it: String -> (Regex("(?=(XMAS)|(SAMX))")
                .findAll(it)).count().toLong() }
        return horizontalCount
    }

    fun getVerticalCount(): Long {
        var word = ""
        var verticalCount = 0L
        for (j in 0..< this.inputLines[0].length) {
            for (i in 0..< this.inputLines.size) {
                word += "" + this.inputLines[i][j]
            }
            verticalCount += Regex("(?=(XMAS)|(SAMX))").findAll(word).count().toLong()
            word = ""
        }
        return verticalCount
    }

    fun getTotalCount(): Long {
        return getHorizontalCount() + getForwardDiagonalCount() +
                getBackwardDiagonalCount() + getVerticalCount()
    }

    fun getXCrossCount(): Long {
        var crossCount = 0L
        for (i in 0..this.inputLines.size - 3) {
            for (j in 0..this.inputLines[i].length - 3) {
                var forward = this.inputLines[i][j] + "" + this.inputLines[i + 1][j + 1] + "" + this.inputLines[i + 2][j + 2]
                var backward = this.inputLines[i][j + 2] + "" + this.inputLines[i + 1][j + 1] + "" + this.inputLines[i + 2][j]
                if ((forward == "MAS" || forward == "SAM") && (backward == "MAS" || backward == "SAM"))
                    crossCount++
            }
        }
        return crossCount
    }
}

fun main() {
    val fileInput = Path("src/main/resources/inputDay04a.txt").readLines()
    val aocDay04 = AOCDay04(fileInput)
    println(aocDay04.getTotalCount())
    println(aocDay04.getXCrossCount())
}