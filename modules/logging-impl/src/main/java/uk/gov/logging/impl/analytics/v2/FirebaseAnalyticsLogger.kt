package uk.gov.logging.impl.analytics.v2

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.v2.LogTagProvider
import uk.gov.logging.api.v2.Logger
import uk.gov.logging.impl.analytics.extensions.setCollectionEnabled

/**
 * Analytics Logging implementation that utilises the static
 * [com.google.firebase.analytics.FirebaseAnalytics] object for logging [AnalyticsEvent]s.
 */

class FirebaseAnalyticsLogger(
    private val analytics: FirebaseAnalytics,
    private val logger: Logger,
) : AnalyticsLogger,
    LogTagProvider {
    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    ) {
        debugLog(
            tag = tag,
            msg = "Should log event: $shouldLogEvent",
        )
        if (shouldLogEvent) {
            events.forEach(::internalLogEvent)
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        Firebase.setCollectionEnabled(isEnabled)
    }

    /**
     * Delay the thread for 1 millisecond before and after each analytics event to enforce
     * that separate events don't get sent at exactly the same time stamp, which would
     * cause Firebase to display them under a single event.
     */
    private fun internalLogEvent(event: AnalyticsEvent) {
        runBlocking(Dispatchers.Default) {
            delay(1)
            val bundledParameters = event.toBundle()
            analytics.logEvent(event.eventType, bundledParameters)
            debugLog(
                tag = tag,
                msg = "Firebase event sent with: $bundledParameters",
            )
            delay(1)
        }
    }

    override fun debugLog(
        tag: String,
        msg: String,
    ) = logger.debug(tag, msg)
}
