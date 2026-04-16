package uk.gov.logging.api.v3

import android.util.Log
import uk.gov.logging.api.v3.customkey.CustomKey

fun interface Logger {
    fun log(entries: Iterable<LogEntry>)

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
            customKeys = listOf(),
        ),
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        vararg customKey: CustomKey,
    ) = log(
        LogEntry.Error(
            tag = tag,
            message = message,
            throwable = throwable,
            level = Log.ERROR,
            customKeys = customKey.toList(),
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
