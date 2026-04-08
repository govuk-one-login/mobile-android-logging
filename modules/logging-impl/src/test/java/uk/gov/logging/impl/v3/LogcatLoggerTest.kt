package uk.gov.logging.impl.v3

import android.util.Log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.eq
import uk.gov.logging.api.v3.LogEntry
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
    fun `Error messages logged in Log cat  static logger `() {
        logger.log(
            listOf(
                LogEntry.Error(
                    tag = LOG_TAG,
                    message = LOG_MESSAGE,
                    level = Log.ERROR,
                    throwable = logThrowable,
                    customKeys = null,
                ),
                LogEntry.Basic(
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
    }
}
