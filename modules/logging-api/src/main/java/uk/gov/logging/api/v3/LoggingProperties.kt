package uk.gov.logging.api.v3

/**
 *  Logging properties data class use for
 *   checking if [LogEntry] is to be logged remotely
 *
 *   @property allowRemote
 *
 */

data class LoggingProperties(
    val allowRemote: Boolean = true,
)
