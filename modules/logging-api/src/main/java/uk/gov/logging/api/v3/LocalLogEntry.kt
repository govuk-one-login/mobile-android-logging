package uk.gov.logging.api.v3

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

/**
 * [LogEntry] implementation used for logging events.
 *
 * [Logger] implementations that perform network calls shouldn't log [LogEntry] instances of this
 * data type.
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
        override val errorKeys: ErrorKeys? = null
    }
}
