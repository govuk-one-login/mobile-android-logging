package uk.gov.logging.api

interface CrashLogger {
    fun log(throwable: Throwable)
    fun log(message: String)
}
