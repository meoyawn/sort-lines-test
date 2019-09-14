import java.io.File
import kotlin.random.Random

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
