package uk.gov.logging.api.v3

import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.v3.customkey.CustomKey

/**
 * Logger interface for logging [LogEntry]s.
 *
 * Includes convenience functions for logging entries at each [LogLevel].
 *
 * For further convenience, [Logger] may be used within a [LogTagProvider]
 * extensions that apply log tags automatically.
 *
 * Implementations need only override the [log] function.
 *
 * @see LogEntry
 * @sample LoggerExample
 */
fun interface Logger {
    /**
     * Logs a single [LogEntry] with [LoggingProperties].
     * This is the single abstract method (SAM) that implementations must override.
     *
     * @param entry the log entry to process
     * @param properties the logging behaviour configuration
     */
    fun log(
        entry: LogEntry,
        properties: LoggingProperties,
    )

    /**
     * Logs a single [LogEntry] using default [LoggingProperties].
     *
     * @param entry the log entry to process
     */
    fun log(entry: LogEntry) = log(entry, LoggingProperties())

    /**
     * Logs each [LogEntry] in the [entries] collection.
     *
     * @param entries the collection of log entries to process
     * @param properties the logging behaviour configuration, uses default [LoggingProperties]
     */
    fun log(
        entries: Iterable<LogEntry>,
        properties: LoggingProperties = LoggingProperties(),
    ) = entries.forEach { log(it, properties) }

    /**
     * Logs each [LogEntry] passed as vararg.
     *
     * @param entries the log entries to process
     * @param properties the logging behaviour configuration, uses default [LoggingProperties]
     */
    fun log(
        vararg entries: LogEntry,
        properties: LoggingProperties = LoggingProperties(),
    ): Unit = log(entries.asList(), properties)

    /**
     * Logs a [LogEntry.Info] entry. Remote logging is enabled.
     *
     * @param tag the log tag
     * @param message the log message
     */
    fun info(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Info(tag = tag, message = message),
        LoggingProperties(allowRemote = true),
    )

    /**
     * Logs a [LogEntry.Debug] entry. Remote logging is disabled.
     *
     * @param tag the log tag
     * @param message the log message
     */
    fun debug(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Debug(tag = tag, message = message),
        LoggingProperties(allowRemote = false),
    )

    /**
     * Logs a [LogEntry.Verbose] entry. Remote logging is disabled.
     *
     * @param tag the log tag
     * @param message the log message
     */
    fun verbose(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Verbose(tag = tag, message = message),
        LoggingProperties(allowRemote = false),
    )

    /**
     * Logs a [LogEntry.Error] entry with the associated [throwable] and optional [customKey]s.
     * Remote logging is enabled.
     *
     * @param tag the log tag
     * @param message the log message
     * @param throwable the exception to record
     * @param customKey optional typed key-value pairs for crash reports
     */
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

    /**
     * Logs a [LogEntry.Warn] entry. Remote logging is enabled.
     *
     * @param tag the log tag
     * @param message the log message
     */
    fun warning(
        tag: String,
        message: String,
    ) = log(
        LogEntry.Warn(tag = tag, message = message),
        LoggingProperties(allowRemote = true),
    )
}
