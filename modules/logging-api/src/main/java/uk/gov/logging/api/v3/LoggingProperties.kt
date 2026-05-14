package uk.gov.logging.api.v3

/**
 *  Properties for specifying the behaviour of a logger.
 *   @property allowRemote When true, the [Logger] implementation may log remotely,
 *   if it has the capability to do so
 */
data class LoggingProperties(
    val allowRemote: Boolean = true,
)
