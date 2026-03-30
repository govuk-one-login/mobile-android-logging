package uk.gov.logging.api.v3

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

sealed interface LogEntry {
    val level: Int
    val message: String
    val tag: String

    interface WithException : LogEntry {
        val throwable: Throwable
        val errorKeys: ErrorKeys?
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
        override val errorKeys: ErrorKeys?,
    ) : WithException
}
