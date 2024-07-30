package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.TypeActionMenu

@Suppress("MaxLineLength")
/**
 * Event monitoring the number of times users are shown popup action menus
 * (those that do not require user select to dismiss)
 *
 * @param text The text that the User had interacted with to create this event
 * @param type The type of analytics event
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 */
data class ActionMenuParameters(
    private val text: String,
    private val type: TypeActionMenu = TypeActionMenu.ACTION_MENU,
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
