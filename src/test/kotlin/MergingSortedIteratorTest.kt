import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MergingSortedIteratorTest {

    @Test
    fun empty() {
        val i1 = emptyList<String>().iterator()
        val i2 = emptyList<String>().iterator()

        assertEquals(expected = false, actual = MergingSortedIterator(listOf(i1, i2)).hasNext())
    }

    @Test
    fun halfEmpty() {
        val i1 = emptyList<String>().iterator()
        val i2 = listOf("1", "2", "3").iterator()

        val sorted = mutableListOf<String>()
        MergingSortedIterator(listOf(i1, i2)).forEach { sorted += it }
        assertEquals(expected = listOf("1", "2", "3"), actual = sorted)
    }

    @Test
    fun filled() {
        val i1 = listOf(10, 20, 30).iterator()
        val i2 = listOf(1, 200, 3000).iterator()

        val sorted = mutableListOf<Int>()
        MergingSortedIterator(listOf(i1, i2)).forEach { sorted += it }

        assertEquals(expected = listOf(1, 10, 20, 30, 200, 3000), actual = sorted)
    }
}
