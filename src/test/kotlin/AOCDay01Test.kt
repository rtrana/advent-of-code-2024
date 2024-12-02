import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class AOCDay01Test {

    @Test
    fun `shouldReturnTwoListsOfLongs`() {
        val input = listOf("2   3", "4   2","1   5")
        assertEquals(listOf<Long>(2, 4, 1), AOCDay01().getInputAsLongs(input)[0])
        assertEquals(listOf<Long>(3, 2, 5), AOCDay01().getInputAsLongs(input)[1])
    }

    @Test
    fun `shouldGetDifferenceSumOfTwoLists`() {
        val input = listOf(listOf<Long>(2, 4, 1), listOf<Long>(3, 2, 5))
        assertEquals(3, AOCDay01().getDifferenceSumOfOrderedLists(input))
    }

    @Test
    fun `shouldGetSimilarityScore`() {
        val input = listOf(listOf<Long>(3, 4, 2, 1, 3, 3), listOf<Long>(4, 3, 5, 3, 9, 3))
        assertEquals(31, AOCDay01().getSimilarityScore(input))
    }
}