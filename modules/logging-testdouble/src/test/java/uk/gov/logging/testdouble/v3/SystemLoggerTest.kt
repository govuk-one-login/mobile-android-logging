package uk.gov.logging.testdouble.v3

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.testdouble.v3.LoggingTestData.LOG_MESSAGE
import uk.gov.logging.testdouble.v3.LoggingTestData.LOG_TAG
import uk.gov.logging.testdouble.v3.LoggingTestData.basicDebugEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.basicErrorEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.basicInfoEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.customKeyThrowable
import uk.gov.logging.testdouble.v3.LoggingTestData.errorThrowableEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.intCustomKey
import uk.gov.logging.testdouble.v3.LoggingTestData.logThrowable
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
                        basicDebugEntry,
                    ),
                    { log: Logger -> log.debug(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Info messages are stored",
                        basicInfoEntry,
                    ),
                    { log: Logger -> log.info(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Error messages are stored",
                        basicErrorEntry,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE) },
                ),
                arguments(
                    named(
                        "Error messages are stored with Throwable",
                        errorThrowableEntry,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE, logThrowable) },
                ),
                arguments(
                    named(
                        "Error messages are stored with Throwable and Error Keys",
                        customKeyThrowable,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE, logThrowable, intCustomKey) },
                ),
            )
    }
}
