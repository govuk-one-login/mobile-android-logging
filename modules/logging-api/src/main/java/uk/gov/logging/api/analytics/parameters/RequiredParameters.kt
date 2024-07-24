package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import java.util.Locale
import uk.gov.logging.api.analytics.logging.LANGUAGE
import uk.gov.logging.api.analytics.logging.ORGANISATION
import uk.gov.logging.api.analytics.logging.PRIMARY_PUBLISHING_ORGANISATION
import uk.gov.logging.api.analytics.logging.SAVED_DOC_TYPE
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL1
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL2
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.Organisation.OT1056
import uk.gov.logging.api.analytics.parameters.PrimaryPublishingOrganisation.GDS_DI
import uk.gov.logging.api.analytics.parameters.TaxonomyLevel1.ONE_LOGIN
import uk.gov.logging.api.analytics.parameters.TaxonomyLevel3.UNDEFINED

@Suppress("MaxLineLength")
/**
 * Base class for providing values that's required for all events. These are:
 *
 * - [SAVED_DOC_TYPE], with the value of [savedDocType]. Default [UNDEFINED]
 * - [PRIMARY_PUBLISHING_ORGANISATION], with the value of [primaryPublishingOrganisation]. Default[GDS_DI]
 * - [ORGANISATION], with the value of [organisation]. Default [OT1056]
 * - [TAXONOMY_LEVEL1], with the value of [taxonomyLevel1]. Default [ONE_LOGIN]
 * - [TAXONOMY_LEVEL2], with the value of [taxonomyLevel2].
 * - [TAXONOMY_LEVEL3], with the value of [taxonomyLevel3]. Default [UNDEFINED]
 * - [LANGUAGE], with the value of the default [Locale.getLanguage]. For english Users, this is
 *   `en`
 *
 * **see also:**
 *  - [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 *
 */
open class RequiredParameters(
    private val savedDocType: SavedDocType = SavedDocType.UNDEFINED,
    private val primaryPublishingOrganisation: PrimaryPublishingOrganisation = GDS_DI,
    private val organisation: Organisation = OT1056,
    private val taxonomyLevel1: TaxonomyLevel1 = ONE_LOGIN,
    private val taxonomyLevel2: TaxonomyLevel2,
    private val taxonomyLevel3: TaxonomyLevel3 = UNDEFINED
) : Mapper {

    @CallSuper
    override fun asMap(): Map<out String, Any?> = mutableMapOf(
        SAVED_DOC_TYPE to savedDocType.value,
        PRIMARY_PUBLISHING_ORGANISATION to primaryPublishingOrganisation.value,
        ORGANISATION to organisation.value,
        TAXONOMY_LEVEL1 to taxonomyLevel1.value,
        TAXONOMY_LEVEL2 to taxonomyLevel2.value,
        TAXONOMY_LEVEL3 to taxonomyLevel3.value,
        LANGUAGE to Locale.getDefault().language
    )
}
