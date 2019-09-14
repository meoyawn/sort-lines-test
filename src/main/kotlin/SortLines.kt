import java.io.BufferedReader
import java.io.File

private fun readLines(reader: BufferedReader, into: MutableList<String>): Boolean {
    val maxMem = Runtime.getRuntime().maxMemory()
    var bytesRead = 0L

    do {
        val line = reader.readLine() ?: return true
        into += line
        bytesRead += line.length * 2
    } while (bytesRead < maxMem)

    return false
}

class MergingSortedIterator<T : Comparable<T>>(private val iterators: List<Iterator<T>>) : Iterator<T> {

    private val cache = MutableList<T?>(iterators.size) { null }

    override fun hasNext(): Boolean =
        cache.any { it != null } || iterators.any { it.hasNext() }

    override fun next(): T {
        var min: T? = null
        var minI = -1

        for (i in iterators.indices) {
            val iterator = iterators[i]

            if (cache[i] == null && iterator.hasNext()) {
                cache[i] = iterator.next()
            }

            if (min == null) {
                min = cache[i]
                minI = i
            } else {
                cache[i]?.let {
                    if (it < min!!) {
                        min = it
                        minI = i
                    }
                }
            }
        }

        cache[minI] = null
        return min ?: error("exhaust")
    }
}

fun sortLines(file: File): File {
    val tmpDir = File(file.parentFile, "tmp")
    tmpDir.mkdir()

    try {
        file.bufferedReader().use { r ->
            var end = false
            var chunkNum = 0L
            val lines = mutableListOf<String>()

            while (!end) {
                end = readLines(r, lines)

                lines.sort()

                File(tmpDir, "${chunkNum}.txt").bufferedWriter().use { w ->
                    for (idx in 0 until lines.lastIndex) {
                        w.write(lines[idx])
                        w.write("\n")
                    }
                    lines.lastOrNull()?.let { w.write(it) }
                }

                lines.clear()

                chunkNum++
            }
        }

        val readers = tmpDir.listFiles()!!.map { it.bufferedReader() }
        try {
            val sorted = File(file.parentFile, "${file.nameWithoutExtension}_sorted.txt")
            sorted.bufferedWriter().use { w ->
                MergingSortedIterator(readers.map { it.lineSequence().iterator() }).forEach {
                    w.write(it)
                    w.newLine()
                }
            }
            return sorted
        } finally {
            readers.forEach(BufferedReader::close)
        }
    } finally {
        tmpDir.deleteRecursively()
    }
}
