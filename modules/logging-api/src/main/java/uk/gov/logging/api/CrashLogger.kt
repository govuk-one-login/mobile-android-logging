package uk.gov.logging.api

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
@Deprecated(
    message =
        "Replace with v2 version which allows to set custom error keys" +
            " -aim to remove by 10th of May 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.api.v2.CrashLogger",
        ),
    level = DeprecationLevel.WARNING,
)
interface CrashLogger {
    fun log(throwable: Throwable)

    fun log(message: String)
}
