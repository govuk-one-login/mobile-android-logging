package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.parameters.Mapper

@Suppress("MaxLineLength")
/**
 * Data class to build the key value parameter pairs required for the screenView AnalyticsEvent.
 * Use ScreenViews to track how many times users land on screens.
 *
 * **see also:**
 * - See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 *
 * @param name title displayed to the user on the app screen.
 * @param id The lower cased unique identifier used to monitor the lifecycle of a given screen.
 * @param overrides [Mapper] object that's applied after the setup specific to this class. This
 * is most often a [RequiredParameters] object.
 *
 * @see RequiredParameters for the rest of the required parameters for a navigation event.
 */
data class ScreenViewParameters(
    private val name: String,
    private val id: String,
    private val overrides: Mapper? = null
) : Mapper {
    private val _screenName get() = name.take(HUNDRED_CHAR_LIMIT)
    private val _screenId get() = id.take(HUNDRED_CHAR_LIMIT)

    override fun asMap(): Map<out String, Any?> = mapOf(
            SCREEN_ID to _screenId,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenId,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        ) + overrides?.asMap().orEmpty()
}
