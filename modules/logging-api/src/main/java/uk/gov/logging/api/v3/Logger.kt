package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.CustomKey

/**
 * Logger interface parsing the [LogEntry] model
 * sub functions for each log level
 * @see LogEntry
 * @see LoggerExample
 */

fun interface Logger {
    fun log(entry: LogEntry)

    fun filter(
        entry: LogEntry,
        isLocalOnly: Boolean,
    ) {
        if (!isLocalOnly) log(entry)
    }

    fun log(entries: Iterable<LogEntry>) = entries.forEach(::log)

    fun log(vararg entries: LogEntry): Unit = log(entries.asList())

    fun info(
        tag: String,
        message: String,
    ) = filter(
        LogEntry.Info(
            tag = tag,
            message = message,
        ),
        isLocalOnly = false,
    )

    fun debug(
        tag: String,
        message: String,
    ) = filter(
        LogEntry.Debug(
            tag = tag,
            message = message,
        ),
        isLocalOnly = true,
    )

    fun verbose(
        tag: String,
        message: String,
    ) = filter(
        LogEntry.Verbose(
            tag = tag,
            message = message,
        ),
        isLocalOnly = true,
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        vararg customKey: CustomKey,
    ) = filter(
        LogEntry.Error(
            tag = tag,
            message = message,
            throwable = throwable,
            customKeys = customKey.toList(),
        ),
        isLocalOnly = false,
    )

    fun warning(
        tag: String,
        message: String,
    ) = filter(
        LogEntry.Warn(
            tag = tag,
            message = message,
        ),
        isLocalOnly = false,
    )
}
