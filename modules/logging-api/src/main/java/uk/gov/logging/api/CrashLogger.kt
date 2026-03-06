package uk.gov.logging.api

/**
 * Abstraction for declaring crashlytics logger behavior.
 */

@Deprecated(
    message = "Replace with v2 version which allows to set custom error keys",
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
