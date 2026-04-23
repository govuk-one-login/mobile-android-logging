package uk.gov.logging.impl.v3

import android.util.Log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.eq
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.customkey.CustomKey
import java.util.stream.Stream

class LogcatLoggerTest {
    private lateinit var staticLogMock: MockedStatic<Log>

    @BeforeEach
    fun setUp() {
        staticLogMock = Mockito.mockStatic(Log::class.java)
    }

    @AfterEach
    fun tearDown() {
        staticLogMock.close()
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("messageEntries")
    fun `message entry calls correct Log function`(
        entry: LogEntry,
        verify: (MockedStatic<Log>) -> Unit,
    ) {
        LogcatLogger.log(entry)

        verify(staticLogMock)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("exceptionEntries")
    fun `exception entry calls correct Log function with throwable`(
        entry: LogEntry,
        verify: (MockedStatic<Log>) -> Unit,
    ) {
        LogcatLogger.log(entry)

        verify(staticLogMock)
    }

    companion object {
        private const val TAG = "tag"
        private const val MSG = "msg"
        private val throwable = RuntimeException("There's a problem")

        private fun exception(level: LogLevel) =
            object : LogEntry.Exception {
                override val level = level
                override val tag = TAG
                override val message = MSG
                override val isLocalOnly = false
                override val throwable = this@Companion.throwable
                override val customKeys: List<CustomKey> = emptyList()

                override fun toString() = level.name
            }

        private fun message(level: LogLevel) =
            object : LogEntry.Message {
                override val level = level
                override val tag = TAG
                override val message = MSG
                override val isLocalOnly = false

                override fun toString() = level.name
            }

        @JvmStatic
        fun messageEntries(): Stream<Arguments> =
            Stream.of(
                arguments(
                    message(LogLevel.Verbose),
                    { mock: MockedStatic<Log> -> mock.verify { Log.v(eq(TAG), eq(MSG)) } },
                ),
                arguments(
                    message(LogLevel.Debug),
                    { mock: MockedStatic<Log> -> mock.verify { Log.d(eq(TAG), eq(MSG)) } },
                ),
                arguments(
                    message(LogLevel.Info),
                    { mock: MockedStatic<Log> -> mock.verify { Log.i(eq(TAG), eq(MSG)) } },
                ),
                arguments(
                    message(LogLevel.Warn),
                    { mock: MockedStatic<Log> -> mock.verify { Log.w(eq(TAG), eq(MSG)) } },
                ),
                arguments(
                    message(LogLevel.Error),
                    { mock: MockedStatic<Log> -> mock.verify { Log.e(eq(TAG), eq(MSG)) } },
                ),
            )

        @JvmStatic
        fun exceptionEntries(): Stream<Arguments> =
            Stream.of(
                arguments(
                    exception(LogLevel.Verbose),
                    { mock: MockedStatic<Log> ->
                        mock.verify { Log.v(eq(TAG), eq(MSG), eq(throwable)) }
                    },
                ),
                arguments(
                    exception(LogLevel.Debug),
                    { mock: MockedStatic<Log> ->
                        mock.verify { Log.d(eq(TAG), eq(MSG), eq(throwable)) }
                    },
                ),
                arguments(
                    exception(LogLevel.Info),
                    { mock: MockedStatic<Log> ->
                        mock.verify { Log.i(eq(TAG), eq(MSG), eq(throwable)) }
                    },
                ),
                arguments(
                    exception(LogLevel.Warn),
                    { mock: MockedStatic<Log> ->
                        mock.verify { Log.w(eq(TAG), eq(MSG), eq(throwable)) }
                    },
                ),
                arguments(
                    exception(LogLevel.Error),
                    { mock: MockedStatic<Log> ->
                        mock.verify { Log.e(eq(TAG), eq(MSG), eq(throwable)) }
                    },
                ),
            )
    }
}
