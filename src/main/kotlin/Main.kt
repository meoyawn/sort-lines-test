import java.io.File

fun main() {
    val f = File("foo.txt")
    generate(file = f, lines = 1_000_000, maxLineLen = 10_000)
    sortLines(f)
}
