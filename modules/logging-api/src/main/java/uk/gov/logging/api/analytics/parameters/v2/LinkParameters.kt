package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.Type

@Suppress("MaxLineLength")
/**
 * Data class to build the key value parameter pairs required for the TrackEventLink AnalyticsEvent.
 * Use TrackEventLink to monitor the number of times users interact with links in the app.
 * This includes all link type.
 *
 * **see also:**
 * - https://govukverify.atlassian.net/wiki/spaces/PI/pages/3790995627/GA4+One+Login+Mobile+Application+Data+Schema+V3.1
 *
 * @param domain The domain of the accessed URL, such as `www.gov.uk`
 * @param `isExternal` Whether the navigation is accessing a URL that's considered external
 * @param text The lower case text displayed to the user of the hyperlink/cta that triggered
 * a navigation event
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class
 *
 * @see RequiredParameters for the rest of the required parameters for a navigation event.
 */
data class LinkParameters(
    private val isExternal: Boolean,
    private val domain: String,
    private val text: String,
    private val overrides: Mapper? = null
) : Mapper {
    private val _linkDomain get() = domain.take(HUNDRED_CHAR_LIMIT)
    private val _isExternal get() = isExternal.toString()
    private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

    override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            EXTERNAL to _isExternal,
            LINK_DOMAIN to _linkDomain,
            TEXT to _text,
            TYPE to Type.Link.value,
        ) + overrides?.asMap().orEmpty()
}
