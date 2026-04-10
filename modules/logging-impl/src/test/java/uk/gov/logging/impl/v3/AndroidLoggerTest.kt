package uk.gov.logging.impl.v3

import android.util.Log
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customKeys.CustomKey
import uk.gov.logging.impl.LoggingTestDataRelease.customKeyNotnull
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable
import uk.gov.logging.impl.LoggingTestDataRelease.multipleCustomKeys
import uk.gov.logging.impl.v2.CrashlyticsLoggerTest.Companion.KEY
import java.util.stream.Stream

class AndroidLoggerTest {
    private val crashLogger: CrashLogger = mock()

    private lateinit var staticLogMock: MockedStatic<Log>

    private val logger: Logger by lazy {
        AndroidLogger(crashLogger = crashLogger)
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
    fun `test log entries with debug entry `() {
        logger.log(basicDebugEntry)
        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.d(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        }
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("setUpTestValuesBasicEntry")
    fun `test log entries logs basics entries `(
        entries: Collection<LogEntry>,
        symbol: String,
    ) {
        logger.log(entries)

        verify(crashLogger).log(eq("$symbol : $logTag : $logMessage"))
    }

    @Test
    fun `test log entry with exception and custom keys is not equals to null `() {
        logger.log(errorEntryWithCustomKeys)

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                    eq(logThrowable),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }

        verify(crashLogger).log(eq(logThrowable), eq(customKeyNotnull))
    }

    @Test
    fun `test log entry with exception and custom keys is equals to null `() {
        logger.log(errorEntryWithOutCustomKeys)

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                    eq(logThrowable),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }

        verify(crashLogger).log(eq(logThrowable))
    }

    @Test
    fun `Debug messages defer to static Android log function`() {
        logger.debug(tag = logTag, message = logMessage)
        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.d(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
    }

    @Test
    fun `Info messages call crash logger and static logger`() {
        logger.info(tag = logTag, message = logMessage)
        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.i(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
        verify(crashLogger).log(eq("I : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages is logged when crash logger  and static logger`() {
        logger.error(tag = logTag, message = logMessage)
        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
        verify(crashLogger).log(eq("E : $logTag : $logMessage"))
    }

    @Test
    fun `Error messages logged with throwable no custom key call crash logger and static logger `() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable)

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                    eq(logThrowable),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
        verify(crashLogger).log(eq(logThrowable))
    }

    @Test
    fun `Error messages with throwable and custom key call crash logger and static logger `() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable, customKeyNotnull)

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                    eq(logThrowable),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
        verify(crashLogger, times(0)).log(eq(logThrowable))
        verify(crashLogger, times(1)).log(eq(logThrowable), eq(customKeyNotnull))
    }

    @Test
    fun `Error messages with throwable and multiple custom key call crash logger and static logger `() {
        logger.error(tag = logTag, message = logMessage, throwable = logThrowable, *multipleCustomKeys.toTypedArray())

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.e(
                    eq(logTag),
                    eq(logMessage),
                    eq(logThrowable),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }
        verify(crashLogger, times(0)).log(eq(logThrowable))
        verify(crashLogger, times(1)).log(eq(logThrowable), eq(multipleCustomKeys[0]), eq(multipleCustomKeys[1]))
    }

    @Test
    fun `warning messages call crash logger and static logger`() {
        logger.warning(tag = logTag, message = logMessage)

        if (BuildConfig.DEBUG) {
            staticLogMock.verify {
                Log.w(
                    eq(logTag),
                    eq(logMessage),
                )
            }
        } else {
            staticLogMock.verifyNoInteractions()
        }

        verify(crashLogger).log(eq("W : $logTag : $logMessage"))
    }

    companion object {
        val basicDebugEntry =
            listOf<LogEntry>(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.DEBUG,
                ),
            )

        val basicInfoEntry =
            listOf<LogEntry>(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.INFO,
                ),
            )

        val basicWarnEntry =
            listOf<LogEntry>(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.WARN,
                ),
            )
        val basicErrorEntry =
            listOf<LogEntry>(
                LogEntry.Basic(
                    tag = logTag,
                    message = logMessage,
                    level = Log.ERROR,
                ),
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
