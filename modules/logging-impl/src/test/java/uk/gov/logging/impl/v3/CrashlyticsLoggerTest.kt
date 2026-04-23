package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customkey.CustomKey

class CrashlyticsLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()
    private val logger = CrashlyticsLogger(firebaseCrashlytics)
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")
    private val customKey = CustomKey.StringKey("my_key", "my_value")

    @Test
    fun `local only entries are not logged`() {
        logger.log(LogEntry.Debug(tag = tag, message = message))

        verifyNoInteractions(firebaseCrashlytics)
    }

    @Test
    fun `message entry logs formatted message`() {
        logger.log(LogEntry.Info(tag = tag, message = message))

        verify(firebaseCrashlytics).log(eq("I : $tag : $message"))
    }

    @Test
    fun `exception entry logs message and records exception`() {
        logger.log(
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
            ),
        )

        verify(firebaseCrashlytics).log(eq("E : $tag : $message"))
        verify(firebaseCrashlytics).recordException(eq(throwable))
    }

    @Test
    fun `exception entry sets custom keys`() {
        logger.log(
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
                customKeys = listOf(customKey),
            ),
        )

        verify(firebaseCrashlytics).setCustomKey(eq(customKey.key), eq(customKey.value))
    }
}
