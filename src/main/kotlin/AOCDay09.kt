import kotlin.io.path.Path
import kotlin.io.path.readText
class AOCDay09 {

    var fileCount: MutableList<Int> = mutableListOf()
    var spaceCount: MutableList<Int> = mutableListOf()
    var expandedFormat: MutableList<String> = mutableListOf()

    fun parseDiskMapFormat(diskFormat: String) {
        var index = 0
        for (i in diskFormat.indices) {
            if (i % 2 == 0) {
                expandDiskMapFormat(diskFormat, fileCount, i, "$index")
                index++
            } else
                expandDiskMapFormat(diskFormat, spaceCount, i, ".")
        }
    }

    private fun expandDiskMapFormat(diskFormat: String, counter: MutableList<Int>, i: Int, valueToExpand: String) {
        var total = ("" + diskFormat[i]).toInt()
        counter.add((total))
        for (count in 0..< total) {
            this.expandedFormat.add("" + valueToExpand)
        }
    }

    fun compressDiskFormat(): List<String> {
        var compressedFormat: MutableList<String> = this.expandedFormat.toMutableList()
        var i = 0
        var j = compressedFormat.size - 1
        while (i < j) {
            if (compressedFormat[i] == "." && compressedFormat[j] != ".") {
                compressedFormat[i] = compressedFormat[j]
                compressedFormat[j] = "."
                i++
                j--
            } else if (compressedFormat[j] == ".") j--
            else i++
        }
        return compressedFormat
    }

    fun calculateChecksum(compressedFormat: List<String>): Long {
        var i = 0
        var checksum: Long = 0
        while (i < compressedFormat.size) {
            if (compressedFormat[i] != ".")
                checksum += (compressedFormat[i].toLong() * i)
            i++
        }
        return checksum
    }

    fun compressDiskFormatOptimized(): List<String> {
        var compressedFormat: MutableList<String> = mutableListOf()
        var spaceValues: MutableList<MutableList<String>> = MutableList(this.spaceCount.size) { mutableListOf() }
        var fileValues: MutableList<MutableList<String>> = MutableList(this.fileCount.size) { mutableListOf() }
        var j = this.fileCount.size - 1
        while (j >= 0) {
            var count = this.fileCount[j]
            var index = this.spaceCount.indexOfFirst { it >= count }
            if (index != -1 && index < j) {
                for (k in 1 .. count) {
                    spaceValues[index].add("" + j)
                    fileValues[j].add(".")
                }
                this.spaceCount[index] -= count
            } else {
                for (k in 1 .. count) {
                    fileValues[j].add("" + j)
                }
            }
            j--
        }

        for (i in this.fileCount.indices) {
            for (fileValue in fileValues[i]) {
                compressedFormat.add(fileValue)
            }
            if (i < this.spaceCount.size) {
                for (spaceValue in spaceValues[i]) {
                    compressedFormat.add(spaceValue)
                }
                for (k in 1..spaceCount[i]) {
                    compressedFormat.add(".")
                }
            }
        }
        return compressedFormat
    }
}


fun main() {
    var fileInput = Path("src/main/resources/inputDay09a.txt").readText()
    var aoc = AOCDay09()
    aoc.parseDiskMapFormat(fileInput)
    println(aoc.calculateChecksum(aoc.compressDiskFormat()))
    println(aoc.calculateChecksum(aoc.compressDiskFormatOptimized()))
}