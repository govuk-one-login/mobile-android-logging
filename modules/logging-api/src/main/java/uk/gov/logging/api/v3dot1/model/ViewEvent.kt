package uk.gov.logging.api.v3dot1.model

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.IS_ERROR
import uk.gov.logging.api.analytics.logging.REASON
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.logging.STATUS

sealed class ViewEvent(params: RequiredParameters) : AnalyticsEvent {
    override val eventType: String = FirebaseAnalytics.Event.SCREEN_VIEW
    override val parameters: RequiredParameters = params

    data class Screen(
        private val name: String,
        private val id: String,
        private val params: RequiredParameters,
    ) : ViewEvent(params) {
        private val _screenName get() = name.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _screenId get() = id.take(HUNDRED_CHAR_LIMIT).lowercase()

        override fun asMap(): Map<out String, Any?> = mapOf(
            IS_ERROR to false,
            SCREEN_ID to _screenId,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenId,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        ) + params.asMap()
    }

    data class Error(
        private val name: String,
        private val id: String,
        private val endpoint: String,
        private val reason: String,
        private val status: String,
        private val params: RequiredParameters,
    ) : ViewEvent(params) {
        private val _screenName get() = name.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _screenId get() = id.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _endpoint get() = endpoint.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _reason get() = reason.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _status get() = status.take(HUNDRED_CHAR_LIMIT).lowercase()
        private val _hash get() = (_endpoint + "_" + _status).take(HUNDRED_CHAR_LIMIT).lowercase().md5()

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            IS_ERROR to true,
            SCREEN_ID to _screenId,
            ENDPOINT to _endpoint,
            REASON to _reason,
            STATUS to _status,
            HASH to _hash,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenId,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        ) + params.asMap()
    }
}
