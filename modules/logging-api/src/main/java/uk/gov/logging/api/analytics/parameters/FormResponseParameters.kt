package uk.gov.logging.api.analytics.parameters

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * a Users response to a form.
 *
 * **see also:**
 * - [Navigation event tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%F0%9F%8E%AF-Event-tracking)
 *
 * @param text The en text shown to the User for the selected option.
 * @param response Comma separated list of selected options. Max length of 100 characters.
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [ButtonParameters] object, due to the additional parameters
 * needed for Navigation.
 *
 * @see ButtonParameters for the rest of the required parameters for a navigation event.
 */
data class FormResponseParameters(
    private val name: String,
    private val response: Array<String>,
    private val text: String,
    private val overrides: Mapper? = null

) : Mapper {
    private val _response get() = response.joinToString(
        separator = ","
    ).take(HUNDRED_CHAR_LIMIT)
    private val _text get() = text.lowercase()

    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            RESPONSE to _response,
            TEXT to _text,
            FirebaseAnalytics.Param.SCREEN_NAME to name
        )

        val commonParameters: Map<out String, Any?> = overrides?.asMap() ?: mapOf()
        bundle.putAll(commonParameters)

        return bundle
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormResponseParameters) return false

        if (name != other.name) return false
        if (!response.contentEquals(other.response)) return false
        if (text != other.text) return false
        if (overrides != other.overrides) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + response.contentHashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (overrides?.hashCode() ?: 0)
        return result
    }
}
