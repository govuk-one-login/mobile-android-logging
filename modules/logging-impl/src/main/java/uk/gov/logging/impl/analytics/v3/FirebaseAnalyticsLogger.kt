package uk.gov.logging.impl.analytics.v3

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
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
            events.forEach { event ->
                internalLogEvent(event)
            }
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        setCollectionEnabled(isEnabled)
    }

    /**
     * Delay the thread for 1 millisecond before and after each analytics event to enforce
     * that separate events don't get sent at exactly the same time stamp, which would
     * cause Firebase to display them under a single event.
     */
    private fun internalLogEvent(event: AnalyticsEvent) {
        CoroutineScope(dispatcher).launch {
            delay(1)
            val bundledParameters = event.toBundle()
            analytics.logEvent(event.eventType, bundledParameters)
        }
    }
}
