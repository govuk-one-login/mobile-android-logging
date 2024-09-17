package uk.gov.logging.api.analytics.parameters.v2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.parameters.ParametersTestData

class LinkParametersTest {

    private val exampleDomain = "www.unit.test"

    @Test
    fun `Domain length is truncated to be 100 characters or less`() {
        assertEquals(
            ParametersTestData.overOneHundredString.lowercase().take(HUNDRED_CHAR_LIMIT),
            LinkParameters(
                external = false,
                text = "cta name",
                domain = ParametersTestData.overOneHundredString
            ).asMap().get(LINK_DOMAIN)
        )
    }

    @ParameterizedTest
    @ValueSource(
        booleans = [true, false]
    )
    fun `Match output map`(isExternal: Boolean) {
        val expectedMap = mutableMapOf<String, Any?>(
            EXTERNAL to "$isExternal",
            LINK_DOMAIN to exampleDomain.lowercase()
        )

        val mapper = LinkParameters(
            domain = exampleDomain,
            text = "cta name",
            external = isExternal
        )

        val actual = mapper.asMap()

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key]
            )
        }
    }
}
