package uk.gov.logging.api.v3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MemorisedLoggerTest {
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")
    private val logger = MemorisedLogger()

    @Test
    fun `log stores entry`() {
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertEquals(1, logger.size)
        assertEquals(entry, logger[0])
    }

    @Test
    fun `log delegates to sub logger`() {
        val delegated = mutableListOf<LogEntry>()
        val logger = MemorisedLogger(subLogger = Logger { delegated.add(it) })
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertEquals(listOf(entry), delegated)
    }

    @Test
    fun `contains matches by message string`() {
        val logger = MemorisedLogger()
        logger.log(LogEntry.Info(tag = tag, message = message))

        assertTrue(logger.contains(message))
        assertFalse(logger.contains("other"))
    }

    @Test
    fun `contains matches by throwable`() {
        logger.log(LogEntry.Error(tag = tag, message = message, throwable = throwable))

        assertTrue(logger.contains(throwable))
        assertFalse(logger.contains(RuntimeException("other")))
    }

    @Test
    fun `iterates over entries`() {
        val entry = LogEntry.Info(tag = tag, message = message)
        logger.log(entry)

        assertEquals(listOf(entry), logger.toList())
    }
}
