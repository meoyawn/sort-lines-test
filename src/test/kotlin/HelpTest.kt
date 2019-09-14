import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class HelpTest {

    @Test
    fun genEmpty(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 0, maxLineLen = 0)
        assertEquals("", f.readText())
    }

    @Test
    fun genSingleLine(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 1, maxLineLen = 100)
        assertEquals(expected = 1, actual = f.readLines().size)
    }

    @Test
    fun genTwoLines(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 2, maxLineLen = 10000)
        assertEquals(expected = 2, actual = f.readLines().size)
    }

    @Test
    fun emptySorted(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        f.createNewFile()
        assertEquals(expected = true, actual = isSorted(f))
    }

    @Test
    fun sorted(@TempDir dir: File) {
        val f = File(dir, "foo.txt")

        f.printWriter().use {
            it.println("1")
            it.println("2")
            it.println("500")
        }

        assertEquals(expected = true, actual = isSorted(f))
    }

    @Test
    fun notSorted(@TempDir dir: File) {
        val f = File(dir, "foo.txt")

        f.printWriter().use {
            it.println("5")
            it.println("1")
            it.println("2")
            it.println("500")
        }

        assertEquals(expected = false, actual = isSorted(f))
    }
}
