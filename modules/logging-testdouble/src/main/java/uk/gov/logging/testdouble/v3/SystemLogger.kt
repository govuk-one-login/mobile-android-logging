package uk.gov.logging.testdouble.v3

import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

class SystemLogger : Logger {
    private val logs = mutableListOf<LogEntry>()

    override fun log(entries: Collection<LogEntry>) {
        entries.forEach { entry ->
            println("${entry.tag}: ${entry.message}")
            logs.add(entry)
        }
    }

    val size: Int get() = logs.size

    fun any(condition: (LogEntry) -> Boolean) = logs.any(condition)

    operator fun contains(message: String) = logs.any { it.message == message }

    operator fun contains(entry: LogEntry) =
        when (entry) {
            is LogEntry.Basic -> logs.contains(entry)
            is LogEntry.Error -> logs.any { it.matchesError(entry) }
            is LocalLogEntry.Basic -> logs.contains(entry)
            is LocalLogEntry.Error -> logs.any { it.matchesWithException(entry) }
            is LogEntry.WithException -> logs.any { it.matchesWithException(entry) }
        }

    operator fun get(i: Int) = logs[i]

    override fun toString() = "SystemLogger(logs=${logs.toTypedArray().contentToString()})"

    private fun LogEntry.matchesError(other: LogEntry.Error) =
        this is LogEntry.Error &&
            tag == other.tag &&
            message == other.message &&
            throwable.javaClass == other.throwable.javaClass &&
            throwable.message == other.throwable.message &&
            customKeys == other.customKeys &&
            level == other.level

    private fun LogEntry.matchesWithException(other: LogEntry.WithException) =
        this is LogEntry.WithException &&
            tag == other.tag &&
            message == other.message &&
            throwable.javaClass == other.throwable.javaClass &&
            throwable.message == other.throwable.message &&
            customKeys == other.customKeys
}
