package uk.gov.logging.api.v3

import android.util.Log
import uk.gov.logging.api.v2.errorKeys.ErrorKeys

fun interface Logger {
    fun log(entries: Collection<LogEntry>)

    fun log(vararg entries: LogEntry): Unit = log(entries.asList())

    fun info(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Basic(
            tag = tag,
            message = message,
            level = Log.INFO,
        ),
    )

    fun debug(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Basic(
            tag = tag,
            message = message,
            level = Log.DEBUG,
        ),
    )

    fun error(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Basic(
            tag = tag,
            message = message,
            level = Log.ERROR,
        ),
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
    ) = log(
        LogEntry.Error(
            tag = tag,
            message = message,
            throwable = throwable,
            level = Log.ERROR,
            errorKeys = null,
        ),
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) = log(
        LogEntry.Error(
            tag = tag,
            message = message,
            throwable = throwable,
            level = Log.ERROR,
            errorKeys = errorKeys,
        ),
    )

    fun warning(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Basic(
            tag = tag,
            message = message,
            level = Log.WARN,
        ),
    )
}
