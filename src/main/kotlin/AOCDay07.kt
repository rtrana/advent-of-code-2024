import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay07() {

    private var calibrationsMap: MutableMap<Long, List<Long>> = mutableMapOf()

    class TreeNode(var value: Long, var loc: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    fun parseInputToMap(input: List<String>) {
        input.map { it.split(": ") }
            .forEach { it ->
                this.calibrationsMap[it[0].toLong()] =
                it[1].split(" ").map { it.toLong() } }
    }

    fun totalCalibrationResults(): Long {
        return this.calibrationsMap.filter { traverseOperations(it.value, it.key) }
            .keys.toMutableList().sumOf { it}
    }

    fun traverseOperations(calibrations: List<Long>, targetValue: Long): Boolean {
        val stack = Stack<TreeNode>()
        var i = 0
        var current = TreeNode(calibrations[i], i)
        stack.push(current)
        var targetMet = false
        while ((current.value != -1L || stack.isNotEmpty()) && !targetMet) {
            if (i != calibrations.size - 1 && current.value <= targetValue) {
                i++
                current.left = TreeNode(current.value * calibrations[i], i)
                current.right = TreeNode(current.value + calibrations[i], i)
                stack.push(current.right)
                stack.push(current.left)
            } else {
                if (current.value == targetValue) {
                    targetMet = true
                }
            }

            if (stack.isNotEmpty()) {
                current = stack.pop()
                i = current.loc
            } else current.value = -1L
        }

        return targetMet
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay07.txt").readLines()
    var aoc = AOCDay07()
    aoc.parseInputToMap(fileInput)
    println(aoc.totalCalibrationResults())
}