package uk.gov.logging.api.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics.Event
import uk.gov.logging.api.analytics.extensions.MapExtensions.toBundle
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.parameters.ButtonParameters
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.api.analytics.parameters.ScreenViewParameters

/**
 * Wrapper class to contain information used when sending [Firebase] events.
 */
@Suppress("UnusedPrivateMember")
data class AnalyticsEvent(
    val eventType: String,
    val parameters: Map<String, Any?>,
) {
    fun toBundle(): Bundle = parameters.toBundle()

    fun isScreenView(): Boolean = this.eventType == Event.SCREEN_VIEW

    companion object {
        /**
         * Method for logging screen view analytics events.
         *
         * See [One Login Mobile Application Data Schema V1.0](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3790995627/GA4+One+Login+Mobile+Application+Data+Schema+V1.0+amended+to+V1.1#Tracked-Events).
         * See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
         *
         * @param parameters sent as part of analytics event. Should include a
         * [RequiredParameters] object and additionally any other parameters objects that are
         * relevant for the analytics event, most commonly [ScreenViewParameters] or
         * [ButtonParameters] objects.
         */
        @JvmStatic
        fun screenView(vararg parameters: Mapper): AnalyticsEvent {
            val bundle = mutableMapOf<String, Any?>()

            parameters.forEach {
                bundle.putAll(it.asMap())
            }
            val eventName = (bundle[EVENT_NAME] as? String) ?: Event.SCREEN_VIEW

            return AnalyticsEvent(
                eventName,
                bundle,
            )
        }

        /**
         * Method for logging non screen view analytics events, such as button presses and
         * navigation. Simply defers to the [screenView] method, but named differently to align with
         * the analytics specification.
         *
         * See [One Login Mobile Application Data Schema V1.0](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3790995627/GA4+One+Login+Mobile+Application+Data+Schema+V1.0+amended+to+V1.1#Tracked-Events).
         *
         * @param parameters Takes the same arguments as the [screenView] method.
         */
        @JvmStatic
        fun trackEvent(vararg parameters: Mapper): AnalyticsEvent = screenView(*parameters)
    }
}
