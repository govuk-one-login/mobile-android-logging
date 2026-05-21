package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import uk.gov.logging.impl.crashlytics.TestFirebaseCrashlyticsWrapper
import uk.gov.logging.impl.crashlytics.TestFirebaseCrashlyticsWrapper.RecordedException
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CrashlyticsLoggerTest {
    private val crashlytics = TestFirebaseCrashlyticsWrapper()
    private val crashLogger = CrashlyticsLogger(crashlytics)

    @Test
    fun `log single string message on firebase crashlytics`() {
        val logMessage = "log message"

        crashLogger.log(logMessage)

        assertEquals(listOf(logMessage), crashlytics.loggedMessages)
    }

    @Test
    fun `log throwable message on firebase crashlytics`() {
        crashLogger.log(exception)

        assertEquals(
            listOf(RecordedException(exception, emptyMap())),
            crashlytics.recordedExceptions,
        )
    }

    @ParameterizedTest(name = "{index}: test key {2} with value {3}")
    @MethodSource("setUpTestValues")
    fun `test error keys set on firebase crashlytics`(
        throwable: Throwable,
        errorKeys: ErrorKeys,
        expectedKey: String,
        expectedValue: String,
    ) {
        crashLogger.log(throwable, errorKeys)

        assertEquals(
            listOf(RecordedException(throwable, mapOf(expectedKey to expectedValue))),
            crashlytics.recordedExceptions,
        )
    }

    @Test
    fun `can be constructed with FirebaseCrashlytics`() {
        val firebaseCrashlytics: FirebaseCrashlytics = mock()

        assertNotNull(CrashlyticsLogger(firebaseCrashlytics))
    }

    companion object {
        const val KEY = "key"

        const val STRING_VALUE = "error"
        val stringErrorKey = ErrorKeys.StringKey(KEY, STRING_VALUE)

        const val INT_VALUE = 1
        val intErrorKey = ErrorKeys.IntKey(KEY, INT_VALUE)

        const val DOUBLE_VALUE = 1.0

        val doubleErrorKey = ErrorKeys.DoubleKey(KEY, DOUBLE_VALUE)

        const val BOOLEAN_VALUE = false

        val booleanErrorKey = ErrorKeys.BooleanKey(KEY, BOOLEAN_VALUE)

        val exception = Exception("error throwable")

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
