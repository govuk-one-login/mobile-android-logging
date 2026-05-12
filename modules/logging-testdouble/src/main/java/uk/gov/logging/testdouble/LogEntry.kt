package uk.gov.logging.testdouble

@Deprecated(
    message =
        "Replace with MemorisedLogger" +
            " -aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "mobile-android-logging/modules/logging-api/src" +
                "main/java/uk/gov/logging/api/v3/LogEntry.kt",
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
