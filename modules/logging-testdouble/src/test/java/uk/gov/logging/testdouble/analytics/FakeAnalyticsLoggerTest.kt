package uk.gov.logging.testdouble.analytics

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.gov.logging.api.analytics.AnalyticsEvent

class FakeAnalyticsLoggerTest {
    private lateinit var logger: FakeAnalyticsLogger

    @BeforeEach
    fun setUp() {
        logger = FakeAnalyticsLogger()
    }

    @Test
    fun `logEvent should add events when shouldLogEvent is true`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())

        logger.logEvent(true, event1, event2)

        assertTrue(event1 in logger)
        assertTrue(event2 in logger)
        assertEquals(2, logger.size)
    }

    @Test
    fun `logEvent should not add events when shouldLogEvent is false`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())

        logger.logEvent(false, event1, event2)

        assertFalse(event1 in logger)
        assertFalse(event2 in logger)
        assertEquals(0, logger.size)
    }

    @Test
    fun `get should return the event at the given index`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        val event3 = AnalyticsEvent("Screen3", mapOf())

        logger.logEvent(true, event1, event2, event3)

        assertEquals(event1, logger[0])
        assertEquals(event2, logger[1])
        assertEquals(event3, logger[2])
    }

    @Test
    fun `logEvent should contain the events`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        val event3 = AnalyticsEvent("Screen3", mapOf())

        logger.logEvent(true, event1, event2, event3)

        assertTrue(logger.contains(logger[0]))
        assertTrue(
            logger.contains {
                it.eventType.contains("Screen")
            },
        )
        assertFalse(
            logger.contains {
                it.eventType.contains("Something wrong")
            },
        )
    }

    @Test
    fun `shouldLog should return true for non-screen view events`() {
        val event = AnalyticsEvent("Screen1", mapOf())
        assertTrue(logger.shouldLog(event))
    }

    @Test
    fun `shouldLog should return true for unique screen view events`() {
        val event = AnalyticsEvent(SCREEN_VIEW, mapOf())
        assertTrue(logger.shouldLog(event))
    }

    @Test
    fun `shouldLog should return false for duplicate screen view events`() {
        val event = AnalyticsEvent(SCREEN_VIEW, mapOf())
        logger.logEvent(true, event)
        assertFalse(logger.shouldLog(event))
    }

    @Test
    fun `setEnabled should update the enabled status`() {
        assertFalse(logger.enabled ?: false)

        logger.setEnabled(true)
        assertTrue(logger.enabled ?: false)

        logger.setEnabled(false)
        assertFalse(logger.enabled ?: false)
    }

    @Test
    fun `contains should return true if event is present`() {
        val event = AnalyticsEvent("Screen1", mapOf())
        logger.logEvent(true, event)
        assertTrue(event in logger)
    }

    @Test
    fun `contains should return false if event is not present`() {
        val event = AnalyticsEvent("Screen1", mapOf())
        assertFalse(event in logger)
    }

    @Test
    fun `iterator should iterate over the logged events`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1, event2)

        val iterator = logger.iterator()
        assertEquals(event1, iterator.next())
        assertEquals(event2, iterator.next())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `contains all should return true if all events are present`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1, event2)

        assertTrue(logger.contains(listOf(event1, event2)))
    }

    @Test
    fun `contains all should return false if not all events are present`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1)

        assertFalse(logger.contains(listOf(event1, event2)))
    }

    @Test
    fun `containsOnly should return true if the logger contains only the given events`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1, event2)

        assertTrue(logger.containsOnly(listOf(event1, event2)))
    }

    @Test
    fun `containsOnly should return false if the logger does not contain only the given events`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1)

        assertThrows<IllegalArgumentException> {
            (logger.containsOnly(listOf(event1, event2)))
        }
    }

    @Test
    fun `filter should return a list of events matching the predicate`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        val event2 = AnalyticsEvent("Screen2", mapOf())
        logger.logEvent(true, event1, event2)

        val filteredEvents = logger.filter { it.eventType == "Screen2" }
        assertEquals(1, filteredEvents.size)
        assertEquals(event2, filteredEvents[0])
    }

    @Test
    fun `toString should return a string representation of the logger`() {
        val event1 = AnalyticsEvent("Screen1", mapOf())
        logger.logEvent(true, event1)
        assertEquals("FakeAnalyticsLogger(size=1, events=[$event1])", logger.toString())
    }
}
