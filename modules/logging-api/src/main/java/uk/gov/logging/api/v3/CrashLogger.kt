package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customKeys.CustomKeys

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger : Logger {
    fun log(
        throwable: Throwable,
        vararg customKeys: CustomKeys,
    )

    fun log(throwable: Throwable)

    fun log(message: String)
}
