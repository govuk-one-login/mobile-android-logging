@file:Suppress("ktlint:standard:backing-property-naming", "MaxLineLength")

package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LANGUAGE
import uk.gov.logging.api.analytics.logging.ORGANISATION
import uk.gov.logging.api.analytics.logging.PRIMARY_PUBLISHING_ORGANISATION
import uk.gov.logging.api.analytics.logging.SAVED_DOC_TYPE
import uk.gov.logging.api.analytics.logging.SAVED_DOC_TYPE_UNDEFINED
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL1
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL2
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.Mapper
import uk.gov.logging.api.analytics.parameters.data.Organisation
import uk.gov.logging.api.analytics.parameters.data.Organisation.OT1056
import uk.gov.logging.api.analytics.parameters.data.PrimaryPublishingOrganisation
import uk.gov.logging.api.analytics.parameters.data.PrimaryPublishingOrganisation.GDS_DI
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel1
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel1.ONE_LOGIN
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3.UNDEFINED
import java.util.Locale

/**
 * Base class for providing values that's required for all events. These are:
 *
 * - [SAVED_DOC_TYPE], with the value of [String]. Default [UNDEFINED]
 * - [PRIMARY_PUBLISHING_ORGANISATION], with the value of [primaryPublishingOrganisation]. Default[GDS_DI]
 * - [ORGANISATION], with the value of [organisation]. Default [OT1056]
 * - [TAXONOMY_LEVEL1], with the value of [taxonomyLevel1]. Default [ONE_LOGIN]
 * - [TAXONOMY_LEVEL2], with the value of [taxonomyLevel2].
 * - [TAXONOMY_LEVEL3], with the value of [taxonomyLevel3]. Default [UNDEFINED]
 *
 * **see also:**
 *  - [GA4 Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 */
open class RequiredParameters(
    private val savedDocType: String = SAVED_DOC_TYPE_UNDEFINED,
    private val primaryPublishingOrganisation: PrimaryPublishingOrganisation = GDS_DI,
    private val organisation: Organisation = OT1056,
    private val taxonomyLevel1: TaxonomyLevel1 = ONE_LOGIN,
    private val taxonomyLevel2: TaxonomyLevel2,
    private val taxonomyLevel3: TaxonomyLevel3 = UNDEFINED,
) : Mapper {
    private val _savedDocType get() = savedDocType.take(HUNDRED_CHAR_LIMIT)

    override fun asMap(): Map<out String, Any?> = mapOf(
        SAVED_DOC_TYPE to _savedDocType,
        PRIMARY_PUBLISHING_ORGANISATION to primaryPublishingOrganisation.value,
        ORGANISATION to organisation.value,
        TAXONOMY_LEVEL1 to taxonomyLevel1.value,
        TAXONOMY_LEVEL2 to taxonomyLevel2.value,
        TAXONOMY_LEVEL3 to taxonomyLevel3.value,
        LANGUAGE to Locale.getDefault().language,
    )
}
