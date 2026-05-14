package uk.gov.logging.api.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */

@Deprecated(
    message =
        "Replace with v3 Logger " +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.api.v3.Logger",
        ),
    level = DeprecationLevel.WARNING,
)
interface CrashLogger {
    fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    )

    fun log(throwable: Throwable)

    fun log(message: String)
}
