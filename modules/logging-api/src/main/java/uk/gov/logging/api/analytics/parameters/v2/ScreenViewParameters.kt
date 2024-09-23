package uk.gov.logging.api.analytics.parameters.v2

import androidx.annotation.CallSuper
import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.logging.LOWER_SNAKE_CASE_HUNDRED_LIMIT
import uk.gov.logging.api.analytics.parameters.Mapper

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * a User viewing a new screen.
 *
 * **see also:**
 * - See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 * - [Manually track screen view events](https://firebase.google.com/docs/analytics/screenviews#manually_track_screens)
 *
 * @param name Unique name for each app screen, irrespective of the [clazz] used to
 * identify pathways in the user journey. Must be `lower_snake_case` and under 100 characters in
 * length. This class internally handles the lower casing of the String.
 * @param id The lower cased unique identifier used to monitor the lifecycle of a given screen
 * irrespective of copy updates
 * @param overrides [Mapper] object that's applied after the setup specific to this class. This
 * is most often a [RequiredParameters] object.
 */
data class ScreenViewParameters(
    private val name: String,
    private val id: String,
    private val event: String = FirebaseAnalytics.Event.SCREEN_VIEW,
    private val overrides: Mapper? = null
) : Mapper {
    private val _screenName get() = name.lowercase().take(HUNDRED_CHAR_LIMIT)
    private val _screenId get() = id.lowercase().take(HUNDRED_CHAR_LIMIT)
    private val _eventName get() = event.lowercase()

    init {
        validateScreenName()
    }

    private fun validateScreenName() {
        require(LOWER_SNAKE_CASE_HUNDRED_LIMIT.matcher(_screenName).matches()) {
            "The screenName parameter is not considered to be lower snake cased ( " +
                    "${LOWER_SNAKE_CASE_HUNDRED_LIMIT.pattern()} )!: $_screenName"
        }
    }

    @CallSuper
    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            EVENT_NAME to _eventName,
            SCREEN_ID to _screenId,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenId,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        )

        val overwrittenValues: Map<out String, Any?> = overrides?.asMap() ?: mapOf()

        bundle.putAll(overwrittenValues)

        return bundle
    }
}
