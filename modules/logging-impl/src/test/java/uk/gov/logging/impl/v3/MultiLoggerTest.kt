package uk.gov.logging.impl.v3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LogEntry

class MultiLoggerTest {
    private val tag = "tag"
    private val message = "msg"

    @Test
    fun `delegates entry to all loggers`() {
        val loggedA = mutableListOf<LogEntry>()
        val loggedB = mutableListOf<LogEntry>()
        val multiLogger =
            MultiLogger(
                { loggedA.add(it) },
                { loggedB.add(it) },
            )

        val entry = LogEntry.Info(tag = tag, message = message)
        multiLogger.log(entry)

        assertEquals(listOf(entry), loggedA)
        assertEquals(listOf(entry), loggedB)
    }

    @Test
    fun `filter with isLocalOnly true does not log to all loggers`() {
        val loggedA = mutableListOf<LogEntry>()
        val loggedB = mutableListOf<LogEntry>()
        val multiLogger =
            MultiLogger(
                { loggedA.add(it) },
                { loggedB.add(it) },
            )

        val entry = LogEntry.Debug(tag = tag, message = message)
        multiLogger.filter(entry, isLocalOnly = true)

        assertEquals(emptyList<LogEntry>(), loggedA)
        assertEquals(emptyList<LogEntry>(), loggedB)
    }

    @Test
    fun `filter with isLocalOnly false logs to all loggers`() {
        val loggedA = mutableListOf<LogEntry>()
        val loggedB = mutableListOf<LogEntry>()
        val multiLogger =
            MultiLogger(
                { loggedA.add(it) },
                { loggedB.add(it) },
            )

        val entry = LogEntry.Info(tag = tag, message = message)
        multiLogger.filter(entry, isLocalOnly = false)

        assertEquals(listOf(entry), loggedA)
        assertEquals(listOf(entry), loggedB)
    }
}
