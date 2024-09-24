package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import org.junit.Assert.assertArrayEquals
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import kotlin.test.assertEquals
import kotlin.test.Test
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import kotlin.test.assertContains

class ScreenViewParametersTest {
    private val exampleScreenName = "unit_test"
    private val exampleId = "someid"

    @Test
    fun `screen id parameter value is truncated to be 100 characters or less`() {
        // Given ScreenViewParameters with screen id longer than 100 characters
        val parameters = ScreenViewParameters(
            name = exampleScreenName,
            id = ParametersTestData.overOneHundredString
        )
        val actual = parameters.asMap()[SCREEN_ID]
        // Then the screen id value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `screen name parameter value is truncated to be 100 characters or less`() {
        // Given ScreenViewParameters with screen name longer than 100 characters
        val parameters = ScreenViewParameters(
            name = ParametersTestData.overOneHundredString,
            id = exampleId
        )
        val actual = parameters.asMap()[SCREEN_NAME]
        // Then the screen name value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `Verify map output`() {
        val expectedMap: Map<String, Any?> = mapOf(
            SCREEN_ID to exampleId,
            SCREEN_CLASS to exampleId,
            SCREEN_NAME to exampleScreenName.lowercase(),
        )

        val mapper = ScreenViewParameters(
            name = exampleScreenName,
            id = exampleId
        )

        val actual = mapper.asMap()

        assertArrayEquals(
            expectedMap.keys.toTypedArray(),
            actual.keys.toTypedArray()
        )

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key]
            )
        }
    }

    @Test
    fun `has required keys`() {
        // Given ScreenViewParameters
        val parameters = ScreenViewParameters(
            name = exampleScreenName,
            id = exampleId
        )
        // Then ScreenId and ScreenClass, and ScreenName parameters are set
        requiredKeys.forEach { expectedKey ->
            assertContains(parameters.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val requiredKeys = listOf(SCREEN_ID, SCREEN_CLASS, SCREEN_NAME)
    }
}
