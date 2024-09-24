package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.Type

/**
 * Data class to build the key value parameter pairs required for the TrackEventIcon AnalyticsEvent.
 * Use TrackEventIcon to monitor the number of times users interact with icons in the app
 * that trigger actions in the app.
 *
 * @param text The text that the User had interacted with to create this event
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 *
 * @see RequiredParameters for the rest of the required parameters for a navigation event.
 */
data class IconParameters(
    private val text: String,
    private val overrides: Mapper? = null
) : Mapper {
    private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

    override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            TYPE to Type.Icon.value,
        ) + overrides?.asMap().orEmpty()
}
