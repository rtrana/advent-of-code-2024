import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay07Test {

    private val calibrations = Path("src/main/resources/inputDay07.txt").readLines()
    private val aoc = AOCDay07()

    @BeforeEach
    fun setUp() {
        aoc.parseInputToMap(calibrations)
    }

    @Test
    fun `should check if calibrations are possble with operations`() {
        assertTrue(AOCDay07().traverseOperations(listOf(10, 19), 190))
        assertTrue(AOCDay07().traverseOperations(listOf(81, 40, 27), 3267))
        assertFalse(AOCDay07().traverseOperations(listOf(17, 5), 83))
        assertFalse(AOCDay07().traverseOperations(listOf(15, 6), 156))
        assertFalse(AOCDay07().traverseOperations(listOf(6, 8, 6, 15), 7290))
        assertFalse(AOCDay07().traverseOperations(listOf(16, 10, 13), 161011))
        assertFalse(AOCDay07().traverseOperations(listOf(17, 8, 14), 192))
        assertFalse(AOCDay07().traverseOperations(listOf(9, 7, 18, 13), 21037))
        assertTrue(AOCDay07().traverseOperations(listOf(11, 6, 16, 20), 292))
    }

    @Test
    fun `should count total possible calibrations`() {
        assertEquals(3749, aoc.totalBinaryCalibrationResults())
    }

}
