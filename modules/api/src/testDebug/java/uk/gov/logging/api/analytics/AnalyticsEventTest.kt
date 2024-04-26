package uk.gov.logging.api.analytics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.documentchecking.repositories.api.webhandover.documenttype.DocumentType.DRIVING_LICENCE
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY

internal class AnalyticsEventTest {

    @Test
    fun `differentiate between screen views and navigation views`() {
        assertTrue(
            AnalyticsEvent.screenView(
                screenClass = this::class.java.simpleName,
                screenName = "screenOne"
            ).isScreenView()
        ) {
            "The event should be corrected defined as a screen view event!"
        }
        assertFalse(
            AnalyticsEvent.internalButton(
                section = "unit_test_section",
                text = "unit test",
                url = "unittest://analyticsevent.test"
            ).isScreenView()
        ) {
            "The navigation event should not be defined as a screen view!"
        }
    }

    @Test
    fun `verify document type and cri journey is sent correctly for a DL screen view`() {
        val event = AnalyticsEvent.screenView(
            screenClass = "MyScreenClass",
            screenName = "MyScreenName",
            documentType = DRIVING_LICENCE
        )

        assertEquals(
            "driving licence",
            event.parameters[DOCUMENT_TYPE_JOURNEY_KEY]
        )
    }

    @Test
    fun `verify document type and cri journey is sent correctly for DL screen view error`() {
        val event = AnalyticsEvent.screenViewError(
            screenClass = "MyScreenClass",
            screenName = "MyScreenName",
            documentType = DRIVING_LICENCE,
            endpoint = "/an/endpoint",
            reason = "test"
        )

        assertEquals(
            "driving licence",
            event.parameters[DOCUMENT_TYPE_JOURNEY_KEY]
        )
    }
}
