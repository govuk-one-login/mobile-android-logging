package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.TypeSubmitForm

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * navigating a User via a Button. This is different to the [LinkParameters] class, due to
 * the lack of a 'domain' String property, or the 'external' Boolean property.
 *
 * @param text The text that the User had interacted with to create this event, such
 * as a Button's text, or the contents of a `TextView`.
 * @param type The type of analytics event. Default TypeSubmitForm.SUBMIT_FORM
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 */
data class ButtonParameters(
    private val text: String,
    private val type: TypeSubmitForm = TypeSubmitForm.SUBMIT_FORM,
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
