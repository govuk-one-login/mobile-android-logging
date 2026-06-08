package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.impl.crashlytics.TestFirebaseCrashlyticsWrapper
import uk.gov.logging.impl.crashlytics.TestFirebaseCrashlyticsWrapper.RecordedException
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CrashlyticsLoggerTest {
    private val crashlytics = TestFirebaseCrashlyticsWrapper()
    private val logger = CrashlyticsLogger(crashlytics)
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")
    private val customKey = CustomKey.StringKey("my_key", "my_value")

    @Test
    fun `entries with allowRemote false are not logged`() {
        logger.log(LogEntry.Debug(tag = tag, message = message), LoggingProperties(allowRemote = false))

        assertTrue(crashlytics.loggedMessages.isEmpty())
        assertTrue(crashlytics.recordedExceptions.isEmpty())
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("messageEntries")
    fun `message entry logs formatted message`(
        entry: LogEntry,
        expectedSymbol: String,
    ) {
        logger.log(entry, LoggingProperties(allowRemote = true))

        assertEquals(listOf("$expectedSymbol : $tag : $message"), crashlytics.loggedMessages)
    }

    @Test
    fun `exception entry logs message and records exception`() {
        logger.log(
            LogEntry.Error(tag = tag, message = message, throwable = throwable),
            LoggingProperties(allowRemote = true),
        )

        assertEquals(listOf("E : $tag : $message"), crashlytics.loggedMessages)
        assertEquals(
            listOf(RecordedException(throwable, emptyMap())),
            crashlytics.recordedExceptions,
        )
    }

    @Test
    fun `exception entry records exception with custom keys`() {
        logger.log(
            LogEntry.Error(tag = tag, message = message, throwable = throwable, customKeys = listOf(customKey)),
            LoggingProperties(allowRemote = true),
        )

        assertEquals(listOf("E : $tag : $message"), crashlytics.loggedMessages)
        assertEquals(
            listOf(RecordedException(throwable, mapOf(customKey.key to customKey.value))),
            crashlytics.recordedExceptions,
        )
    }

    @Test
    fun `can be constructed with FirebaseCrashlytics`() {
        val firebaseCrashlytics: FirebaseCrashlytics = mock()

        assertNotNull(CrashlyticsLogger(firebaseCrashlytics))
    }

    companion object {
        private const val TAG = "tag"
        private const val MSG = "msg"

        @JvmStatic
        fun messageEntries(): Stream<Arguments> =
            Stream.of(
                arguments(LogEntry.Verbose(tag = TAG, message = MSG), "V"),
                arguments(LogEntry.Debug(tag = TAG, message = MSG), "D"),
                arguments(LogEntry.Info(tag = TAG, message = MSG), "I"),
                arguments(LogEntry.Warn(tag = TAG, message = MSG), "W"),
            )
    }
}
