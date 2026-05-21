package uk.gov.logging.api.v3

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LoggingTestData.LOG_MESSAGE
import uk.gov.logging.api.v3.LoggingTestData.LOG_TAG
import uk.gov.logging.api.v3.LoggingTestData.errorKeys
import uk.gov.logging.api.v3.LoggingTestData.logThrowable
import uk.gov.logging.api.v3.LoggingTestData.messageEntries

class LoggerTest {
    private val entries = mutableListOf<LogEntry>()
    private val properties = mutableListOf<LoggingProperties>()
    private lateinit var logger: Logger

    @BeforeEach
    fun setUp() {
        entries.clear()
        properties.clear()
        logger =
            Logger { entry, props ->
                entries.add(entry)
                properties.add(props)
            }
    }

    @Test
    fun `info creates an Info entry with allowRemote true`() {
        logger.info(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Info(tag = LOG_TAG, message = LOG_MESSAGE)))
        assertThat(properties, contains(LoggingProperties(allowRemote = true)))
    }

    @Test
    fun `debug creates a Debug entry with allowRemote false`() {
        logger.debug(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Debug(tag = LOG_TAG, message = LOG_MESSAGE)))
        assertThat(properties, contains(LoggingProperties(allowRemote = false)))
    }

    @Test
    fun `verbose creates a Verbose entry with allowRemote false`() {
        logger.verbose(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Verbose(tag = LOG_TAG, message = LOG_MESSAGE)))
        assertThat(properties, contains(LoggingProperties(allowRemote = false)))
    }

    @Test
    fun `warning creates a Warn entry with allowRemote true`() {
        logger.warning(tag = LOG_TAG, message = LOG_MESSAGE)

        assertThat(entries, contains(LogEntry.Warn(tag = LOG_TAG, message = LOG_MESSAGE)))
        assertThat(properties, contains(LoggingProperties(allowRemote = true)))
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
        assertThat(properties, contains(LoggingProperties(allowRemote = true)))
    }

    @Test
    fun `error creates an Error entry with custom keys`() {
        logger.error(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
            errorKeys,
        )

        assertThat(
            entries,
            contains(
                LogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    throwable = logThrowable,
                    errorKeys = errorKeys,
                ),
            ),
        )
        assertThat(properties, contains(LoggingProperties(allowRemote = true)))
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
