package uk.gov.logging.testdouble.v3

import android.util.Log
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customKeys.CustomKeys

object LoggingTestData {
    private const val THROWABLE_MESSAGE = "This is a unit test!"

    const val LOG_MESSAGE = "Unit test log message"
    const val LOG_TAG = "Example log tag"

    const val LOG_MESSAGE_TWO = "unit test second log message"
    const val LOG_TAG_TWO = "Example log tag two"

    val logThrowable = Throwable(message = THROWABLE_MESSAGE)

    val intCustomKey = CustomKeys.IntKey("Key", 1)

    val basicDebugEntry =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.DEBUG,
        )

    val basicLocalDebugEntry =
        LocalLogEntry.Basic(
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

    val basicLocalInfoEntry =
        LocalLogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.INFO,
        )

    val basicWarnEntry =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            level = Log.WARN,
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

    val errorLocalThrowableEntry =
        LocalLogEntry.Error(
            Log.ERROR,
            LOG_MESSAGE,
            LOG_TAG,
            logThrowable,
        )

    val withExceptionEntry: LogEntry.WithException =
        LogEntry.Error(
            level = Log.ERROR,
            tag = LOG_TAG,
            message = LOG_MESSAGE,
            throwable = logThrowable,
            customKeys = null,
        )

    val withExceptionLocalEntry: LogEntry.WithException =
        LocalLogEntry.Error(
            level = Log.ERROR,
            message = LOG_MESSAGE,
            tag = LOG_TAG,
            throwable = logThrowable,
        )

    val customKeyThrowable =
        LogEntry.Error(
            Log.ERROR,
            LOG_MESSAGE,
            LOG_TAG,
            logThrowable,
            listOf(intCustomKey),
        )

    val logMessageEntryFalse =
        LogEntry.Basic(
            tag = LOG_TAG,
            message = LOG_MESSAGE_TWO,
            level = Log.DEBUG,
        )

    val logTagEntryFalse =
        LogEntry.Basic(
            Log.DEBUG,
            LOG_TAG_TWO,
            LOG_MESSAGE,
        )
}
