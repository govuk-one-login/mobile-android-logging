package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customKeys.CustomKey

/**
 * LocalLogEntry implements the [LogEntry] interface for logging entries
 * that are not yet ready to be logged remotely or entries to be logged locally.
 *
 * [Logger] implementations that perform network calls shouldn't log [LogEntry] instances of this
 * data type.
 * @property level the logging level
 * @property message the log message
 * @property tag the log tag
 */
sealed class LocalLogEntry(
    override val level: Int,
    override val message: String,
    override val tag: String,
) : LogEntry {
    data class Basic(
        override val level: Int,
        override val message: String,
        override val tag: String,
    ) : LocalLogEntry(
            message = message,
            level = level,
            tag = tag,
        )

    data class Error(
        override val level: Int,
        override val message: String,
        override val tag: String,
        override val throwable: Throwable,
    ) : LocalLogEntry(
            message = message,
            level = level,
            tag = tag,
        ),
        LogEntry.WithException {
        val customKeys: List<CustomKey> = listOf()
    }
}
