package uk.gov.logging.api.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.api.analytics.parameters.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.TaxonomyLevel3

internal class AnalyticsEventTest {

    @Test
    fun `differentiate between screen views and navigation views`() {
        assertTrue(
            AnalyticsEvent.screenView(
                RequiredParameters(
                    taxonomyLevel2 = TaxonomyLevel2.GOVUK,
                    taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
                )
            ).isScreenView()
        ) {
            "The event should be corrected defined as a screen view event!"
        }
    }

    @Test
    fun `verify document type and cri journey is sent correctly for a DL screen view`() {
        val event = AnalyticsEvent.screenView(
            RequiredParameters(
                taxonomyLevel2 = TaxonomyLevel2.DOCUMENT_CHECKING_APP,
                taxonomyLevel3 = TaxonomyLevel3.DRIVING_LICENCE_CRI,
            )
        )

        assertEquals(
            TaxonomyLevel3.DRIVING_LICENCE_CRI.value,
            event.parameters[TAXONOMY_LEVEL3]
        )
    }
}
