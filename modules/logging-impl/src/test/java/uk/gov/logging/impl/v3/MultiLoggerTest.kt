package uk.gov.logging.impl.v3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

class MultiLoggerTest {
    private val tag = "tag"
    private val message = "msg"

    @Test
    fun `delegates entry to all loggers`() {
        val loggedA = mutableListOf<LogEntry>()
        val loggedB = mutableListOf<LogEntry>()
        val multiLogger =
            MultiLogger(
                Logger { loggedA.add(it) },
                Logger { loggedB.add(it) },
            )

        val entry = LogEntry.Info(tag = tag, message = message)
        multiLogger.log(entry)

        assertEquals(listOf(entry), loggedA)
        assertEquals(listOf(entry), loggedB)
    }
}
