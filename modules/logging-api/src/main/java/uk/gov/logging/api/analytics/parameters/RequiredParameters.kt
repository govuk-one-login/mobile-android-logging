@file:Suppress("ktlint:standard:backing-property-naming", "MaxLineLength")

package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID_VALUE
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY
import uk.gov.logging.api.analytics.logging.LANGUAGE
import java.util.Locale

/**
 * Base class for providing values that's required for all events. These are:
 *
 * - [DIGITAL_IDENTITY_ID], optional with the default value of [DIGITAL_IDENTITY_ID_VALUE].
 * - [DIGITAL_IDENTITY_JOURNEY], with the value of [digitalIdentityJourney].
 * - [LANGUAGE], with the value of the default [Locale.getLanguage]. For english Users, this is
 *   `en`.
 * - [DOCUMENT_TYPE_JOURNEY_KEY], with the value of [DocumentType.journeyType]. This only occurs
 *   when the [DocumentType] isn't [DocumentType.UNDEFINED].
 *
 * **see also:**
 * - [Implementation Guide](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide)
 *
 * @param document The type of Document that the User is scanning as part of their journey.
 */
open class RequiredParameters(
    private val digitalIdentityJourney: String,
    private val journeyType: String,
    private val digitalIdentityId: String = DIGITAL_IDENTITY_ID_VALUE,
) : Mapper {

    @CallSuper
    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf(
            DIGITAL_IDENTITY_ID to digitalIdentityId,
            DIGITAL_IDENTITY_JOURNEY to digitalIdentityJourney,
            LANGUAGE to Locale.getDefault().language,
        )

        if (journeyType.isNotBlank()) {
            bundle[DOCUMENT_TYPE_JOURNEY_KEY] = journeyType
        }

        return bundle
    }
}
