package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.FORTY_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LOWER_SNAKE_CASE_HUNDRED_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * navigating a User via a Button. This is different to the [LinkParameters] class, due to
 * the lack of a 'domain' String property, or the 'external' Boolean property.
 *
 * This class also handles the necessary parameters for providing a popup event. Please see the unit
 * tests for an example of this.
 *
 * **see also:**
 * - [Navigation event tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%F0%9F%8E%AF-Event-tracking)
 * - [LinkParameters] if you wish to create a URL navigation event by passing in [LinkParameters]
 *   as the value for [overrides].
 *
 * @param callToActionText The text that the User had interacted with to create this event, such
 * as a Button's text, or the contents of a `TextView`.
 * @param name The screen name of the page on which the button click occurs
 * @param type The type of analytics event. This value must be in `lower_snake_case`. Defaults
 * to "navigation."
 * @param action The action performed by the User. Defaults to "submit form."
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 */
data class ButtonParameters(
    private val callToActionText: String,
    private val name: String,
    private val type: String = "navigation",
    private val action: String = "submit form",
    private val overrides: Mapper? = null
) : Mapper {
    private val _callToActionText get() = callToActionText.lowercase()
    private val _eventName get() = type.lowercase()
    private val _eventType get() = action.lowercase()

    init {
        validateCallToActionText()
        validateEventName()
    }

    private fun validateCallToActionText() {
        require(_callToActionText.length <= HUNDRED_CHAR_LIMIT) {
            "The callToActionText parameter length is higher than $HUNDRED_CHAR_LIMIT!: " +
                    "${_callToActionText.length}"
        }
    }

    private fun validateEventName() {
        require(_eventName.length <= FORTY_CHAR_LIMIT) {
            "The eventName parameter length is higher than $FORTY_CHAR_LIMIT!: ${_eventName.length}"
        }
        require(LOWER_SNAKE_CASE_HUNDRED_LIMIT.matcher(_eventName).matches()) {
            "The eventName parameter is not considered lower snake-cased ( " +
                    "${LOWER_SNAKE_CASE_HUNDRED_LIMIT.pattern()} )!: $_eventName"
        }
    }

    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            EVENT_NAME to _eventName,
            TEXT to _callToActionText,
            TYPE to _eventType,
            FirebaseAnalytics.Param.SCREEN_NAME to name
        )

        val commonParameters: Map<out String, Any?> = overrides?.asMap() ?: mapOf()
        bundle.putAll(commonParameters)

        return bundle
    }
}
