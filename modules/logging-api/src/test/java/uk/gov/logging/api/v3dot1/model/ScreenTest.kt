package uk.gov.logging.api.v3dot1.model

import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.v3dot1.model.RequiredParametersTest.Companion.requiredKeys
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ScreenTest {
    private val exampleScreenName = "unit_test"
    private val exampleId = "someid"
    private val required = RequiredParameters(
        taxonomyLevel2 = TaxonomyLevel2.GOVUK,
    )

    @Test
    fun `parameter values are truncated to be 100 characters or less`() {
        // Given a ViewEvent.Screen with parameter values longer than 100 characters
        val parameters = ViewEvent.Screen(
            name = ParametersTestData.overOneHundredString,
            id = ParametersTestData.overOneHundredString,
            params = required,
        )
        val actualName = parameters.asMap()[SCREEN_NAME]
        val actualId = parameters.asMap()[SCREEN_ID]
        // Then truncate to 100 characters or less the parameters' values
        val expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT)
        assertEquals(expected, actualName)
        assertEquals(expected, actualId)
    }

    @Test
    fun `Verify map output`() {
        val expectedMap: Map<String, Any?> = mapOf(
            SCREEN_ID to exampleId,
            SCREEN_CLASS to exampleId,
            SCREEN_NAME to exampleScreenName.lowercase(),
        )

        val mapper = ViewEvent.Screen(
            name = exampleScreenName,
            id = exampleId,
            params = required,
        )

        val actual = mapper.asMap()

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key],
            )
        }
    }

    @Test
    fun `has required keys`() {
        // Given ViewEvent.Screen
        val event = ViewEvent.Screen(
            name = exampleScreenName,
            id = exampleId,
            params = required,
        )
        // Then ScreenId and ScreenClass, and ScreenName parameters are set
        screenKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
        requiredKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val screenKeys = listOf(SCREEN_ID, SCREEN_CLASS, SCREEN_NAME)
    }
}
