@file:Suppress("ktlint:standard:backing-property-naming", "MaxLineLength")

package uk.gov.logging.api.analytics.parameters

import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN

/**
 * Data class to contain the necessary values required for creating an Analytics event based on
 * navigating a User via a URL. Because of this, there is a need for both the [domain] and whether
 * the link is [external] or not.
 *
 * **see also:**
 * - [Navigation event tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%F0%9F%8E%AF-Event-tracking)
 *
 * @param domain The domain of the accessed URL, such as `www.gov.uk`. Defaults to null,
 * so that it isn't included within the analytics event.
 * @param external Whether the navigation is accessing a URL that's considered external.
 * Defaults to false. Be aware that this isn't handled based on the value of [domain].
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [ButtonParameters] object, due to the additional parameters
 * needed for Navigation.
 *
 * @see ButtonParameters for the rest of the required parameters for a navigation event.
 */
data class LinkParameters(
    private val domain: String,
    private val external: Boolean = false,
    private val overrides: Mapper? = null,
) : Mapper {
    private val _domain get() = domain.lowercase().take(HUNDRED_CHAR_LIMIT)
    private val _external get() = external.toString().lowercase()

    override fun asMap(): Map<out String, Any?> {
        val bundle =
            mutableMapOf<String, Any?>(
                EXTERNAL to _external,
                LINK_DOMAIN to _domain,
            )

        val commonParameters: Map<out String, Any?> = overrides?.asMap() ?: mapOf()
        bundle.putAll(commonParameters)

        return bundle
    }
}
