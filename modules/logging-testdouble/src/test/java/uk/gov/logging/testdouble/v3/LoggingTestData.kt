package uk.gov.logging.testdouble.v3

import android.util.Log
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customKeys.CustomKeys

object LoggingTestData {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"

    val logThrowable = Throwable(message = THROWABLE_MESSAGE)

    val intCustomKey = CustomKeys.IntKey("Key", 1)

    val basicDebugEntry =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.DEBUG,
        )

    val basicInfoEntry =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.INFO,
        )

    val basicWarnEntry =
        listOf<LogEntry>(
            LogEntry.Basic(
                tag = LOG_TAG,
                message = LOG_MESSAGE,
                level = Log.WARN,
            ),
        )
    val basicErrorEntry =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.ERROR,
        )

    val errorThrowableEntry =
        LogEntry.Error(
            Log.ERROR,
            LOG_MESSAGE,
            LOG_TAG,
            logThrowable,
            customKeys = null,
        )

    val customKeyThrowable =
        LogEntry.Error(
            Log.ERROR,
            LOG_MESSAGE,
            LOG_TAG,
            logThrowable,
            listOf(intCustomKey),
        )
}
