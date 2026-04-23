package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.CustomKey

/**
 * @sample LoggerExample
 */
fun interface Logger {
    fun log(entry: LogEntry)

    fun log(entries: Iterable<LogEntry>) = entries.forEach(::log)

    fun log(vararg entries: LogEntry): Unit = log(entries.asList())

    fun info(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Info(
            tag = tag,
            message = message,
        ),
    )

    fun debug(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Debug(
            tag = tag,
            message = message,
        ),
    )

    fun verbose(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Verbose(
            tag = tag,
            message = message,
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
            customKeys = customKey.toList(),
        ),
    )

    fun warning(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Warn(
            tag = tag,
            message = message,
        ),
    )
}
