package uk.gov.logging.impl.analytics.v3

import com.google.firebase.analytics.FirebaseAnalytics
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.impl.v3.LogcatLogger

internal class FirebaseAnalyticsLoggerTest {
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var logcatLogger: LogcatLogger
    private lateinit var analyticsLogger: FirebaseAnalyticsLogger

    @BeforeEach
    fun setup() {
        analytics = mock()
        logcatLogger = mock()
        analyticsLogger =
            FirebaseAnalyticsLogger(
                analytics = analytics,
                logcatLogger = logcatLogger,
                setCollectionEnabled = { analytics.setAnalyticsCollectionEnabled(it) },
            )
    }

    @Test
    fun `given shouldLog is true then it logs the event`() {
        analyticsLogger.logEvent(true, event)

        verify(analytics, times(1)).logEvent(eq(event.eventType), any())
    }

    @Test
    fun `given shouldLog is false then it does not log the event`() {
        analyticsLogger.logEvent(false, event)

        verify(analytics, never()).logEvent(any(), any())
    }

    @Test
    fun `given shouldLog is true then it logs a debug message`() {
        analyticsLogger.logEvent(true, event)

        verify(logcatLogger, times(1)).log(
            eq(LogEntry.Debug(tag = "FirebaseAnalyticsLogger", message = "Should log event: true")),
            eq(LoggingProperties(true)),
        )
    }

    @Test
    fun `given shouldLog is false then it still logs a debug message`() {
        analyticsLogger.logEvent(false, event)

        verify(logcatLogger, times(1)).log(
            eq(LogEntry.Debug(tag = "FirebaseAnalyticsLogger", message = "Should log event: false")),
            eq(LoggingProperties(false)),
        )
    }

    @Test
    fun `setEnabled should update the enabled status to false`() {
        analyticsLogger.setEnabled(false)

        verify(analytics, times(1)).setAnalyticsCollectionEnabled(false)
    }

    @Test
    fun `Given enabled is true When setEnabled is called Then enable analytics`() {
        analyticsLogger.setEnabled(true)

        verify(analytics, times(1)).setAnalyticsCollectionEnabled(true)
    }

    @Test
    fun `given multiple events then all events are logged`() {
        analyticsLogger.logEvent(true, event, event, event)

        verify(analytics, times(3)).logEvent(eq(event.eventType), any())
    }

    companion object {
        private val event =
            AnalyticsEvent.screenView(
                RequiredParameters(
                    digitalIdentityJourney = "",
                    journeyType = "driving licence",
                ),
            )
    }
}
