package uk.gov.logging.api.analytics.parameters.v2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TypeCallToAction

internal class PopupParametersTest {

    private val exampleCallToActionText = "button text"

    @Test
    fun `text is truncated to be 100 characters or less`() {
        assertEquals(
            ParametersTestData.overOneHundredString.lowercase().take(HUNDRED_CHAR_LIMIT),
            PopupParameters(
                text = ParametersTestData.overOneHundredString,
                type = TypeCallToAction.CTA,
                overrides = RequiredParameters(
                    taxonomyLevel2 = TaxonomyLevel2.GOVUK
                )
            ).asMap().get(TEXT)
        )
    }

    @Test
    fun `Match output map`() {
        val expectedMap = mutableMapOf<String, Any?>(
            TEXT to exampleCallToActionText.lowercase(),
            TYPE to TypeCallToAction.CTA.value
        )

        val mapper = PopupParameters(
            text = exampleCallToActionText,
            type = TypeCallToAction.CTA,
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
}
