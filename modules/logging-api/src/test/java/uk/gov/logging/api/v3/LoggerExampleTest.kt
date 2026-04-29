package uk.gov.logging.api.v3

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.hasLogEntry
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.isExceptionInstance
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.isMessageEntry

class LoggerExampleTest {
    private val logger = MemorisedLogger()
    private val example = LoggerExample(logger)

    @BeforeEach
    fun setUp() {
        example.example()
    }

    @Test
    fun `example logs message entries`() {
        assertThat(logger, hasLogEntry(hasItem(isMessageEntry())))
    }

    @Test
    fun `example logs exception entries`() {
        assertThat(logger, hasLogEntry(hasItem(isExceptionInstance())))
    }
}
