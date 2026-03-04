package uk.gov.logging.api.v2

import uk.gov.logging.api.v2.errorKeys.ErrorKeys

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger {
    fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys?,
    )

    fun log(message: String)
}
