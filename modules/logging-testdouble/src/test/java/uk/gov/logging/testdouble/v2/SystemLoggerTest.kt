package uk.gov.logging.testdouble.v2

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.logging.api.v2.Logger
import uk.gov.logging.testdouble.v2.LoggingTestData.LOG_MESSAGE
import uk.gov.logging.testdouble.v2.LoggingTestData.LOG_TAG
import uk.gov.logging.testdouble.v2.LoggingTestData.errorKeys
import uk.gov.logging.testdouble.v2.LoggingTestData.logErrorEntry
import uk.gov.logging.testdouble.v2.LoggingTestData.logErrorEntryNoKeys
import uk.gov.logging.testdouble.v2.LoggingTestData.logMessageEntry
import uk.gov.logging.testdouble.v2.LoggingTestData.logThrowable
import java.util.stream.Stream

internal class SystemLoggerTest {
    private val logger = SystemLogger()

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideLoggingTestCases")
    @Suppress("Unused")
    fun `Verify in-memory logging behaviour`(
        expectedEntry: LogEntry,
        action: (Logger) -> Unit,
    ) {
        action.invoke(logger)

        assertTrue(expectedEntry in logger) {
            "The in-memory logger should have contained the provided entry!: $logger"
        }
    }

    companion object {
        @JvmStatic
        fun provideLoggingTestCases(): Stream<Arguments> =
            Stream.of(
                arguments(
                    named(
                        "Debug messages are stored",
                        logMessageEntry,
                    ),
                    { log: Logger -> log.debug(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Info messages are stored",
                        logMessageEntry,
                    ),
                    { log: Logger -> log.info(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Error messages are stored",
                        logMessageEntry,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Error messages are stored with Throwable and errorKeys",
                        logErrorEntry,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE, logThrowable, errorKeys) },
                ),
                arguments(
                    named(
                        "Error messages are stored with Throwable",
                        logErrorEntryNoKeys,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE, logThrowable) },
                ),
                arguments(
                    named(
                        "Warning messages are stored",
                        logMessageEntry,
                    ),
                    { log: Logger -> log.warning(LOG_TAG, LOG_MESSAGE) },
                ),
            )
    }
}
