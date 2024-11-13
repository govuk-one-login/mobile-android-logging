package uk.gov.logging.testdouble

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.logging.api.Logger
import uk.gov.logging.testdouble.LoggingTestData.logErrorEntry
import uk.gov.logging.testdouble.LoggingTestData.logMessage
import uk.gov.logging.testdouble.LoggingTestData.logMessageEntry
import uk.gov.logging.testdouble.LoggingTestData.logTag
import uk.gov.logging.testdouble.LoggingTestData.logThrowable
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
        fun provideLoggingTestCases(): Stream<Arguments> = Stream.of(
            arguments(
                named(
                    "Debug messages are stored",
                    logMessageEntry,
                ),
                { log: Logger -> log.debug(logTag, logMessage) },
            ),
            arguments(
                named(
                    "Info messages are stored",
                    logMessageEntry,
                ),
                { log: Logger -> log.info(logTag, logMessage) },
            ),
            arguments(
                named(
                    "Error messages are stored",
                    logMessageEntry,
                ),
                { log: Logger -> log.error(logTag, logMessage) },
            ),
            arguments(
                named(
                    "Error messages are stored with Throwable",
                    logErrorEntry,
                ),
                { log: Logger -> log.error(logTag, logMessage, logThrowable) },
            ),
        )
    }
}
