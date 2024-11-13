package uk.gov.logging.testdouble

import android.content.Context
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.testdouble.LoggingTestData.logMessage
import uk.gov.logging.testdouble.LoggingTestData.logThrowable
import java.util.stream.Stream

internal class FakeCrashLoggerTest {

    private val context: Context = mock()
    private val logger = FakeCrashLogger(context)

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideLoggingTestCases")
    @Suppress("Unused")
    fun `Verify in-memory logging behaviour`(
        expectedMessage: String?,
        action: (CrashLogger) -> Unit,
    ) {
        action.invoke(logger)

        assertTrue(expectedMessage in logger) {
            "The in-memory logger should have contained the provided entry!: $logger"
        }
    }

    companion object {
        private val emptyThrowable = Throwable()

        @JvmStatic
        fun provideLoggingTestCases(): Stream<Arguments> = Stream.of(
            arguments(
                named(
                    "Stores internally created Throwable out of provided String",
                    logMessage,
                ),
                { log: CrashLogger -> log.log(logMessage) },
            ),
            arguments(
                named(
                    "Uses Throwable message when provided a Throwable",
                    logThrowable.message,
                ),
                { log: CrashLogger -> log.log(logThrowable) },
            ),
            arguments(
                named(
                    "Accepts null messages from a Throwable",
                    null,
                ),
                { log: CrashLogger -> log.log(emptyThrowable) },
            ),
        )
    }
}
