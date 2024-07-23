package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import java.util.Locale
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID_VALUE
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL2
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.logging.LANGUAGE
import uk.gov.logging.api.analytics.logging.ORGANISATION
import uk.gov.logging.api.analytics.logging.ORGANISATION_VALUE
import uk.gov.logging.api.analytics.logging.PRIMARY_PUBLISHING_ORGANISATION
import uk.gov.logging.api.analytics.logging.PRIMARY_PUBLISHING_ORGANISATION_VALUE
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL1

@Suppress("MaxLineLength")
/**
 * Base class for providing values that's required for all events. These are:
 *
 * - [DIGITAL_IDENTITY_ID], optional with the default value of [DIGITAL_IDENTITY_ID_VALUE].
 * - [TAXONOMY_LEVEL2], with the value of [taxonomyLevel2].
 * - [LANGUAGE], with the value of the default [Locale.getLanguage]. For english Users, this is
 *   `en`.
 * - [TAXONOMY_LEVEL3], with the value of [DocumentType.journeyType]. This only occurs
 *   when the [DocumentType] isn't [DocumentType.UNDEFINED].
 *
 * **see also:**
 * - [Implementation Guide](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide)
 *
 * @param document The type of Document that the User is scanning as part of their journey.
 */
open class RequiredParameters(
    private val taxonomyLevel1: String,
    private val taxonomyLevel2: String,
    private val taxonomyLevel3: String,
    private val digitalIdentityId: String = DIGITAL_IDENTITY_ID_VALUE,
    private val primaryPublishingOrganisation: String = PRIMARY_PUBLISHING_ORGANISATION_VALUE,
    private val organisation: String = ORGANISATION_VALUE
) : Mapper {

    @CallSuper
    override fun asMap(): Map<out String, Any?> {
        val bundle = mutableMapOf(
            DIGITAL_IDENTITY_ID to digitalIdentityId,
            TAXONOMY_LEVEL1 to taxonomyLevel1,
            TAXONOMY_LEVEL2 to taxonomyLevel2,
            PRIMARY_PUBLISHING_ORGANISATION to primaryPublishingOrganisation,
            ORGANISATION to organisation,
            LANGUAGE to Locale.getDefault().language
        )

        if (taxonomyLevel3.isNotBlank()) {
            bundle[TAXONOMY_LEVEL3] = taxonomyLevel3
        }

        return bundle
    }
}
