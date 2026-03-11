package uk.gov.logging.api

/**
 * Abstraction for declaring crashlytics logger behavior.
 */

@Deprecated(
    message =
        "Replace with v2 version which allows to set custom error keys" +
            " -aim to remove by 10th of May 26 2026",
    replaceWith =
        ReplaceWith(
            "mobile-android-logging/modules/logging-api/src/main" +
                "/java/uk/gov/logging/api/v2/CrashLogger.kt",
        ),
    level = DeprecationLevel.WARNING,
)
interface CrashLogger {
    fun log(throwable: Throwable)

    fun log(message: String)
}
