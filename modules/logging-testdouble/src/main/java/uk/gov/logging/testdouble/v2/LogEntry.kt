package uk.gov.logging.testdouble.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

sealed class LogEntry(
    open val tag: String,
    open val message: String,
) {
    data class Message(
        override val tag: String,
        override val message: String,
    ) : LogEntry(tag, message)

    data class Error(
        override val tag: String,
        override val message: String,
        val throwable: Throwable,
        val errorKeys: ErrorKeys,
    ) : LogEntry(tag, message)

    data class ErrorNoKeys(
        override val tag: String,
        override val message: String,
        val throwable: Throwable,
    ) : LogEntry(tag, message)
}
