package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.Type
import uk.gov.logging.api.v3dot1.model.RequiredParametersTest.Companion.requiredKeys
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class IconTest {
    private val exampleText = "button text"
    private val required = RequiredParameters(taxonomyLevel2 = TaxonomyLevel2.GOVUK)

    @Test
    fun `parameter values are truncated to be 100 characters or less`() {
        // Given a TrackEvent.Icon with parameter values longer than 100 characters
        val parameters = TrackEvent.Icon(
            text = ParametersTestData.overOneHundredString,
            params = required,
        )
        val actual = parameters.asMap()[TEXT]
        // Then truncate to 100 characters or less the parameters' values
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual,
        )
    }

    @Test
    fun `Match output map`() {
        val expectedMap = mutableMapOf<String, Any?>(
            TEXT to exampleText.lowercase(),
            TYPE to Type.Icon.value,
        )

        val event = TrackEvent.Icon(
            text = exampleText,
            params = required,
        )

        val actual = event.asMap()

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key],
            )
        }
    }

    @Test
    fun `has required keys`() {
        // Given TrackEvent.Icon
        val event = TrackEvent.Icon(
            text = exampleText,
            params = required,
        )
        // Then both Text and Type parameters should be set
        iconKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
        requiredKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val iconKeys = listOf(TEXT, TYPE)
    }
}
