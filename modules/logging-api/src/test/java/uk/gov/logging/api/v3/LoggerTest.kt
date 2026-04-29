package uk.gov.logging.api.v3

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LoggingTestData.LOG_MESSAGE
import uk.gov.logging.api.v3.LoggingTestData.LOG_TAG
import uk.gov.logging.api.v3.LoggingTestData.intCustomKey
import uk.gov.logging.api.v3.LoggingTestData.logThrowable
import uk.gov.logging.api.v3.LoggingTestData.messageEntries

class LoggerTest {
    private val entries = mutableListOf<LogEntry>()
    private lateinit var logger: Logger

    @BeforeEach
    fun setUp() {
        entries.clear()
        logger = Logger { entries.add(it) }
    }

    @Test
    fun `info creates an Info entry`() {
        logger.info(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Info(tag = LOG_TAG, message = LOG_MESSAGE)))
    }

    @Test
    fun `debug is local only and filtered from SAM`() {
        logger.debug(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, hasSize(0))
    }

    @Test
    fun `verbose is local only and filtered from SAM`() {
        logger.verbose(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, hasSize(0))
    }

    @Test
    fun `warning creates a Warn entry`() {
        logger.warning(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Warn(tag = LOG_TAG, message = LOG_MESSAGE)))
    }

    @Test
    fun `error creates an Error entry with throwable`() {
        logger.error(tag = LOG_TAG, message = LOG_MESSAGE, throwable = logThrowable)

        assertThat(
            entries,
            contains(
                LogEntry.Error(tag = LOG_TAG, message = LOG_MESSAGE, throwable = logThrowable),
            ),
        )
    }

    @Test
    fun `error creates an Error entry with custom keys`() {
        logger.error(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
            intCustomKey,
        )

        assertThat(
            entries,
            contains(
                LogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    throwable = logThrowable,
                    customKeys = listOf(intCustomKey),
                ),
            ),
        )
    }

    @Test
    fun `log with iterable logs all entries`() {
        logger.log(messageEntries)

        assertThat(entries, hasSize(messageEntries.size))
        assertThat(entries, contains(*messageEntries.toTypedArray()))
    }

    @Test
    fun `log with vararg logs all entries`() {
        val info = LogEntry.Info(tag = LOG_TAG, message = LOG_MESSAGE)
        val debug = LogEntry.Debug(tag = LOG_TAG, message = LOG_MESSAGE)

        logger.log(info, debug)

        assertThat(entries, contains(info, debug))
    }
}
