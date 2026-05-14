package uk.gov.logging.impl.v3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LoggingProperties

class MultiLoggerTest {
    private val tag = "tag"
    private val message = "msg"

    @Test
    fun `delegates entry to all loggers`() {
        val loggedA = mutableListOf<LogEntry>()
        val loggedB = mutableListOf<LogEntry>()
        val multiLogger =
            MultiLogger(
                { entry, _ -> loggedA.add(entry) },
                { entry, _ -> loggedB.add(entry) },
            )

        val entry = LogEntry.Info(tag = tag, message = message)
        multiLogger.log(entry, LoggingProperties())

        assertEquals(listOf(entry), loggedA)
        assertEquals(listOf(entry), loggedB)
    }

    @Test
    fun `delegates properties to all loggers`() {
        val propertiesA = mutableListOf<LoggingProperties>()
        val propertiesB = mutableListOf<LoggingProperties>()
        val multiLogger =
            MultiLogger(
                { _, properties -> propertiesA.add(properties) },
                { _, properties -> propertiesB.add(properties) },
            )

        val entry = LogEntry.Info(tag = tag, message = message)
        val properties = LoggingProperties(allowRemote = false)
        multiLogger.log(entry, properties)

        assertEquals(listOf(properties), propertiesA)
        assertEquals(listOf(properties), propertiesB)
    }
}
