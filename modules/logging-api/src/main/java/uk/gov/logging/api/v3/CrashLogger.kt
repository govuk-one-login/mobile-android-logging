package uk.gov.logging.api.v3

import uk.gov.logging.api.v3.customKeys.CustomKey

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger : Logger {
    fun log(
        throwable: Throwable,
        vararg customKeys: CustomKey,
    )

    fun log(message: String)
}
