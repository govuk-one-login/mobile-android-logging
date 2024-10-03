package uk.gov.logging.api.v3dot1.logger

import kotlin.test.BeforeTest
import kotlin.test.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.v3dot1.model.RequiredParameters
import uk.gov.logging.api.v3dot1.model.TrackEvent
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.logging.TEXT
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AnalyticsLoggerExtensionsTest {
    private val required = RequiredParameters(taxonomyLevel2 = TaxonomyLevel2.GOVUK)
    private lateinit var logger: AnalyticsLogger

    @BeforeTest
    fun setUp() {
        logger = mock()
    }

    @Test
    fun `asLegacyEvent coverts v3_1 AnalyticsEvent to legacy AnalyticsEvent`() {
        // Given an AnalyticsEvent from the v3_1 schema
        val domain = "test_domain"
        val text = "test_button_text"
        val event = TrackEvent.Link(
            isExternal = true,
            domain = domain,
            text = text,
            params = required
        )
        // When calling `asLegacyEvent`
        val actual = event.asLegacyEvent()
        // Then log that event the adapter
        assertIs<AnalyticsEvent>(actual)
        assertEquals(expected = text, actual = actual.parameters[TEXT])
        assertEquals(expected = domain, actual = actual.parameters[LINK_DOMAIN])
    }

    @Test
    fun `logEventV3Dot1 extension method converts and calls logEvent`() {
        // Given an AnalyticsEvent from the v3_1 schema
        val domain = "test_domain"
        val text = "test_button_text"
        val event = TrackEvent.Link(
            isExternal = true,
            domain = domain,
            text = text,
            params = required
        )
        // When calling `logEvent`
        logger.logEventV3Dot1(event)
        // Then log that event the adapter
        verify(logger).logEvent(true, event.asLegacyEvent())
    }
}
