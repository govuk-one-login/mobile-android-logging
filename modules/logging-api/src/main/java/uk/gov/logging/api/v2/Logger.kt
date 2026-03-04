package uk.gov.logging.api.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

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
        errorKeys: ErrorKeys? = null,
    )

    fun error(
        tag: String,
        message: String,
    )
}
