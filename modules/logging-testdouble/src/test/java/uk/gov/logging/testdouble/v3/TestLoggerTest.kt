package uk.gov.logging.testdouble.v3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customkey.CustomKey

internal class TestLoggerTest {
    private val logger = TestLogger()
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")

    @Test
    fun `log stores entry`() {
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertEquals(1, logger.size)
        assertEquals(entry, logger[0])
    }

    @Test
    fun `contains matches by message string`() {
        logger.log(LogEntry.Info(tag = tag, message = message))

        assertTrue(logger.contains(message))
        assertFalse(logger.contains("other"))
    }

    @Test
    fun `contains matches message entry by equality`() {
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertTrue(entry in logger)
    }

    @Test
    fun `contains matches exception entry by field comparison`() {
        val entry =
            LogEntry.Error(
                tag = tag,
                message = message,
                throwable = throwable,
                customKeys = listOf(CustomKey.IntKey("k", 1)),
            )

        logger.log(entry)

        assertTrue(entry in logger)
    }

    @Test
    fun `contains returns false for non-matching entry`() {
        logger.log(LogEntry.Info(tag = tag, message = message))

        assertFalse(LogEntry.Info(tag = "other", message = message) in logger)
    }

    @Test
    fun `any delegates predicate to entries`() {
        logger.log(LogEntry.Info(tag = tag, message = message))

        assertTrue(logger.any { it.tag == tag })
        assertFalse(logger.any { it.tag == "other" })
    }
}
