package uk.gov.logging.impl.v3

import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

/**
 * Logger that delegates to multiple other loggers.
 *
 * @property loggers the Iterable loggers to delegate to
 * @constructor creates a logger that will delegate to all the given loggers
 */
class MultiLogger(
    private val loggers: Iterable<Logger>,
) : Logger {
    constructor(
        vararg loggers: Logger,
    ) : this(loggers = loggers.toList())

    override fun log(entries: Iterable<LogEntry>) =
        loggers.forEach { logger ->
            logger.log(entries)
        }
}
