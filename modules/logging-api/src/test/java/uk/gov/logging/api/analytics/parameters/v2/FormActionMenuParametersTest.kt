package uk.gov.logging.api.analytics.parameters.v2

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class FormActionMenuParametersTest {

    @Test
    fun `text parameter value is truncated to be 100 characters or less`() {
        // Given FormActionMenuParameters with text longer than 100 characters
        val parameters = FormActionMenuParameters(
            text = ParametersTestData.overOneHundredString,
            response = "yes"
        )
        val actual = parameters.asMap()[TEXT]
        // Then the text value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `response parameter value is truncated to be 100 characters or less`() {
        // Given FormActionMenuParameters with response longer than 100 characters
        val parameters = FormActionMenuParameters(
            text = "Test Button",
            response = ParametersTestData.overOneHundredString
        )
        val actual = parameters.asMap()[RESPONSE]
        // Then the response value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `has required keys`() {
        // Given FormActionMenuParameters
        val parameters = FormActionMenuParameters(
            text = "Test Button",
            response = ParametersTestData.overOneHundredString
        )
        // Then Text, Response, and Type parameters are set
        requiredKeys.forEach { expectedKey ->
            assertContains(parameters.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val requiredKeys = listOf(TEXT, TYPE, RESPONSE)
    }
}
