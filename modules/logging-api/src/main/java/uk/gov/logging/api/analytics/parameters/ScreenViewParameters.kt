package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LOWER_ALPHANUMERIC_FORTY_LIMIT
import uk.gov.logging.api.analytics.logging.LOWER_ALPHANUMERIC_HUNDRED_LIMIT
import uk.gov.logging.api.analytics.logging.TITLE
import uk.gov.logging.api.analytics.logging.UPPER_SNAKE_CASE_FORTY_LIMIT

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * a User viewing a new screen.
 *
 * **see also:**
 * - See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 * - [Manually track screen view events](https://firebase.google.com/docs/analytics/screenviews#manually_track_screens)
 *
 * @param screenClass The backend identifier of the screen. This is most commonly the fragment name.
 * Must be under 40 characters in length. This class internally handles the lower casing of the
 * String.
 * @param name Unique name for each app screen, irrespective of the [screenClass] used to
 * identify pathways in the user journey. Must be `UPPER_SNAKE_CASE` and under 40 characters in
 * length. This class internally handles the upper casing of the String.
 * @param overrides [Mapper] object that's applied after the setup specific to this class. This
 * is most often a [RequiredParameters] object.
 * @param title The frontend display title. Must be lowercase and under 100 characters in length.
 * This class internally handles the lower casing of the String.
 */
data class ScreenViewParameters(
    private val name: String,
    private val clazz: ScreenClass,
    private val id: String,
    private val event: String = FirebaseAnalytics.Event.SCREEN_VIEW,
    private val overrides: Mapper? = null
) : Mapper {

    private val _eventName get() = event.lowercase()
    private val _screenClass get() = clazz.value.take(HUNDRED_CHAR_LIMIT)
    private val _screenName get() = name.uppercase().take(HUNDRED_CHAR_LIMIT)

    init {
        validateScreenClass()
        validateScreenName()
    }

    private fun validateScreenClass() {
        require(LOWER_ALPHANUMERIC_HUNDRED_LIMIT.matcher(_screenClass).matches()) {
            "The screenClass parameter is not considered to be lower-cased alphanumeric ( " +
                "${LOWER_ALPHANUMERIC_FORTY_LIMIT.pattern()} )!: $_screenClass"
        }
    }

    private fun validateScreenName() {
        require(UPPER_SNAKE_CASE_FORTY_LIMIT.matcher(_screenName).matches()) {
            "The screenName parameter is not considered to be upper snake cased ( " +
                "${UPPER_SNAKE_CASE_FORTY_LIMIT.pattern()} )!: $_screenName"
        }
    }

    @CallSuper
    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            EVENT_NAME to _eventName,
            FirebaseAnalytics.Param.SCREEN_CLASS to _screenClass,
            FirebaseAnalytics.Param.SCREEN_NAME to _screenName,
        )

        val overwrittenValues: Map<out String, Any?> = overrides?.asMap() ?: mapOf()

        bundle.putAll(overwrittenValues)

        return bundle
    }
}
