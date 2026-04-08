package uk.gov.logging.testfixture

import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

/**
 * [uk.gov.logging.api.v3.Logger] test fixture implementation that stores the provided entries from the [log] function
 * into the internal [entries] property for later assertions.
 *
 * @param subLogger The underlying [uk.gov.logging.api.v3.Logger] implementation to decorate. Defaults to an empty
 * implementation, meaning that the provided [uk.gov.logging.api.v3.LogEntry] objects are only stored in memory.
 *
*/
data class MemorisedLogger(
    val entries: MutableList<LogEntry> = mutableListOf(),
    private val subLogger: Logger = Logger {},
) : Logger,
    Iterable<LogEntry> by entries {
    val size: Int get() = entries.size

    override fun log(entries: Collection<LogEntry>) {
        this.entries.addAll(entries)
        subLogger.log(entries)
    }

    operator fun contains(message: String) = any { it.message == message }

    operator fun contains(throwable: Throwable) =
        any {
            it is LogEntry.WithException && it.throwable == throwable
        }
}
