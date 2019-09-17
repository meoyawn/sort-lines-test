import java.io.File

fun main(args: Array<String>): Unit =
    when (val cmd = args.getOrNull(0)?.toLowerCase() ?: error("expecting a command: generate | sort | verify")) {
        "generate" -> {
            val lines = args.getOrNull(1)?.toInt() ?: error("missing line count")
            val maxLineLen = args.getOrNull(2)?.toInt() ?: error("missing max line length")
            val path = args.getOrNull(3)?.let(::File) ?: error("missing output path")
            generate(file = path, lines = lines, maxLineLen = maxLineLen)
        }

        "sort" -> {
            val input = args.getOrNull(1)?.let(::File) ?: error("missing input path")
            val output = args.getOrNull(2)?.let(::File) ?: error("missing output path")
            sortLines(input, output)
        }

        "verify" -> {
            val input = args.getOrNull(1)?.let(::File) ?: error("missing file path")
            println(if (isSorted(input)) "SORTED" else "NOT SORTED")
        }

        else ->
            error("unexpected command $cmd")
    }
