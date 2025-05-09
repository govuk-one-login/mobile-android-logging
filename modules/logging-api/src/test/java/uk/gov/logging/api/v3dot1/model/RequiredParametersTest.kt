package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.logging.LANGUAGE
import uk.gov.logging.api.analytics.logging.ORGANISATION
import uk.gov.logging.api.analytics.logging.PRIMARY_PUBLISHING_ORGANISATION
import uk.gov.logging.api.analytics.logging.SAVED_DOC_TYPE
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL1
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL2
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.data.Organisation.OT1056
import uk.gov.logging.api.analytics.parameters.data.PrimaryPublishingOrganisation.GDS_DI
import uk.gov.logging.api.analytics.parameters.data.SavedDocType
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel1.ONE_LOGIN
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class RequiredParametersTest {

    private val expectedMap = mapOf(
        SAVED_DOC_TYPE to SavedDocType.UNDEFINED.value,
        PRIMARY_PUBLISHING_ORGANISATION to GDS_DI.value,
        ORGANISATION to OT1056.value,
        TAXONOMY_LEVEL1 to ONE_LOGIN.value,
        TAXONOMY_LEVEL2 to TaxonomyLevel2.DOCUMENT_CHECKING_APP.value,
        TAXONOMY_LEVEL3 to TaxonomyLevel3.UNDEFINED.value,
        LANGUAGE to Locale.getDefault().language,
    )

    @Test
    fun `Required Parameters present in the result`() {
        val mapper = RequiredParameters(
            taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
            taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
        )

        assertEquals(
            expectedMap,
            mapper.asMap(),
        )
    }

    companion object {
        val requiredKeys = listOf(
            SAVED_DOC_TYPE,
            PRIMARY_PUBLISHING_ORGANISATION,
            ORGANISATION,
            TAXONOMY_LEVEL1,
            TAXONOMY_LEVEL2,
            TAXONOMY_LEVEL3,
        )
    }
}
