package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.v3dot1.model.RequiredParametersTest.Companion.requiredKeys
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class FormTest {
    private val required = RequiredParameters(
        taxonomyLevel2 = TaxonomyLevel2.GOVUK
    )

    @Test
    fun `parameter values are truncated to be 100 characters or less`() {
        // Given a TrackEvent.Form with parameter values longer than 100 characters
        val event = TrackEvent.Form(
            text = ParametersTestData.overOneHundredString,
            response = ParametersTestData.overOneHundredString,
            params =  required
        )
        val actualText = event.asMap()[TEXT]
        val actualResponse = event.asMap()[RESPONSE]
        // Then truncate to 100 characters or less the parameters' values
        val expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT)
        assertEquals(expected, actualText)
        assertEquals(expected, actualResponse)
    }

    @Test
    fun `has required keys`() {
        // Given TrackEvent.Form
        val event = TrackEvent.Form(
            text = "Test Button",
            response = ParametersTestData.overOneHundredString,
            params =  required
        )
        // Then Text, Response, and Type parameters are set
        formKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
        requiredKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val formKeys = listOf(TEXT, TYPE, RESPONSE)
    }
}
