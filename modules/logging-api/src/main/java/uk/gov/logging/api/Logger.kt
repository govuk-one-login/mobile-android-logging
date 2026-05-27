package uk.gov.logging.api

/**
 * Abstraction for declaring Android Logger behaviour.
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
interface Logger {
    fun debug(
        tag: String,
        msg: String,
    )

    fun info(
        tag: String,
        msg: String,
    )

    fun error(
        tag: String,
        msg: String,
        throwable: Throwable,
    )

    fun error(
        tag: String,
        msg: String,
    )
}
