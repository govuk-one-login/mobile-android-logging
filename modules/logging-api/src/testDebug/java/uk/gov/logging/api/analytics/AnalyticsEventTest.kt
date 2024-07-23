package uk.gov.logging.api.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.TAXONOMY_LEVEL3
import uk.gov.logging.api.analytics.parameters.RequiredParameters

internal class AnalyticsEventTest {

    @Test
    fun `differentiate between screen views and navigation views`() {
        assertTrue(
            AnalyticsEvent.screenView(
                RequiredParameters(
                    taxonomyLevel2 = "Test digital identity id",
                    taxonomyLevel3 = "Test journey",
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
                taxonomyLevel2 = "document checking application",
                taxonomyLevel3 = "driving licence",
            )
        )

        assertEquals(
            "driving licence",
            event.parameters[TAXONOMY_LEVEL3]
        )
    }
}
