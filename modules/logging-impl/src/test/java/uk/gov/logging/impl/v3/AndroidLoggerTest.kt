package uk.gov.logging.impl.v3

import android.util.Log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.eq
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable
import uk.gov.logging.impl.v2.CrashlyticsLoggerTest.Companion.KEY
import uk.gov.logging.impl.v3.LoggingTestDataRelease.customKeyNotnull
import uk.gov.logging.impl.v3.LoggingTestDataRelease.multipleCustomKeys
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Stream

class AndroidLoggerTest {
    private val crashLogger: Logger = CrashLogger

    private lateinit var staticLogMock: MockedStatic<Log>
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream

    private val logCatLogger: Logger by lazy {
        LogcatLogger
    }

    private val logger: Logger by lazy {
        AndroidLogger(multiLogger = MultiLogger(crashLogger, logCatLogger))
    }

    @BeforeEach
    fun setUp() {
        staticLogMock = Mockito.mockStatic(Log::class.java)
        originalOut = System.out
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
        staticLogMock.close()
    }

    private fun capturedOutput(): String = outputStream.toString()

    @Test
    fun `test log entries with debug entry does not print to crash logger`() {
        logger.log(basicDebugEntry)
        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.d(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        }
        assertFalse(capturedOutput().contains(logTag))
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("setUpTestValuesBasicEntry")
    fun `test log entries logs basics entries on crash logger`(
        entries: Collection<LogEntry>,
        symbol: String,
    ) {
        logger.log(entries)

        assertTrue(capturedOutput().contains("$symbol : $logTag : $logMessage"))
    }

    @Test
    fun `test log entry with exception and with custom key on crash logger`() {
        logger.log(errorEntryWithCustomKeys)

        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `test log entries with exception and no custom key `() {
        logger.log(errorEntryWithOutCustomKeys)

        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `Debug messages does not get printed on  crash logger`() {
        logger.debug(tag = logTag, message = logMessage)
        assertFalse(capturedOutput().contains(logTag))
    }

    @Test
    fun `Info messages is logged on crash logger`() {
        logger.info(tag = logTag, message = logMessage)
        assertTrue(capturedOutput().contains("I : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages is logged on crash logger`() {
        logger.error(tag = logTag, message = logMessage)

        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages logged with throwable no custom key on crash logger `() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable)
        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages with throwable and custom key call crash logger`() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable, customKeyNotnull)
        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages with throwable and multiple custom key call crash logger and static logger`() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable, *multipleCustomKeys.toTypedArray())

        assertTrue(capturedOutput().contains("E : $logTag : $logMessage"))
    }

    @Test
    fun `warning messages call crash logger`() {
        logger.warning(tag = logTag, message = logMessage)

        assertTrue(capturedOutput().contains("W : $logTag : $logMessage"))
    }

    @Test
    fun `test local log info entry`() {
        logger.log(basicInfoLocalEntry)
        staticLogMock.verify {
            Log.i(
                eq(logTag),
                eq(logMessage),
            )
        }
    }

    @Test
    fun `test local log warn entry`() {
        logger.log(basicWarnLocalEntry)
        staticLogMock.verify {
            Log.w(
                eq(logTag),
                eq(logMessage),
            )
        }
    }

    @Test
    fun `test local log error entry`() {
        logger.log(localErrorBasicEntry)
        staticLogMock.verify {
            Log.e(
                eq(logTag),
                eq(logMessage),
            )
        }
    }

    companion object {
        val basicDebugEntry =
            listOf(
                LocalLogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.DEBUG,
                ),
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.DEBUG,
                ),
            )

        val basicInfoEntry =
            listOf(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.INFO,
                ),
            )

        val basicInfoLocalEntry =
            LocalLogEntry.Basic(
                tag = logTag,
                message = logMessage,
                level = Log.INFO,
            )

        val basicWarnEntry =
            listOf(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.WARN,
                ),
            )

        val basicWarnLocalEntry =
            LocalLogEntry.Basic(
                tag = logTag,
                message = logMessage,
                level = Log.WARN,
            )

        val basicErrorEntry =
            listOf(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                ),
            )

        val localErrorBasicEntry =
            LocalLogEntry.Basic(
                tag = logTag,
                message = logMessage,
                level = Log.ERROR,
            )

        const val LEVEL_SYMBOL_INFO = "I"

        const val LEVEL_SYMBOL_ERROR = "E"

        const val LEVEL_SYMBOL_WARN = "W"

        const val INT_VALUE = 1
        val intCustomKey: List<CustomKey> = listOf(CustomKey.IntKey(KEY, INT_VALUE))

        val errorEntryWithCustomKeys =
            listOf<LogEntry>(
                LogEntry.Error(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                    throwable = logThrowable,
                    customKeys = intCustomKey,
                ),
            )

        val errorEntryWithOutCustomKeys =
            listOf<LogEntry>(
                LogEntry.Error(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                    throwable = logThrowable,
                    customKeys = listOf(),
                ),
            )

        @JvmStatic
        fun setUpTestValuesBasicEntry(): Stream<Arguments> =
            Stream.of(
                arguments(
                    basicInfoEntry,
                    LEVEL_SYMBOL_INFO,
                ),
                arguments(
                    basicErrorEntry,
                    LEVEL_SYMBOL_ERROR,
                ),
                arguments(
                    basicWarnEntry,
                    LEVEL_SYMBOL_WARN,
                ),
            )
    }
}

data object CrashLogger : Logger {
    override fun log(entries: Iterable<LogEntry>) =
        entries.filter { entry -> entry !is LocalLogEntry }.forEach { entry ->

            when (entry.level) {
                Log.WARN -> "W"
                Log.ERROR -> "E"
                Log.INFO -> "I"
                else -> null
            }?.let { level ->
                "$level : ${entry.tag} : ${entry.message}"
            }?.let(::printLogs)
        }

    private fun printLogs(message: String) {
        println(message)
    }
}
