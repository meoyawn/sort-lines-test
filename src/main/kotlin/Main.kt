import java.io.File

fun main() {
    val original = File("foo.txt")

    // around 5GB
    generate(file = original, lines = 1_000_000, maxLineLen = 10_000)

    val sorted = sortLines(original)

    check(isSorted(sorted)) { "file is not sorted" }
}
