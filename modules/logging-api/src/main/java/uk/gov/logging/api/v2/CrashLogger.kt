package uk.gov.logging.api.v2

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger {

    fun log(throwable: Throwable)

    fun log(message: String)
}
