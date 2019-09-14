import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class GenerateTest {

    @Test
    fun empty(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 0, maxLineLen = 0)
        assertEquals("", f.readText())
    }

    @Test
    fun singleLine(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 1, maxLineLen = 100)
        assertEquals(expected = 1, actual = f.readLines().size)
    }

    @Test
    fun twoLines(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        generate(f, lines = 2, maxLineLen = 10000)
        assertEquals(expected = 2, actual = f.readLines().size)
    }
}
