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
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.BOOLEAN_VALUE
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.DOUBLE_VALUE
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.INT_VALUE
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.KEY
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.STRING_VALUE
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.booleanErrorKey
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.doubleErrorKey
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.exception
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.intErrorKey
import uk.gov.logging.impl.v3.CrashlyticsLoggerTest.Companion.stringErrorKey
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_MESSAGE
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_TAG
import java.util.stream.Stream

class MultiLoggerTest {
    private val firebaseCrashlytics: FirebaseCrashlytics = mock()

    private val remoteLogger: Logger by lazy {
        CrashlyticsLogger(firebaseCrashlytics)
    }

    private val localLogger: Logger by lazy {
        LogcatLogger
    }

    private lateinit var staticLogMock: MockedStatic<Log>

    private val multiLogger: Logger by lazy {
        MultiLogger(listOf(remoteLogger, localLogger))
    }

    private val multiLoggerSingle: Logger by lazy {
        MultiLogger(remoteLogger)
    }

    @BeforeEach
    fun setUp() {
        staticLogMock = Mockito.mockStatic(Log::class.java)
    }

    @AfterEach
    fun tearDown() {
        staticLogMock.close()
    }

    @Test
    fun `test multi logger logs remote entries to firebase Crashlytics`() {
        multiLogger.log(remoteEntries)

        verify(firebaseCrashlytics).log("I : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("W : $logTag : $logMessage")
        verify(firebaseCrashlytics).log("E : $logTag : $logMessage")
    }

    @Test
    fun `test multi logger logs local entries to Android  logcat logger`() {
        multiLogger.log(localEntries)

        staticLogMock.verify {
            Log.d(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
        staticLogMock.verify {
            Log.i(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }

        staticLogMock.verify {
            Log.w(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `log throwable message and custom keys on firebase crashlytics `(
        throwable: Throwable,
        customKey: CustomKey,
        expectedKey: String,
        expectedValue: String,
    ) {
        multiLoggerSingle.log(
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

    companion object {
        val remoteEntries =
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

        val localEntries =
            listOf<LocalLogEntry>(
                LocalLogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.DEBUG,
                ),
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
