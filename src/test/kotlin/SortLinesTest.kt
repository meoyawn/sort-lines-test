import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class SortLinesTest {

    @Test
    fun empty(@TempDir dir: File) {
        val inp = File(dir, "foo.txt")
        inp.createNewFile()
        val out = File(dir, "bar.txt")

        sortLines(inp, out)

        assertEquals(expected = "", actual = out.readText())
    }

    @Test
    fun singleLine(@TempDir dir: File) {
        val f = File(dir, "foo.txt")
        f.printWriter().use { it.println("500") }

        val out = File(dir, "bar.txt")
        sortLines(f, out)
        assertEquals(expected = "500\n", actual = out.readText())
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

        val out = File(dir, "bar.txt")
        sortLines(f, out)

        assertEquals(
            expected = """
                        1
                        2
                        3
                        500
                        
                        """.trimIndent(),
            actual = out.readText()
        )
    }
}
