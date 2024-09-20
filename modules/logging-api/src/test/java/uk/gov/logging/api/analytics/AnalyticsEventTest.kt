package uk.gov.logging.api.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.api.analytics.parameters.v2.RequiredParameters as RequiredParametersV2
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel3

class AnalyticsEventTest {

    @Test
    fun `V1 differentiate between screen views and navigation views`() {
        assertTrue(
            AnalyticsEvent.screenView(
                RequiredParameters(
                    digitalIdentityJourney = "Test digital identity id",
                    journeyType = "Test journey"
                )
            ).isScreenView()
        ) {
            "The event should be corrected defined as a screen view event!"
        }
    }

    @Test
    fun `V1 verify document type and cri journey is sent correctly for a DL screen view`() {
        val event = AnalyticsEvent.screenView(
            RequiredParameters(
                digitalIdentityJourney = "document checking application",
                journeyType = "driving licence"
            )
        )

        assertEquals(
            "driving licence",
            event.parameters[DOCUMENT_TYPE_JOURNEY_KEY]
        )
    }

    @Test
    fun `V2 differentiate between screen views and navigation views`() {
        assertTrue(
            AnalyticsEvent.screenView(
                RequiredParametersV2(
                    taxonomyLevel2 = TaxonomyLevel2.GOVUK,
                    taxonomyLevel3 = TaxonomyLevel3.UNDEFINED,
                )
            ).isScreenView()
        ) {
            "The event should be corrected defined as a screen view event!"
        }
    }

    @Test
    fun `V2 verify document type and cri journey is sent correctly for a DL screen view`() {
        val event = AnalyticsEvent.screenView(
            RequiredParametersV2(
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
