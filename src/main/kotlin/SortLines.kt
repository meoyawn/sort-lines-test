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

/**
 * each item in [iterators] must be sorted
 * then the resulting iterator is sorted too
 */
class MergingIterator<T : Comparable<T>>(private val iterators: List<Iterator<T>>) : Iterator<T> {

    private val cache = MutableList<T?>(iterators.size) { null }

    override fun hasNext(): Boolean =
        cache.any { it != null } || iterators.any { it.hasNext() }

    override fun next(): T {
        var min: T? = null
        var minI = -1

        for (i in iterators.indices) {
            val sorted = iterators[i]

            if (cache[i] == null && sorted.hasNext()) {
                cache[i] = sorted.next()
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

/**
 * Sorts a large text file line by line
 *
 * Needs additional fileSize * 2 disk space to work. Clears tmp files after it's finished
 *
 * @return new sorted file. Original still kept on the disk
 */
fun sortLines(file: File): File {
    val tmpDir = File(file.parentFile, "tmp")
    tmpDir.mkdir()

    try {
        // read chunks into memory, sort them
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
                        w.newLine()
                    }
                    lines.lastOrNull()?.let { w.write(it) }
                }

                lines.clear()

                chunkNum++
            }
        }

        // merge chunks
        val readers = tmpDir.listFiles()!!.map { it.bufferedReader() }
        try {
            val sorted = File(file.parentFile, "${file.nameWithoutExtension}_sorted.txt")
            sorted.bufferedWriter().use { w ->
                MergingIterator(readers.map { it.lineSequence().iterator() }).forEach {
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
