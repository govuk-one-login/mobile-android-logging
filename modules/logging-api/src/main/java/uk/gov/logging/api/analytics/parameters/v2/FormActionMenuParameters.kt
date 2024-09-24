package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.Type

/**
 * Data class to build the key value parameter pairs required for the TrackEventFormActionMenu
 * AnalyticsEvent. Use TrackEventFormActionMenu to monitor the number of times users
 * respond to questions in a dismissible call to action form i.e. delete wallet document.
 *
 *
 * @param text The text that the User had interacted with to create this event
 * @param response the answer to a call to action
 * @param overrides [Mapper] object that's applied after the setup specific to this class
 *
 * @see RequiredParameters for the rest of the required parameters for a navigation event.
 */
data class FormActionMenuParameters(
    private val text: String,
    private val response: String,
    private val overrides: Mapper? = null
) : Mapper {
    private val _text get() = text.take(HUNDRED_CHAR_LIMIT)
    private val _response get() = response.take(HUNDRED_CHAR_LIMIT)

    override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            RESPONSE to _response,
            TYPE to Type.ActionMenu.value,
        ) + overrides?.asMap().orEmpty()
}
