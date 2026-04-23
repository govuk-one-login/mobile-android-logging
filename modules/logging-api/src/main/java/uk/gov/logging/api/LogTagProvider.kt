package uk.gov.logging.api

import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.api.v3.Logger as LoggerV3

/**
 * Abstraction for declaring a default value to use as a tag when logging messages via a [Logger].
 */

interface LogTagProvider {
    /**
     * The value to group logging messages under. Defaults to the simple name of the implementing
     * class.
     */
    val tag: String
        get() = this::class.java.simpleName

    fun LoggerV3.info(message: String) =
        info(
            tag = tag,
            message = message,
        )

    fun LoggerV3.debug(message: String) =
        debug(
            tag = tag,
            message = message,
        )

    fun LoggerV3.verbose(message: String) =
        verbose(
            tag = tag,
            message = message,
        )

    fun LoggerV3.error(
        message: String,
        throwable: Throwable,
        vararg customKey: CustomKey,
    ) = error(
        tag = tag,
        message = message,
        throwable = throwable,
        *customKey,
    )

    fun LoggerV3.warning(message: String) =
        warning(
            tag = tag,
            message = message,
        )
}
