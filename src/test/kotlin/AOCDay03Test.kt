import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AOCDay03Test {

    @Test
    fun `should parse input to get multipliers as list of longs`() {
        val puzzle = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        val expected = listOf(listOf(2L,4L), listOf(5L,5L), listOf(11L,8L), listOf(8L,5L))
        assertEquals(expected, AOCDay03().parseInputToIndividualMultipliers(puzzle))
    }

    @Test
    fun `should parse input to get enabled multipliers as list of longs`() {
        val puzzle = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        val expected = listOf(listOf(2L,4L), listOf(8L,5L))
        assertEquals(expected, AOCDay03().parseInputToEnabledMultipliers(puzzle))
    }

    @Test
    fun `should sum up multiplication of lists of longs`() {
        val multipliers = listOf(listOf(2L,4L), listOf(5L,5L), listOf(11L,8L), listOf(8L,5L))
        assertEquals(161, AOCDay03().multiplyMultipliersAndSum(multipliers))
    }

}