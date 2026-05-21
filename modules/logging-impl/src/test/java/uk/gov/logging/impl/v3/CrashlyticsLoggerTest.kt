package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.api.v3.customkey.ErrorKeys
import java.util.stream.Stream

class CrashlyticsLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()
    private val logger = CrashlyticsLogger(firebaseCrashlytics)
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")
    private val errorKeys = ErrorKeys(component = "my.component", action = "my.action")

    @Test
    fun `entries with allowRemote false are not logged`() {
        logger.log(LogEntry.Debug(tag = tag, message = message), LoggingProperties(allowRemote = false))

        verifyNoInteractions(firebaseCrashlytics)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("messageEntries")
    fun `message entry logs formatted message`(
        entry: LogEntry,
        expectedSymbol: String,
    ) {
        logger.log(entry, LoggingProperties(allowRemote = true))

        verify(firebaseCrashlytics).log(eq("$expectedSymbol : $tag : $message"))
    }

    @Test
    fun `exception entry logs message and records exception`() {
        logger.log(
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
            ),
            LoggingProperties(allowRemote = true),
        )

        verify(firebaseCrashlytics).log(eq("E : $tag : $message"))
        verify(firebaseCrashlytics).recordException(eq(throwable))
    }

    @Test
    fun `exception entry with error keys records exception then clears keys`() {
        logger.log(
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
                errorKeys = errorKeys,
            ),
            LoggingProperties(allowRemote = true),
        )

        inOrder(firebaseCrashlytics).apply {
            verify(firebaseCrashlytics).setCustomKey(eq("err_component"), eq("my.component"))
            verify(firebaseCrashlytics).setCustomKey(eq("err_action"), eq("my.action"))
            verify(firebaseCrashlytics).recordException(eq(throwable))
            verify(firebaseCrashlytics).setCustomKey(eq("err_component"), eq(""))
            verify(firebaseCrashlytics).setCustomKey(eq("err_action"), eq(""))
        }
    }

    @Test
    fun `exception entry with default keys records exception then clears keys`() {
        logger.log(
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
            ),
            LoggingProperties(allowRemote = true),
        )

        inOrder(firebaseCrashlytics).run {
            verify(firebaseCrashlytics).setCustomKey(eq("err_component"), eq(""))
            verify(firebaseCrashlytics).setCustomKey(eq("err_action"), eq(""))
            verify(firebaseCrashlytics).recordException(eq(throwable))
            verify(firebaseCrashlytics).setCustomKey(eq("err_component"), eq(""))
            verify(firebaseCrashlytics).setCustomKey(eq("err_action"), eq(""))
        }
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
