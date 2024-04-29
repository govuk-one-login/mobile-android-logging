package uk.gov.logging.api.analytics.logging

import com.google.firebase.analytics.FirebaseAnalytics.Event
import javax.inject.Inject
import uk.gov.logging.api.analytics.AnalyticsEvent

class MemorisedAnalyticsLogger @Inject constructor(
    private val subLogger: AnalyticsLogger
) : AnalyticsLogger {
    private var memorisedEvent: AnalyticsEvent? = null

    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent
    ) {
        events.forEach { event ->
            if (shouldLog(event)) {
                val eventType = event.eventType
                val screenClass = event.parameters["screen_class"].toString()
                val screenName = event.parameters["screen_name"].toString()

                if (isScreenView(event)) {
                    memorisedEvent = event
                }

                subLogger.logEvent(shouldLogEvent, event).also {
                    subLogger.debugLog(
                        tag = this::class.java.simpleName,
                        msg = "Sent event to log: " +
                            "eventType: $eventType; " +
                            "screenClass: $screenClass;" +
                            "screenName: $screenName;"
                    )
                }
            }
        }
    }

    private fun shouldLog(event: AnalyticsEvent): Boolean {
        val isScreenView = isScreenView(event)
        val isDuplicateScreenView = isDuplicateScreenView(event)

        val result = !isScreenView || !isDuplicateScreenView

        return result.also {
            if (isScreenView(event)) {
                val eventType = event.eventType
                val screenClass = event.parameters["screen_class"].toString()
                val screenName = event.parameters["screen_name"].toString()

                subLogger.debugLog(
                    tag = this::class.java.simpleName,
                    msg = "Should log event: $result - " +
                        "Is a screen view: $isScreenView; " +
                        "Is a duplicate: $isDuplicateScreenView; " +
                        "eventType: $eventType; " +
                        "screenClass: $screenClass; " +
                        "screenName: $screenName; "
                )
            }
        }
    }

    private fun isScreenView(event: AnalyticsEvent): Boolean = event.eventType == Event.SCREEN_VIEW

    private fun isDuplicateScreenView(event: AnalyticsEvent): Boolean = event == memorisedEvent

    override fun setEnabled(isEnabled: Boolean) = subLogger.setEnabled(isEnabled)

    companion object {
        const val INSTANCE_STATE_KEY = "MEMORISED_EVENTS"
    }
}
