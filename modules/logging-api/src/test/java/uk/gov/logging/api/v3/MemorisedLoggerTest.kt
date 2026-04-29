package uk.gov.logging.api.v3

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.hasException
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.hasMessage
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.hasTag
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.isExceptionInstance
import uk.gov.logging.api.v3.matchers.LogEntryMatchers.isMessageEntry
import uk.gov.logging.api.v3.matchers.MemorisedLoggerMatchers.hasSize

class MemorisedLoggerTest {
    private val tag = "tag"
    private val message = "msg"
    private val throwable = RuntimeException("error")
    private val logger = MemorisedLogger()

    @Test
    fun `log stores entry`() {
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertThat(
            logger,
            hasSize(logger.size),
        )

        assertThat(
            logger,
            contains(
                allOf(
                    isMessageEntry(),
                    hasMessage(message),
                    hasTag(tag),
                ),
            ),
        )
    }

    @Test
    fun `log delegates to sub logger`() {
        val delegated = mutableListOf<LogEntry>()
        val logger = MemorisedLogger(subLogger = Logger { delegated.add(it) })
        val entry = LogEntry.Info(tag = tag, message = message)

        logger.log(entry)

        assertThat(delegated, contains(entry))
    }

    @Test
    fun `contains matches by message string`() {
        val logger = MemorisedLogger()
        logger.log(LogEntry.Info(tag = tag, message = message))

        assertThat(
            logger,
            contains(
                allOf(
                    isMessageEntry(),
                    hasMessage(message),
                    hasTag(tag),
                ),
            ),
        )
    }

    @Test
    fun `contains matches by throwable`() {
        logger.log(LogEntry.Error(tag = tag, message = message, throwable = throwable))

        assertThat(
            logger,
            contains(
                allOf(
                    isExceptionInstance(),
                    hasException(equalTo(throwable)),
                ),
            ),
        )

        assertThat(
            logger,
            not(contains(hasException(equalTo(RuntimeException("other"))))),
        )
    }

    @Test
    fun `iterates over entries`() {
        val entry = LogEntry.Info(tag = tag, message = message)
        logger.log(entry)

        assertThat(listOf(entry), equalTo(logger.toList()))
    }
}
