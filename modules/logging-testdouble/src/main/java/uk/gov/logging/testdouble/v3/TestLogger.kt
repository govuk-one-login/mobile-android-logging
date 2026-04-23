package uk.gov.logging.testdouble.v3

import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

/**
 * Test double for [Logger]. Collects all logged entries for inspection.
 * Use [entries] to access the collected [LogEntry]s.
 * Implements [Iterable] so you can use it in a for loop or with any other iterable extension functions.
 *
 */
class TestLogger(
    val entries: MutableList<LogEntry> = mutableListOf<LogEntry>(),
) : Logger,
    Iterable<LogEntry> by entries {
    override fun log(entry: LogEntry) {
        entries.add(entry)
        println("${entry.tag}: ${entry.message}")
    }

    val size: Int get() = entries.size

    fun any(condition: (LogEntry) -> Boolean) = entries.any(condition)

    operator fun contains(message: String) = entries.any { it.message == message }

    operator fun contains(entry: LogEntry) =
        when (entry) {
            is LogEntry.Exception -> entries.any { it.matchesError(entry) }
            is LogEntry.Message -> entries.contains(entry)
        }

    operator fun get(i: Int) = entries[i]

    override fun toString() = "TestLogger(logs=${entries.toTypedArray().contentToString()})"

    private fun LogEntry.matchesError(other: LogEntry.Exception) =
        this is LogEntry.Exception &&
            tag == other.tag &&
            message == other.message &&
            throwable.javaClass == other.throwable.javaClass &&
            throwable.message == other.throwable.message &&
            customKeys == other.customKeys &&
            level == other.level
}
