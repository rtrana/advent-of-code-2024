import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay07() {

    private var calibrationsMap: MutableMap<Long, List<Long>> = mutableMapOf()

    class TreeNode(var value: Long, var loc: Int) {
        var children: MutableList<TreeNode> = mutableListOf()
    }

    fun parseInputToMap(input: List<String>) {
        input.map { it.split(": ") }
            .forEach { it ->
                this.calibrationsMap[it[0].toLong()] =
                it[1].split(" ").map { it.toLong() } }
    }

    fun totalBinaryCalibrationResults(): Long {
        return this.calibrationsMap.filter { traverseOperations(it.value, it.key) }
            .keys.toMutableList().sumOf { it}
    }

    fun totalCalibrationResults(): Long {
        return this.calibrationsMap.filter { traverseOperations(it.value, it.key, false) }
            .keys.toMutableList().sumOf { it}
    }

    fun traverseOperations(calibrations: List<Long>, targetValue: Long, hasBinaryChildren: Boolean = true): Boolean {
        val stack = Stack<TreeNode>()
        var i = 0
        var current = TreeNode(calibrations[i], i)
        stack.push(current)
        var targetMet = false
        while ((current.value != -1L || stack.isNotEmpty()) && !targetMet) {
            if (i != calibrations.size - 1 && current.value <= targetValue) {
                i++
                if (hasBinaryChildren)
                    addBinaryChildrenOperations(current, calibrations, i, stack)
                else
                    addMultipleChildrenOperations(current, calibrations, i, stack)
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

    private fun addBinaryChildrenOperations(current: TreeNode, calibrations: List<Long>, i: Int, stack: Stack<TreeNode>) {
        for (j in 0..1) {
            if (j == 1)
                current.children.add(TreeNode(current.value + calibrations[i], i))
            else
                current.children.add(TreeNode(current.value * calibrations[i], i))
            stack.push(current.children[j])
        }
    }

    private fun addMultipleChildrenOperations(current: TreeNode, calibrations: List<Long>, i: Int, stack: Stack<TreeNode>) {
        for (j in 0..2) {
            when (j) {
                2 -> current.children.add(TreeNode(current.value + calibrations[i], i))
                1 -> current.children.add(TreeNode(("" + current.value + "" + calibrations[i]).toLong(), i))
                else -> current.children.add(TreeNode(current.value * calibrations[i], i))
            }
            stack.push(current.children[j])
        }
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay07a.txt").readLines()
    var aoc = AOCDay07()
    aoc.parseInputToMap(fileInput)
    println(aoc.totalBinaryCalibrationResults())
    println(aoc.totalCalibrationResults())
}