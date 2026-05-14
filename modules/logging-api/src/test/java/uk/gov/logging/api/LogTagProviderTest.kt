package uk.gov.logging.api

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customkey.CustomKey

class LogTagProviderTest : LogTagProvider {
    override val tag: String = "tag"

    private val entries = mutableListOf<LogEntry>()
    private lateinit var logger: Logger

    @BeforeEach
    fun setUp() {
        entries.clear()
        logger = Logger { entry, _ -> entries.add(entry) }
    }

    @AfterEach
    fun verifyTag() {
        Assertions.assertEquals("tag", entries.single().tag)
    }

    @Test
    fun ` test info extension logTag extension function`() {
        logger.info("msg")
    }

    @Test
    fun `test debug  logTag extension function`() {
        logger.debug("msg")
    }

    @Test
    fun `test verbose  logTag extension function`() {
        logger.verbose("msg")
    }

    @Test
    fun `test warning logTag extension function`() {
        logger.warning("msg")
    }

    @Test
    fun `test error logTag extension function`() {
        logger.error("msg", RuntimeException(), CustomKey.StringKey("key", "value"))
    }
}
