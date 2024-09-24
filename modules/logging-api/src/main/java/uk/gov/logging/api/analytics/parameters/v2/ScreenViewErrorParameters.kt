package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.REASON
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.logging.STATUS
import uk.gov.logging.api.analytics.parameters.Mapper

/**
 * Data class to build the key value parameter pairs required for the screenView AnalyticsEvent.
 * Use ScreenViews to track how many times users land on screens.
 *
 * @param name title displayed to the user on the app screen.
 * @param id The lower cased unique identifier used to monitor the lifecycle of a given screen.
 * @param endpoint The API endpoint called associated to the error.
 * @param status The lowercase status returned by an API call.
 * @param overrides [Mapper] object that's applied after the setup specific to this class. This
 * is most often a [RequiredParameters] object.
 *
 * @see RequiredParameters for the rest of the required parameters for a navigation event.
 */
data class ScreenViewErrorParameters(
    private val name: String,
    private val id: String,
    private val endpoint: String,
    private val reason: String,
    private val status: String,
    private val overrides: Mapper? = null
) : Mapper {
    private val _screenName get() = name.take(HUNDRED_CHAR_LIMIT)
    private val _screenId get() = id.take(HUNDRED_CHAR_LIMIT)
    private val _endpoint get() = endpoint.take(HUNDRED_CHAR_LIMIT)
    private val _reason get() = reason.take(HUNDRED_CHAR_LIMIT)
    private val _status get() = status.take(HUNDRED_CHAR_LIMIT).lowercase()
    private val _hash get() = (_endpoint + "_" + _status).take(HUNDRED_CHAR_LIMIT).lowercase().md5()

    override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            SCREEN_ID to _screenId,
            ENDPOINT to _endpoint,
            REASON to _reason,
            STATUS to _status,
            HASH to _hash,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenId,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        ) + overrides?.asMap().orEmpty()
}
