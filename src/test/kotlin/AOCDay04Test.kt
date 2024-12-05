import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines

class AOCDay04Test {

    private val fileInput = Path("src/main/resources/inputDay04.txt").readLines()

    @Test
    fun `should find count of XMAS or SAMX horizontally`() {
        val puzzle = listOf("MMMSXXMASM", "XMASAMXAMM")
        assertEquals(3L, AOCDay04(puzzle).getHorizontalCount())
    }

    @Test
    fun `should find count of XMAS or SAMX vertically`() {
        assertEquals(3L, AOCDay04(fileInput).getVerticalCount())
    }

    @Test
    fun `should find count of XMAS or SAMX forward diagonally`() {
        assertEquals(5L, AOCDay04(fileInput).getForwardDiagonalCount())
    }

    @Test
    fun `should find count of XMAS or SAMX backward diagonally`() {
        assertEquals(5L, AOCDay04(fileInput).getBackwardDiagonalCount())
    }

    @Test
    fun `should sum up all directions for total XMAS or SAMX count`() {
        assertEquals(18L, AOCDay04(fileInput).getTotalCount())
    }

    @Test
    fun `should find count of X-MAS cross count`() {
        assertEquals(9L, AOCDay04(fileInput).getXCrossCount())
    }
}