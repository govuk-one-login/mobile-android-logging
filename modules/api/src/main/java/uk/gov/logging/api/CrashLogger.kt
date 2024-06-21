package uk.gov.logging.api

/**
 * Abstraction for declaring crashlytics logger behaviour.
 */
interface CrashLogger {
    fun log(throwable: Throwable)
    fun log(message: String)
}
