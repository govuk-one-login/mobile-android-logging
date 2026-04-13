package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customKeys.CustomKey

/**
 * LogEntry is the data structure that wraps
 * log entries into a common data structure.
 * @property level the logging level
 * @property message the log message
 * @property tag the log tag
 */
sealed interface LogEntry {
    val level: Int
    val message: String
    val tag: String

    interface WithException : LogEntry {
        val throwable: Throwable
    }

    data class Basic(
        override val level: Int,
        override val message: String,
        override val tag: String,
    ) : LogEntry

    data class Error(
        override val level: Int,
        override val message: String,
        override val tag: String,
        override val throwable: Throwable,
        val customKeys: List<CustomKey>,
    ) : WithException
}
