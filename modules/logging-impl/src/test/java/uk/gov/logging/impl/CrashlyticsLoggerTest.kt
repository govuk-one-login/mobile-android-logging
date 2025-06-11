package uk.gov.logging.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable

internal class CrashlyticsLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()

    private val crashLogger: CrashLogger by lazy {
        CrashlyticsLogger(firebaseCrashlytics)
    }

    @Test
    fun `Logging messages defers to Firebase`() {
        crashLogger.log(logMessage)
        verify(firebaseCrashlytics).log(eq(logMessage))
    }

    @Test
    fun `Logging throwables defers to Firebase`() {
        crashLogger.log(logThrowable)
        verify(firebaseCrashlytics).recordException(eq(logThrowable))
    }
}
