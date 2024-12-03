import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class AOCDay02() {

    fun getReportsAsLongs(input: List<String>): List<List<Long>> {
        var reports = mutableListOf<List<Long>>()
        input.forEach{ it: String ->
            reports.add(it.split(" ").map { it.toLong() })
        }
        return reports
    }

    fun isMonotonicBoundedSequence(report: List<Long>): Boolean {
        var isIncreasing = true
        var isDecreasing = true

        val iterator = report.listIterator()
        var previous = iterator.next()
        while (iterator.hasNext()) {
            val current = iterator.next()
            if (current - previous in -3L .. -1L) isIncreasing = false
            else if (current - previous in 1L..3L) isDecreasing = false
            else {
                isIncreasing = false
                isDecreasing = false
            }
            previous = current
        }

        return (isIncreasing || isDecreasing)
    }

    fun isMonotonicBoundedDampenedSequence(report: List<Long>): Boolean {
        var i = 0
        var isDampened = false
        while (i < report.size && !isDampened) {
            var reportCopy = report.toMutableList()
            reportCopy.removeAt(i)
            isDampened = isMonotonicBoundedSequence(reportCopy)
            i++
        }
        return isDampened
    }

    fun countAllSafeReports(reports: List<List<Long>>): Long {
        return reports.count { isMonotonicBoundedSequence(it) }.toLong()
    }

    fun countAllSafeDampenedReports(reports: List<List<Long>>): Long {
        var nonMonotonicBoundedReports = reports.filterNot { isMonotonicBoundedSequence(it) }
        return countAllSafeReports(reports) + nonMonotonicBoundedReports
            .count { isMonotonicBoundedDampenedSequence(it)}.toLong()
    }
}

fun main() {
    var fileInput = Path("src/main/resources/inputDay02a.txt").readLines()
    var reports = AOCDay02().getReportsAsLongs(fileInput)
    println(AOCDay02().countAllSafeReports(reports))
    println(AOCDay02().countAllSafeDampenedReports(reports))
}