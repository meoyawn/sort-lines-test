import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class SortLinesTest {

    @Test
    fun empty(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        f.createNewFile()

        assertEquals(expected = "", actual = sortLines(f).readText())
    }

    @Test
    fun singleLine(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        f.printWriter().use { it.println("500") }
        assertEquals(expected = "500\n", actual = sortLines(f).readText())
    }

    @Test
    fun sorting(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        f.printWriter().use {
            it.println("500")
            it.println("2")
            it.println("1")
            it.println("3")
        }

        assertEquals(
            expected = """
                        1
                        2
                        3
                        500
                        
                        """.trimIndent(),
            actual = sortLines(f).readText()
        )
    }
}
