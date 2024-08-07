package uk.gov.logging.api.analytics.parameters.v2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.analytics.parameters.data.TypeActionMenu

internal class ActionMenuParametersTest {

    private val exampleCallToActionText = "button text"

    @Test
    fun `text is truncated to be 100 characters or less`() {
        assertEquals(
            ParametersTestData.overOneHundredString.lowercase().take(HUNDRED_CHAR_LIMIT),
            ActionMenuParameters(
                text = ParametersTestData.overOneHundredString,
                type = TypeActionMenu.ACTION_MENU,
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
            TYPE to TypeActionMenu.ACTION_MENU.value
        )

        val mapper = ActionMenuParameters(
            text = exampleCallToActionText,
            type = TypeActionMenu.ACTION_MENU,
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
