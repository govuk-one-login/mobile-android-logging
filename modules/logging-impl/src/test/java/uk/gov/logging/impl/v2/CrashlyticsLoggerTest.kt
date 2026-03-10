package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
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

    @Test
    fun `log throwable message on firebase crashlytics`() {
        crashLogger.log(exception)
        verify(firebaseCrashlytics).recordException(eq(exception))
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

        verify(firebaseCrashlytics).setCustomKey(eq(expectedKey), eq(expectedValue))
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
