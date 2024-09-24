package uk.gov.logging.api.analytics.parameters.v2

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
import uk.gov.logging.api.analytics.parameters.data.Type
import kotlin.test.assertContains

class LinkParametersTest {

    private val exampleDomain = "www.unit.test"

    @Test
    fun `text parameter value is truncated to be 100 characters or less`() {
        // Given LinkParameters with text longer than 100 characters
        val parameters = LinkParameters(
            isExternal = false,
            text = ParametersTestData.overOneHundredString,
            domain = exampleDomain
        )
        val actual = parameters.asMap()[TEXT]
        // Then the text value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `domain parameter value is truncated to be 100 characters or less`() {
        // Given LinkParameters with domain longer than 100 characters
        val parameters = LinkParameters(
            isExternal = false,
            text = "cta name",
            domain = ParametersTestData.overOneHundredString
        )
        val actual = parameters.asMap()[LINK_DOMAIN]
        // Then the domain value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
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

        val mapper = LinkParameters(
            domain = exampleDomain,
            text = "cta name",
            isExternal = isExternal
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
        // Given LinkParameters
        val parameters = LinkParameters(
            isExternal = true,
            domain = "domain.test",
            text = "Test Button"
        )
        // Then both Text and Type parameters should be set
        requiredKeys.forEach { expectedKey ->
            assertContains(parameters.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val requiredKeys = listOf(TEXT, TYPE, LINK_DOMAIN, EXTERNAL)
    }
}
