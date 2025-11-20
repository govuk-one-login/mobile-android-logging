package uk.gov.logging.api.analytics.logging

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.AnalyticsEvent
import kotlin.test.Test

class MemorisedAnalyticsLoggerTest {
    private val subLogger: AnalyticsLogger = mock()
    private val memorisedAnalyticsLogger = MemorisedAnalyticsLogger(subLogger)

    @Test
    fun `logEvent should log event if not a screen view`() {
        val event = AnalyticsEvent("testEvent", mapOf())
        memorisedAnalyticsLogger.logEvent(true, event)
        verify(subLogger).logEvent(true, event)
    }

    @Test
    fun `logEvent should log screen view event if not a duplicate`() {
        val event1 =
            AnalyticsEvent(
                SCREEN_VIEW,
                mapOf("screen_class" to "TestScreen", "screen_name" to "TestName"),
            )
        val event2 =
            AnalyticsEvent(
                SCREEN_VIEW,
                mapOf("screen_class" to "TestScreen", "screen_name" to "TestName"),
            )
        memorisedAnalyticsLogger.logEvent(true, event1)
        memorisedAnalyticsLogger.logEvent(true, event2)
        verify(subLogger).logEvent(true, event1)
        verify(subLogger).logEvent(true, event2)
    }

    @Test
    fun `logEvent should not log duplicate screen view event`() {
        val event =
            AnalyticsEvent(
                SCREEN_VIEW,
                mapOf("screen_class" to "TestScreen", "screen_name" to "TestName"),
            )
        memorisedAnalyticsLogger.logEvent(true, event)
        memorisedAnalyticsLogger.logEvent(true, event)
        verify(subLogger, times(1)).logEvent(true, event)
        verify(subLogger, times(1)).debugLog(
            "MemorisedAnalyticsLogger",
            "Sent event to log: " +
                "eventType: screen_view; " +
                "screenClass: TestScreen;" +
                "screenName: TestName;",
        )
        verify(subLogger, times(1)).debugLog(
            "MemorisedAnalyticsLogger",
            "Should log event: true - " +
                "Is a screen view: true; " +
                "Is a duplicate: false; " +
                "eventType: screen_view; " +
                "screenClass: TestScreen; " +
                "screenName: TestName; ",
        )
        verify(subLogger, times(1)).debugLog(
            "MemorisedAnalyticsLogger",
            "Should log event: false - " +
                "Is a screen view: true; " +
                "Is a duplicate: true; " +
                "eventType: screen_view; " +
                "screenClass: TestScreen; " +
                "screenName: TestName; ",
        )
    }

    @Test
    fun `logEvent should log multiple events`() {
        val event1 = AnalyticsEvent("testEvent1", mapOf())
        val event2 = AnalyticsEvent("testEvent2", mapOf())
        memorisedAnalyticsLogger.logEvent(true, event1, event2)
        verify(subLogger).logEvent(true, event1)
        verify(subLogger).logEvent(true, event2)
    }
}
