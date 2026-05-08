package uk.gov.logging.impl.analytics.v3

import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class FirebaseAnalyticsLoggerTest {
    private lateinit var analytics: FirebaseAnalytics

    private lateinit var logcatLogger: LogcatLogger

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @BeforeEach
    fun setup() {
        analytics = mock()
        logcatLogger = mock()
    }

    private val analyticsLogger by lazy {
        FirebaseAnalyticsLogger(
            analytics = analytics,
            logcatLogger = logcatLogger,
            setCollectionEnabled = { analytics.setAnalyticsCollectionEnabled(it) },
            externalScope = testScope,
            dispatcher = testDispatcher,
        )
    }

    @Test
    fun `given shouldLog is true then it logs the event`() =
        runTest(testDispatcher) {
            analyticsLogger.logEvent(true, event)
            advanceUntilIdle()

            verify(analytics, times(1)).logEvent(eq(event.eventType), any())
        }

    @Test
    fun `given shouldLog is false then it does not log the event`() =
        runTest(testDispatcher) {
            analyticsLogger.logEvent(false, event)
            advanceUntilIdle()

            verify(analytics, never()).logEvent(any(), any())
        }

    @Test
    fun `given shouldLog is true then it logs a debug message`() =
        runTest(testDispatcher) {
            analyticsLogger.logEvent(true, event)
            advanceUntilIdle()

            verify(logcatLogger, times(1)).log(
                eq(LogEntry.Debug(tag = "FirebaseAnalyticsLogger", message = "Should log event: true")),
                eq(LoggingProperties(true)),
            )
        }

    @Test
    fun `given shouldLog is false then it still logs a debug message`() =
        runTest(testDispatcher) {
            analyticsLogger.logEvent(false, event)
            advanceUntilIdle()

            verify(logcatLogger, times(1)).log(
                eq(LogEntry.Debug(tag = "FirebaseAnalyticsLogger", message = "Should log event: false")),
                eq(LoggingProperties(false)),
            )
        }

    @Test
    fun `setEnabled should update the enabled status to false`() =
        runTest(testDispatcher) {
            analyticsLogger.setEnabled(false)
            advanceUntilIdle()
            verify(analytics, times(1)).setAnalyticsCollectionEnabled(false)
        }

    @Test
    fun `Given enabled is true When setEnabled is called Then enable analytics`() =
        runTest(testDispatcher) {
            analyticsLogger.setEnabled(true)
            advanceUntilIdle()
            verify(analytics, times(1)).setAnalyticsCollectionEnabled(true)
        }

    @Test
    fun `given multiple events ensure they are logged in firebase analytics logger`() =
        runTest(testDispatcher) {
            val event1 =
                AnalyticsEvent.screenView(
                    RequiredParameters(
                        digitalIdentityJourney = "journey1",
                        journeyType = "type1",
                    ),
                )
            val event2 =
                AnalyticsEvent.screenView(
                    RequiredParameters(
                        digitalIdentityJourney = "journey2",
                        journeyType = "type2",
                    ),
                )
            val event3 =
                AnalyticsEvent.screenView(
                    RequiredParameters(
                        digitalIdentityJourney = "journey3",
                        journeyType = "type3",
                    ),
                )

            analyticsLogger.logEvent(true, *arrayOf(event1, event2, event3))
            advanceUntilIdle()

            verify(analytics, times(3)).logEvent(any(), any())
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
