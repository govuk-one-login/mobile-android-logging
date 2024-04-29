package uk.gov.logging.api.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics.Event
import uk.gov.logging.api.analytics.extensions.MapExtensions.toBundle
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.parameters.Mapper

/**
 * Wrapper class to contain information used when sending [Firebase] events.
 */
@Suppress("UnusedPrivateMember")
data class AnalyticsEvent(
    val eventType: String,
    val parameters: Map<String, Any?>
) {

    fun toBundle(): Bundle = parameters.toBundle()

    fun isScreenView(): Boolean =
        this.eventType == Event.SCREEN_VIEW

    companion object {
        @JvmStatic
        fun screenView(vararg parameters: Mapper): AnalyticsEvent {
            val bundle = mutableMapOf<String, Any?>()

            parameters.forEach {
                bundle.putAll(it.asMap())
            }
            val eventName = (bundle[EVENT_NAME] as? String) ?: "screen_view"

            return AnalyticsEvent(
                eventName,
                bundle
            )
        }

        @JvmStatic
        fun trackEvent(vararg parameters: Mapper): AnalyticsEvent = screenView(*parameters)
    }
}
