package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customKeys.CustomKeys
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable
import java.util.stream.Stream

class CrashlyticsLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()

    private val crashLogger: CrashLogger by lazy {
        CrashlyticsLogger(firebaseCrashlytics)
    }

    @Test
    fun `log single string message on firebase crashlytics`() {
        val logMessage = "log message"
        crashLogger.log(logMessage)
        verify(firebaseCrashlytics).log(eq(logMessage))
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `log throwable message and custom keys on firebase crashlytics `(
        throwable: Throwable,
        customKeys: CustomKeys,
        expectedKey: String,
        expectedValue: String,
    ) {
        crashLogger.log(throwable, customKeys)
        verify(firebaseCrashlytics).recordException(eq(throwable))
        verify(firebaseCrashlytics).setCustomKey(eq(expectedKey), eq(expectedValue))
    }

    @Test
    fun `log throwable message on firebase crashlytics `() {
        crashLogger.log(exception)
        verify(firebaseCrashlytics).recordException(eq(exception))
    }

    @Test
    fun `log error message with log message and log tag on firebase crashlytics`() {
        crashLogger.error(
            logTag,
            logMessage,
        )
        verify(firebaseCrashlytics).log("E : $logTag : $logMessage")
    }

    @Test
    fun `log error message with log tag and log message on firebase crashlytics with no error keys parsed`() {
        crashLogger.error(
            logTag,
            logMessage,
            exception,
        )
        verify(firebaseCrashlytics).recordException(eq(exception))
    }

    @Test
    fun `log error message with log tag and log message on firebase crashlytics with null error keys`() {
        crashLogger.error(
            logTag,
            logMessage,
            exception,
            null,
        )
        verify(firebaseCrashlytics).recordException(eq(exception))
    }

    @Test
    fun `log basic entries on firebase crashlytics  without throwable`() {
        crashLogger.log(listBasicEntries)

        verify(firebaseCrashlytics).log("I : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("W : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("E : $logTag : $logMessage")
    }

    @Test
    fun ` log entries with exception and custom keys or no custom keys firebase records twice`() {
        crashLogger.log(listEntriesWithException)
        verify(firebaseCrashlytics, times(2)).recordException(eq(logThrowable))
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `test log error message with log tag  and set custom key on firebase crashlytics  `(
        throwable: Throwable,
        customKeys: CustomKeys,
        expectedKey: String,
        expectedValue: String,
    ) {
        crashLogger.error(
            logTag,
            logMessage,
            throwable,
            customKeys,
        )
        verify(firebaseCrashlytics).recordException(eq(exception))

        verify(firebaseCrashlytics).setCustomKey(eq(expectedKey), eq(expectedValue))
    }

    companion object {
        const val KEY = "key"

        const val STRING_VALUE = "error"
        val stringErrorKey = CustomKeys.StringKey(KEY, STRING_VALUE)

        const val INT_VALUE = 1
        val intErrorKey = CustomKeys.IntKey(KEY, INT_VALUE)

        const val DOUBLE_VALUE = 1.0

        val doubleErrorKey = CustomKeys.DoubleKey(KEY, DOUBLE_VALUE)

        const val BOOLEAN_VALUE = false

        val booleanErrorKey = CustomKeys.BooleanKey(KEY, BOOLEAN_VALUE)

        val exception = Exception("error throwable")

        val listBasicEntries =
            listOf(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.INFO,
                ),
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.WARN,
                ),
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                ),
            )

        val listEntriesWithException =
            listOf(
                LogEntry.Error(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                    throwable = logThrowable,
                    customKeys = listOf(intErrorKey, stringErrorKey),
                ),
                LogEntry.Error(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                    throwable = logThrowable,
                    customKeys = null,
                ),
            )

        @JvmStatic
        fun setUpTestValues(): Stream<Arguments> =
            Stream.of(
                arguments(
                    exception,
                    stringErrorKey,
                    KEY,
                    STRING_VALUE,
                ),
                arguments(
                    exception,
                    intErrorKey,
                    KEY,
                    INT_VALUE.toString(),
                ),
                arguments(
                    exception,
                    doubleErrorKey,
                    KEY,
                    DOUBLE_VALUE.toString(),
                ),
                arguments(
                    exception,
                    booleanErrorKey,
                    KEY,
                    BOOLEAN_VALUE.toString(),
                ),
            )
    }
}
