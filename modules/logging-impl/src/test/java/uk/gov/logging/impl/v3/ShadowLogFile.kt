package uk.gov.logging.impl.v3

import org.junit.rules.TemporaryFolder
import org.robolectric.shadows.ShadowLog
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.Charset

/**
 * [TemporaryFolder] implementation that creates a log file for assertions.
 *
 * Use in classes that update robolectric configurations with [ShadowLog].
 */
class ShadowLogFile(
    private val fileName: String,
    private val fileExtension: String = ".txt",
) : TemporaryFolder(),
    Iterable<String> {
    private lateinit var loggingFile: File
    private lateinit var fileOutputStream: FileOutputStream
    private lateinit var printStream: PrintStream

    override fun before() {
        super.before()
        loggingFile = newFile("$fileName$fileExtension")
        fileOutputStream = FileOutputStream(loggingFile)
        printStream = PrintStream(fileOutputStream)

        ShadowLog.stream = printStream
    }

    override fun after() {
        fileOutputStream.close()
        printStream.close()
        super.after()
    }

    override fun iterator(): Iterator<String> = readLines().iterator()

    fun readLines(charset: Charset = Charsets.UTF_8) = loggingFile.readLines(charset)
}
