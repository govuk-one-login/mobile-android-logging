package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.TypeCallToAction

@Suppress("MaxLineLength")
/**
 * Event monitoring the number of times users are shown popup action menus
 *
 * @param text The text that the User had interacted with to create this event
 * @param type The type of analytics event
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 */
data class PopupParameters(
    private val text: String,
    private val type: TypeCallToAction = TypeCallToAction.CTA,
    private val overrides: Mapper? = null
) : Mapper {
    private val _text get() = text.take(HUNDRED_CHAR_LIMIT).lowercase()

    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            TEXT to _text,
            TYPE to type.value,
        )

        val commonParameters: Map<out String, Any?> = overrides?.asMap() ?: mapOf()
        bundle.putAll(commonParameters)

        return bundle
    }
}
