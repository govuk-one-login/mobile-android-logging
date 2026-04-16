package uk.gov.logging.impl.v3

import android.util.Log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.eq
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_MESSAGE
import uk.gov.logging.impl.v3.LoggingTestDataRelease.LOG_TAG
import uk.gov.logging.impl.v3.LoggingTestDataRelease.logThrowable

class LogcatLoggerTest {
    private lateinit var staticLogMock: MockedStatic<Log>

    private val logger: Logger by lazy {
        LogcatLogger
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
    fun `Debug messages logged in Log cat static logger`() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.DEBUG,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.DEBUG,
                ),
            ),
        )

        staticLogMock.verify {
            Log.d(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
                eq(logThrowable),
            )
        }
        staticLogMock.verify {
            Log.d(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @Test
    fun `Info messages logged in Log cat static logger`() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.INFO,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.INFO,
                ),
            ),
        )

        staticLogMock.verify {
            Log.i(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
                eq(logThrowable),
            )
        }

        staticLogMock.verify {
            Log.i(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @Test
    fun `Verbose messages logged in Log cat static logger`() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.VERBOSE,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.VERBOSE,
                ),
            ),
        )

        staticLogMock.verify {
            Log.v(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
                eq(logThrowable),
            )
        }

        staticLogMock.verify {
            Log.v(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @Test
    fun `Warn messages logged in Log cat static logger`() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.WARN,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.WARN,
                ),
            ),
        )

        staticLogMock.verify {
            Log.w(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
                eq(logThrowable),
            )
        }

        staticLogMock.verify {
            Log.w(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @Test
    fun `Error messages logged in Log cat  static logger `() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.ERROR,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.ERROR,
                ),
            ),
        )

        staticLogMock.verify {
            Log.e(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
                eq(logThrowable),
            )
        }

        staticLogMock.verify {
            Log.e(
                eq(LOG_TAG),
                eq(LOG_MESSAGE),
            )
        }
    }

    @Test
    fun `log entry with  false  log level logged in static logger zero interaction`() {
        logger.log(
            listOf(
                LocalLogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = 0,
                    throwable = logThrowable,
                ),
                LocalLogEntry.Basic(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = 0,
                ),
            ),
        )

        staticLogMock.verifyNoInteractions()
        staticLogMock.verifyNoInteractions()
    }
}
