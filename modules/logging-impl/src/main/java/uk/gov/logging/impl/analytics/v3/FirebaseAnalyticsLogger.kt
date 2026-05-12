package uk.gov.logging.impl.analytics.v3

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.v3.AnalyticsLogger
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.impl.analytics.extensions.setCollectionEnabled
import uk.gov.logging.impl.v3.LogcatLogger

class FirebaseAnalyticsLogger(
    private val analytics: FirebaseAnalytics,
    private val logcatLogger: LogcatLogger,
    private val setCollectionEnabled: (Boolean) -> Unit = { Firebase.setCollectionEnabled(it) },
) : AnalyticsLogger,
    LogTagProvider {
    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    ) {
        logcatLogger.log(
            LogEntry.Debug(
                tag = tag,
                message = "Should log event: $shouldLogEvent",
            ),
            LoggingProperties(allowRemote = shouldLogEvent),
        )
        if (shouldLogEvent) {
            events.forEach(::internalLogEvent)
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        setCollectionEnabled(isEnabled)
    }

    /**
     * Bundles the [AnalyticsEvent] and logs it to Firebase Analytics.
     *
     * @param event The analytics event to log
     */
    private fun internalLogEvent(event: AnalyticsEvent) {
        val bundledParameters = event.toBundle()
        analytics.logEvent(event.eventType, bundledParameters)
        logcatLogger.log(
            LogEntry.Debug(
                tag = tag,
                message = "Firebase event sent with: $bundledParameters",
            ),
        )
    }
}
