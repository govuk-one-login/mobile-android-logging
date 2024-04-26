package uk.gov.logging.api.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event
import uk.gov.documentchecking.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.logging.api.analytics.extensions.MapExtensions.toBundle
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.REASON
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

        private const val DEPRECATION_MESSAGE = "Uses the older GA tracking, which is an issue. " +
            "Replace with `screenView` static factory function with Mapper objects."

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

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun screenView(
            screenClass: String,
            screenName: String,
            documentType: DocumentType = DocumentType.UNDEFINED
        ): AnalyticsEvent {
            val bundle = mutableMapOf(
                FirebaseAnalytics.Param.SCREEN_CLASS to screenClass,
                FirebaseAnalytics.Param.SCREEN_NAME to screenName
            )
            if (!documentType.isUndefined()) {
                bundle[DOCUMENT_TYPE_JOURNEY_KEY] = documentType.journeyType
            }
            return AnalyticsEvent(
                Event.SCREEN_VIEW,
                bundle
            )
        }

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun screenViewError(
            screenClass: String,
            screenName: String,
            documentType: DocumentType = DocumentType.UNDEFINED,
            endpoint: String,
            reason: String
        ): AnalyticsEvent {
            val bundle = mutableMapOf(
                FirebaseAnalytics.Param.SCREEN_CLASS to screenClass,
                FirebaseAnalytics.Param.SCREEN_NAME to screenName,
                ENDPOINT to endpoint,
                REASON to reason
            )
            if (!documentType.isUndefined()) {
                bundle[DOCUMENT_TYPE_JOURNEY_KEY] = documentType.journeyType
            }
            return AnalyticsEvent(
                Event.SCREEN_VIEW,
                bundle
            )
        }

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun navigation(
            isExternal: Boolean,
            section: String,
            text: String,
            type: String,
            url: String
        ) = AnalyticsEvent(
            "navigation",
            mapOf(
                "external" to isExternal,
                "section" to section,
                "text" to text,
                "type" to type,
                "url" to url
            )
        )

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun internalButton(
            section: String,
            text: String,
            url: String
        ) = navigation(
            isExternal = false,
            section = section,
            text = text,
            type = "button",
            url = url.lowercase().take(HUNDRED_CHAR_LIMIT)
        )

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun externalButton(
            section: String,
            text: String,
            url: String
        ) = navigation(
            isExternal = true,
            section = section,
            text = text,
            type = "button",
            url = url.lowercase().take(HUNDRED_CHAR_LIMIT)
        )

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun link(
            section: String,
            text: String,
            url: String
        ) = navigation(
            isExternal = true,
            section = section,
            text = text,
            type = "link",
            url = url.lowercase().take(HUNDRED_CHAR_LIMIT)
        )

        @Deprecated(DEPRECATION_MESSAGE)
        @JvmStatic
        fun screenViewNetworkError(
            endpoint: String,
            hash: String,
            screenClass: String,
            screenName: String
        ): AnalyticsEvent {
            val bundle = mutableMapOf(
                FirebaseAnalytics.Param.SCREEN_CLASS to screenClass,
                FirebaseAnalytics.Param.SCREEN_NAME to screenName,
                ENDPOINT to endpoint,
                HASH to hash
            )
            return AnalyticsEvent(
                Event.SCREEN_VIEW,
                bundle
            )
        }
    }
}
