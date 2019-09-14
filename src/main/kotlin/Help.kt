import java.io.File
import kotlin.random.Random

// unboxed chars
private val CHARS: CharArray =
    (('a'..'z') + ('A'..'Z') + ('0'..'9') + listOf(' ', ',', '.')).toCharArray()

fun generate(file: File, lines: Int, maxLineLen: Int): Unit =
    file.bufferedWriter().use { w ->
        repeat(lines) { _ ->
            repeat(Random.nextInt(maxLineLen)) { _ ->
                w.write(CHARS.random().toInt())
            }
            w.newLine()
        }
    }

fun isSorted(file: File): Boolean =
    file.bufferedReader().use { r ->
        var prev = ""

        while (true) {
            val current = r.readLine() ?: break

            if (current < prev) return false

            prev = current
        }

        true
    }
