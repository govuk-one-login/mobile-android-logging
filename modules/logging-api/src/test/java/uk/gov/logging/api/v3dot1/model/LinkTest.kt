package uk.gov.logging.api.v3dot1.model

import kotlin.test.assertEquals
import kotlin.test.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.Type
import uk.gov.logging.api.v3dot1.model.RequiredParametersTest.Companion.requiredKeys
import kotlin.test.assertContains

class LinkTest {
    private val exampleDomain = "www.unit.test"
    private val required = RequiredParameters(taxonomyLevel2 = TaxonomyLevel2.GOVUK)

    @Test
    fun `parameter values are truncated to be 100 characters or less`() {
        // Given a TrackEvent.Link with parameter values longer than 100 characters
        val event = TrackEvent.Link(
            isExternal = false,
            text = ParametersTestData.overOneHundredString,
            domain = ParametersTestData.overOneHundredString,
            params = required
        )
        val actualText = event.asMap()[TEXT]
        val actualDomain = event.asMap()[LINK_DOMAIN]
        // Then truncate to 100 characters or less the parameters' values
        val expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT)
        assertEquals(expected, actualText)
        assertEquals(expected, actualDomain)
    }

    @ParameterizedTest
    @ValueSource(
        booleans = [true, false]
    )
    fun `Match output map`(isExternal: Boolean) {
        val expectedMap = mutableMapOf<String, Any?>(
            EXTERNAL to "$isExternal",
            LINK_DOMAIN to exampleDomain.lowercase(),
            TYPE to Type.Link.value
        )

        val event = TrackEvent.Link(
            domain = exampleDomain,
            text = "cta name",
            isExternal = isExternal,
            params = required
        )

        val actual = event.asMap()

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key]
            )
        }
    }

    @Test
    fun `has required keys`() {
        // Given TrackEvent.Link
        val event = TrackEvent.Link(
            isExternal = true,
            domain = "domain.test",
            text = "Test Button",
            params = required
        )
        // Then both Text and Type parameters should be set
        linkKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
        requiredKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val linkKeys = listOf(TEXT, TYPE, LINK_DOMAIN, EXTERNAL)
    }
}
