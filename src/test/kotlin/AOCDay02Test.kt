import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AOCDay02Test {

    @Test
    fun `should determine if report is all increasing or decreasing`() {
        val testReport1 = listOf<Long>(7, 6, 4, 2, 1)
        val testReport2 = listOf<Long>(8, 6, 4, 4, 1)
        val testReport3 = listOf<Long>(1, 3, 6, 7, 9)
        assertEquals(true, AOCDay02().isMonotonicBoundedSequence(testReport1))
        assertEquals(false, AOCDay02().isMonotonicBoundedSequence(testReport2))
        assertEquals(true, AOCDay02().isMonotonicBoundedSequence(testReport3))
    }

    @Test
    fun `should add report difference bounding ranges to increasing and decreasing check`() {
        val testReport1 = listOf<Long>(1, 2, 7, 8, 9)
        val testReport2 = listOf<Long>(9, 7, 6, 2, 1)
        val testReport3 = listOf<Long>(1, 3, 2, 4, 5)
        assertEquals(false, AOCDay02().isMonotonicBoundedSequence(testReport1))
        assertEquals(false, AOCDay02().isMonotonicBoundedSequence(testReport2))
        assertEquals(false, AOCDay02().isMonotonicBoundedSequence(testReport3))
    }

    @Test
    fun `should count total safe reports`() {
        val testReports = listOf(
            listOf<Long>(7, 6, 4, 2, 1),
            listOf<Long>(8, 6, 4, 4, 1),
            listOf<Long>(1, 3, 6, 7, 9),
            listOf<Long>(1, 2, 7, 8, 9),
            listOf<Long>(9, 7, 6, 2, 1),
            listOf<Long>(1, 3, 2, 4, 5)
        )
        assertEquals(2L, AOCDay02().countAllSafeReports(testReports))
    }

    @Test
    fun `should determine if report is dampened safe`() {
        val testReport1 = listOf<Long>(7, 6, 4, 2, 1)
        val testReport2 = listOf<Long>(1, 2, 7, 8, 9)
        val testReport3 = listOf<Long>(9, 7, 6, 2, 1)
        val testReport4 = listOf<Long>(1, 3, 2, 4, 5)
        val testReport5 = listOf<Long>(8, 6, 4, 4, 1)
        val testReport6 = listOf<Long>(1, 3, 6, 7, 9)
        val testReport7 = listOf<Long>(22, 18, 17, 15, 14, 12)
        assertEquals(true, AOCDay02().isMonotonicBoundedDampenedSequence(testReport1))
        assertEquals(false, AOCDay02().isMonotonicBoundedDampenedSequence(testReport2))
        assertEquals(false, AOCDay02().isMonotonicBoundedDampenedSequence(testReport3))
        assertEquals(true, AOCDay02().isMonotonicBoundedDampenedSequence(testReport4))
        assertEquals(true, AOCDay02().isMonotonicBoundedDampenedSequence(testReport5))
        assertEquals(true, AOCDay02().isMonotonicBoundedDampenedSequence(testReport6))
        assertEquals(true, AOCDay02().isMonotonicBoundedDampenedSequence(testReport7))
    }

    @Test
    fun `should count total safe reports with dampening`() {
        val testReports = listOf(
            listOf<Long>(7, 6, 4, 2, 1),
            listOf<Long>(8, 6, 4, 4, 1),
            listOf<Long>(1, 3, 6, 7, 9),
            listOf<Long>(1, 2, 7, 8, 9),
            listOf<Long>(9, 7, 6, 2, 1),
            listOf<Long>(1, 3, 2, 4, 5)
        )
        assertEquals(4L, AOCDay02().countAllSafeDampenedReports(testReports))
    }

}