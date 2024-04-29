package uk.gov.logging.api

interface Logger {
    fun debug(tag: String, msg: String)
    fun info(tag: String, msg: String)
    fun error(tag: String, msg: String, throwable: Throwable)
    fun error(tag: String, msg: String)
}
