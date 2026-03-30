package uk.gov.logging.api.v3

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger : Logger {
    fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    )

    fun log(throwable: Throwable)

    fun log(message: String)
}
