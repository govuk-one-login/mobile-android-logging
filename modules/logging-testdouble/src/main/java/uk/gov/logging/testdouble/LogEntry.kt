package uk.gov.logging.testdouble

sealed class LogEntry private constructor(
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
    ) : LogEntry(tag, message)
}
