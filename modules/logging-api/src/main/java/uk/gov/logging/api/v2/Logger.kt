package uk.gov.logging.api.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

@Deprecated(
    message =
        "Replace with v3 Logger " +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "mobile-android-logging/modules/logging-api/src/main/" +
                "java/uk/gov/logging/api/v3/Logger.kt",
        ),
    level = DeprecationLevel.WARNING,
)
interface Logger {
    fun debug(
        tag: String,
        message: String,
    )

    fun info(
        tag: String,
        message: String,
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        errorKeys: ErrorKeys,
    )

    fun error(
        tag: String,
        message: String,
        throwable: Throwable,
    )

    fun error(
        tag: String,
        message: String,
    )

    fun warning(
        tag: String,
        message: String,
    )
}
