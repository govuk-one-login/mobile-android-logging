package uk.gov.logging.impl.v3

import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

/**
 * Android specific logger implementation
 * which delegates to a [MultiLogger]
 *
 * @param multiLogger instance of [MultiLogger] to delegate to
 * @constructor creates an instance of [AndroidLogger]
 *
 */
class AndroidLogger(
    private val multiLogger: MultiLogger,
) : Logger {
    override fun log(entries: Iterable<LogEntry>) =
        entries.forEach { entry ->
            multiLogger.log(entry)
        }
}
