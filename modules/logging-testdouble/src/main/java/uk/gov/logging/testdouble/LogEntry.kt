package uk.gov.logging.testdouble

@Deprecated(
    message =
        "Replace with v3 LogEntry" +
            " -aim to remove by 27th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.api.v3.LogEntry",
        ),
    level = DeprecationLevel.WARNING,
)
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
