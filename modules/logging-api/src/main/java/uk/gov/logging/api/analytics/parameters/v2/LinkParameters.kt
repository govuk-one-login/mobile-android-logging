package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ButtonParameters
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.TypeGenericLink

@Suppress("MaxLineLength")
/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * navigating a User via a URL. Because of this, there is a need for both the [domain] and whether
 * the link is [external] or not.
 *
 * **see also:**
 * - https://govukverify.atlassian.net/wiki/spaces/PI/pages/3790995627/GA4+One+Login+Mobile+Application+Data+Schema+V3.1
 *
 * @param domain The domain of the accessed URL, such as `www.gov.uk`
 * @param external Whether the navigation is accessing a URL that's considered external
 * @param text The lower case text displayed to the user of the hyperlink/cta that triggered
 * a navigation event
 * @param type Defaults to TypeGenericLink.GENERIC_LINK
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class
 *
 * @see ButtonParameters for the rest of the required parameters for a navigation event.
 */
data class LinkParameters(
    private val external: Boolean,
    private val domain: String,
    private val text: String,
    private val type: TypeGenericLink = TypeGenericLink.GENERIC_LINK,
    private val overrides: Mapper? = null
) : Mapper {
    private val _linkDomain get() = domain.take(HUNDRED_CHAR_LIMIT).lowercase()
    private val _external get() = external.toString().lowercase()
    private val _text get() = text.take(HUNDRED_CHAR_LIMIT).lowercase()

    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            EXTERNAL to _external,
            LINK_DOMAIN to _linkDomain,
            TEXT to _text,
            TYPE to type.value,
        )

        val commonParameters: Map<out String, Any?> = overrides?.asMap() ?: mapOf()
        bundle.putAll(commonParameters)

        return bundle
    }
}
