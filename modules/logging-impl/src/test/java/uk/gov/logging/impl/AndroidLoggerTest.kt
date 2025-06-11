package uk.gov.logging.impl

import android.util.Log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.api.Logger
import uk.gov.logging.impl.LoggingTestDataRelease.logMessage
import uk.gov.logging.impl.LoggingTestDataRelease.logTag
import uk.gov.logging.impl.LoggingTestDataRelease.logThrowable

internal class AndroidLoggerTest {

    private val crashLogger: CrashLogger = mock()
    private lateinit var staticLogMock: MockedStatic<Log>

    private val logger: Logger by lazy {
        AndroidLogger(crashLogger = crashLogger)
    }

    @AfterEach
    fun tearDown() {
        staticLogMock.close()
    }

    @BeforeEach
    fun setUp() {
        staticLogMock = Mockito.mockStatic(Log::class.java)
    }

    @Test
    fun `Debug messages defer to static Android log function`() {
        logger.debug(tag = logTag, msg = logMessage)

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
        logger.info(tag = logTag, msg = logMessage)

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
    fun `Error messages call crash logger and static logger`() {
        logger.error(tag = logTag, msg = logMessage)

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
    fun `Error messages with throwable call crash logger and static logger`() {
        logger.error(tag = logTag, msg = logMessage, throwable = logThrowable)

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
}
