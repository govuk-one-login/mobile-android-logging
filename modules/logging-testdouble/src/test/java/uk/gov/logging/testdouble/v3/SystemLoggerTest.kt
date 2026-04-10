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
import uk.gov.logging.testdouble.v3.LoggingTestData.basicLocalDebugEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.basicLocalInfoEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.basicWarnEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.customKeyThrowable
import uk.gov.logging.testdouble.v3.LoggingTestData.errorLocalThrowableEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.errorThrowableEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.intCustomKey
import uk.gov.logging.testdouble.v3.LoggingTestData.logMessageEntryFalse
import uk.gov.logging.testdouble.v3.LoggingTestData.logTagEntryFalse
import uk.gov.logging.testdouble.v3.LoggingTestData.logThrowable
import uk.gov.logging.testdouble.v3.LoggingTestData.withExceptionEntry
import uk.gov.logging.testdouble.v3.LoggingTestData.withExceptionLocalEntry
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertFalse

@Suppress("LongMethod")
internal class SystemLoggerTest {
    private val logger = SystemLogger()

    @Test
    fun `verify in-memory logging behaviour with any function`() {
        logger.debug(LOG_TAG, LOG_MESSAGE)
        assertTrue {
            logger.any {
                it.message == LOG_MESSAGE
            }
        }
    }

    @Test
    fun `verify in-memory logging behaviour with contains function`() {
        logger.debug(LOG_TAG, LOG_MESSAGE)
        assertTrue {
            logger.contains(LOG_MESSAGE)
        }
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideNegativeLoggingTestCase")
    fun `verify in-memory logging behaviour with false entry`(
        falseEntry: LogEntry,
        message: String,
        action: (Logger, String) -> Unit,
    ) {
        action.invoke(logger, message)

        assertFalse(falseEntry in logger)
    }

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
                        "Local Debug messages are stored",
                        basicLocalDebugEntry,
                    ),
                    { log: Logger -> log.log(basicLocalDebugEntry) },
                ),
                arguments(
                    named(
                        "Local info messages are stored ",
                        basicLocalInfoEntry,
                    ),
                    { log: Logger -> log.log(basicLocalInfoEntry) },
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
                        "Warning messages are store",
                        basicWarnEntry,
                    ),
                    { log: Logger -> log.warning(LOG_TAG, LOG_MESSAGE) },
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
                        "Error messages are stored with Throwable when entry is logged",
                        errorThrowableEntry,
                    ),
                    { log: Logger -> log.log(errorThrowableEntry) },
                ),
                arguments(
                    named(
                        "Local Error messages are stored ",
                        errorLocalThrowableEntry,
                    ),
                    { log: Logger -> log.log(errorLocalThrowableEntry) },
                ),
                arguments(
                    named(
                        "Local Error messages are stored with Exception",
                        withExceptionLocalEntry,
                    ),
                    { log: Logger -> log.log(withExceptionLocalEntry) },
                ),
                arguments(
                    named(
                        "Log Entry Error messages are stored with Exception",
                        withExceptionEntry,
                    ),
                    { log: Logger -> log.log(withExceptionEntry) },
                ),
                arguments(
                    named(
                        "Error messages are stored with Throwable and Error Keys",
                        customKeyThrowable,
                    ),
                    { log: Logger -> log.error(LOG_TAG, LOG_MESSAGE, logThrowable, intCustomKey) },
                ),
            )

        @JvmStatic
        fun provideNegativeLoggingTestCase(): Stream<Arguments> =
            Stream.of(
                arguments(
                    named(
                        "Debug messages are stored",
                        logMessageEntryFalse,
                    ),
                    LOG_MESSAGE,
                    { log: Logger, message: String -> log.debug(LOG_TAG, message) },
                ),
                arguments(
                    named(
                        "Debug message are stored",
                        logTagEntryFalse,
                    ),
                    LOG_TAG,
                    { log: Logger, tag: String -> log.debug(tag, LOG_MESSAGE) },
                ),
            )
    }
}
