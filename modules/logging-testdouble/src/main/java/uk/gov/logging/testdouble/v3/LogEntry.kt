package uk.gov.logging.testdouble.v3

import uk.gov.logging.api.v3.customKeys.CustomKeys

sealed interface LogEntry {
    val level: Int
    val message: String
    val tag: String

    interface WithException : LogEntry {
        val throwable: Throwable
        val customKeys: CustomKeys
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
        override val customKeys: CustomKeys,
    ) : WithException
}
