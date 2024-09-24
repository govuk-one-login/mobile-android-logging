package uk.gov.logging.api.analytics.parameters.v2

import kotlin.test.assertEquals
import kotlin.test.Test
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.Type
import kotlin.test.assertContains

class IconParametersTest {

    private val exampleCallToActionText = "button text"

    @Test
    fun `text parameter value is truncated to be 100 characters or less`() {
        // Given IconParameters with text longer than 100 characters
        val parameters = IconParameters(
            text = ParametersTestData.overOneHundredString,
            overrides = RequiredParameters(
                taxonomyLevel2 = TaxonomyLevel2.GOVUK
            )
        )
        val actual = parameters.asMap()[TEXT]
        // Then the text value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `Match output map`() {
        val expectedMap = mutableMapOf<String, Any?>(
            TEXT to exampleCallToActionText.lowercase(),
            TYPE to Type.Icon.value
        )

        val mapper = IconParameters(
            text = exampleCallToActionText,
            overrides = RequiredParameters(
                taxonomyLevel2 = TaxonomyLevel2.GOVUK
            )
        )

        val actual = mapper.asMap()

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key]
            )
        }
    }

    @Test
    fun `has required keys`() {
        // Given IconParameters
        val parameters = IconParameters(
            text = "Test Button"
        )
        // Then both Text and Type parameters should be set
        requiredKeys.forEach { expectedKey ->
            assertContains(parameters.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val requiredKeys = listOf(TEXT, TYPE)
    }
}
