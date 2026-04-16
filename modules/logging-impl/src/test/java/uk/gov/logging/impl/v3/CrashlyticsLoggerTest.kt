package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_MESSAGE
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_TAG
import java.util.stream.Stream

class CrashlyticsLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()

    private val logger: Logger by lazy {
        CrashlyticsLogger(firebaseCrashlytics)
    }

    private lateinit var staticLogMock: MockedStatic<Log>

    @BeforeEach
    fun setUp() {
        staticLogMock = Mockito.mockStatic(Log::class.java)
    }

    @AfterEach
    fun tearDown() {
        staticLogMock.close()
    }

    @Test
    fun `log single info basic entry on firebase crashlytics`() {
        logger.info(logTag, logMessage)
        verify(firebaseCrashlytics).log(eq("I : $logTag : $logMessage"))
    }

    @Test
    fun `log single warn  basic entry message on firebase crashlytics `() {
        logger.warning(logTag, logMessage)
        verify(firebaseCrashlytics).log(eq("W : $logTag : $logMessage"))
    }

    @Test
    fun `log error message with log message and log tag on firebase crashlytics`() {
        logger.error(
            logTag,
            logMessage,
        )
        verify(firebaseCrashlytics).log("E : $logTag : $logMessage")
    }

    @Test
    fun `log error message with log tag and log message on firebase crashlytics with no error keys parsed`() {
        logger.error(
            logTag,
            logMessage,
            exception,
        )
        verify(firebaseCrashlytics).recordException(eq(exception))
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `log throwable message and custom keys on firebase crashlytics `(
        throwable: Throwable,
        customKey: CustomKey,
        expectedKey: String,
        expectedValue: String,
    ) {
        logger.log(
            LogEntry.Error(
                Log.ERROR,
                logMessage,
                logTag,
                throwable,
                customKeys = listOf(customKey),
            ),
        )

        verify(firebaseCrashlytics).recordException(eq(throwable))
        verify(firebaseCrashlytics).setCustomKey(eq(expectedKey), eq(expectedValue))
    }

    @Test
    fun `log basic entries on firebase crashlytics  without throwable`() {
        logger.log(listBasicEntries)

        verify(firebaseCrashlytics).log("I : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("W : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("E : $logTag : $logMessage")
    }

    @Test
    fun ` log entries with exception and custom keys or no custom keys firebase records twice`() {
        logger.log(listEntriesWithException)
        verify(firebaseCrashlytics, times(2)).recordException(eq(logThrowable))
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `test log error message with log tag  and set custom key on firebase crashlytics  `(
        throwable: Throwable,
        customKey: CustomKey,
        expectedKey: String,
        expectedValue: String,
    ) {
        logger.error(
            logTag,
            logMessage,
            throwable,
            customKey,
        )
        verify(firebaseCrashlytics).recordException(eq(exception))

        verify(firebaseCrashlytics).setCustomKey(eq(expectedKey), eq(expectedValue))
    }

    @Test
    fun ` Test no interaction on log local log entries on crash logger `() {
        logger.log(listLocalLogEntries)

        staticLogMock.verifyNoInteractions()
        staticLogMock.verifyNoInteractions()
        staticLogMock.verifyNoInteractions()
        staticLogMock.verifyNoInteractions()
        verifyNoInteractions(firebaseCrashlytics)
    }

    @Test
    fun `log entry with  debug  log level logged in  firebase Crashlytics zero interaction`() {
        logger.log(
            LogEntry.Basic(
                tag = LOG_TAG,
                message = LOG_MESSAGE,
                level = Log.DEBUG,
            ),
        )

        verifyNoInteractions(firebaseCrashlytics)
    }

    companion object {
        const val KEY = "key"

        const val STRING_VALUE = "error"
        val stringErrorKey = CustomKey.StringKey(KEY, STRING_VALUE)

        const val INT_VALUE = 1
        val intErrorKey = CustomKey.IntKey(KEY, INT_VALUE)

        const val DOUBLE_VALUE = 1.0

        val doubleErrorKey = CustomKey.DoubleKey(KEY, DOUBLE_VALUE)

        const val BOOLEAN_VALUE = false

        val booleanErrorKey = CustomKey.BooleanKey(KEY, BOOLEAN_VALUE)

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
                    customKeys = listOf(),
                ),
            )

        val listLocalLogEntries =
            listOf(
                LocalLogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.INFO,
                ),
                LocalLogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.WARN,
                ),
                LocalLogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                ),
                LocalLogEntry.Error(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                    throwable = logThrowable,
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
