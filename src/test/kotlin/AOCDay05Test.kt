import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay05Test {

    private val rulesInput = Path("src/main/resources/inputDay05.txt").readLines()
    private val updates = Path("src/main/resources/inputDay05-order.txt").readLines()
    private val aoc = AOCDay05()

    @BeforeEach
    fun setUp() {
        aoc.parseInputToRulesMap(rulesInput)
        aoc.parseInputToUpdates(updates)
    }

    @Test
    fun `should determine if update is correctly ordered`() {
        assertTrue(aoc.isCorrectlyOrdered(listOf(75,47,61,53,29)))
        assertTrue(aoc.isCorrectlyOrdered(listOf(97,61,53,29,13)))
        assertTrue(aoc.isCorrectlyOrdered(listOf(75,29,13)))
        assertFalse(aoc.isCorrectlyOrdered(listOf(75,97,47,61,53)))
        assertFalse(aoc.isCorrectlyOrdered(listOf(61,13,29)))
        assertFalse(aoc.isCorrectlyOrdered(listOf(97,13,75,29,47)))
    }

    @Test
    fun `should fix incorrectly ordered updates`() {
        assertEquals(listOf<Long>(97,75,47,61,53), aoc.fixIncorrectUpdate(listOf(75,97,47,61,53)))
        assertEquals(listOf<Long>(61, 29, 13), aoc.fixIncorrectUpdate(listOf(61,13,29)))
        assertEquals(listOf<Long>(97,75,47,29,13), aoc.fixIncorrectUpdate(listOf(97,13,75,29,47)))
    }

    @Test
    fun `should find sum of middle correct updates`() {
        assertEquals(143L, aoc.findSumOfMiddleCorrectUpdates())
    }

    @Test
    fun `should find sum of middle fixed updates`() {
        assertEquals(123L, aoc.findSumOfFixedMiddleUpdates())
    }


}