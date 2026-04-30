package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.CustomKey

/**
 * Logger interface parsing the [LogEntry] model
 * sub functions for each log level
 * @see LogEntry
 * @see LoggerExample
 */

fun interface Logger {
    fun log(
        entry: LogEntry,
        properties: LoggingProperties,
    )

    fun log(
        entries: Iterable<LogEntry>,
        properties: LoggingProperties = LoggingProperties(),
    ) = entries.forEach { log(it, properties) }

    fun log(
        vararg entries: LogEntry,
        properties: LoggingProperties = LoggingProperties(),
    ): Unit = log(entries.asList(), properties)

    fun info(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Info(tag = tag, message = message),
        LoggingProperties(allowRemote = true),
    )

    fun debug(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Debug(tag = tag, message = message),
        LoggingProperties(allowRemote = false),
    )

    fun verbose(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Verbose(tag = tag, message = message),
        LoggingProperties(allowRemote = false),
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
        LoggingProperties(allowRemote = true),
    )

    fun warning(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Warn(tag = tag, message = message),
        LoggingProperties(allowRemote = true),
    )
}

fun Logger.log(entry: LogEntry) = log(entry, LoggingProperties())
