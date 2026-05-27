package uk.gov.logging.api

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
@Deprecated(
    message =
        "Replace with v3 version which allows to set custom  keys" +
            " -aim to remove by 27th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.api.v3.Logger",
        ),
    level = DeprecationLevel.WARNING,
)
interface CrashLogger {
    fun log(throwable: Throwable)

    fun log(message: String)
}
