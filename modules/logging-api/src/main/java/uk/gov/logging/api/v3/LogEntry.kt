package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customkey.CustomKey

/**
 * [LogEntry] represents information to be logged by a [Logger].
 *
 * @property level the logging level
 * @property message the log message
 * @property tag the log tag
 *
 */

sealed interface LogEntry {
    val level: LogLevel
    val message: String
    val tag: String

    interface Message : LogEntry

    interface Exception : LogEntry {
        val customKeys: List<CustomKey>
        val throwable: Throwable
    }

    data class Verbose(
        override val tag: String,
        override val message: String,
    ) : Message {
        override val level: LogLevel = LogLevel.Verbose
    }

    data class Debug(
        override val tag: String,
        override val message: String,
    ) : Message {
        override val level: LogLevel = LogLevel.Debug
    }

    data class Info(
        override val tag: String,
        override val message: String,
    ) : Message {
        override val level: LogLevel = LogLevel.Info
    }

    data class Warn(
        override val tag: String,
        override val message: String,
    ) : Message {
        override val level: LogLevel = LogLevel.Warn
    }

    data class Error(
        override val tag: String,
        override val message: String,
        override val throwable: Throwable,
        override val customKeys: List<CustomKey> = listOf(),
    ) : Exception {
        override val level: LogLevel = LogLevel.Error
    }
}

enum class LogLevel {
    Verbose,
    Debug,
    Info,
    Warn,
    Error,
}
