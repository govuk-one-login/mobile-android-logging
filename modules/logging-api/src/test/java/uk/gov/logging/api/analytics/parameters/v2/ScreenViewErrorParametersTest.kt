package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.REASON
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.logging.STATUS
import uk.gov.logging.api.analytics.parameters.ParametersTestData
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ScreenViewErrorParametersTest {
    private val exampleName = "Signing out will delete your app data"
    private val exampleId = "30a6b339-75a8-44a2-a79a-e108546419bf"

    @Test
    fun `screen id parameter value is truncated to be 100 characters or less`() {
        // Given ScreenViewErrorParameters with screen id longer than 100 characters
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = ParametersTestData.overOneHundredString,
            endpoint = "www.signin.gov.uk",
            reason = "",
            status = "404"
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
        // Given ScreenViewErrorParameters with name longer than 100 characters
        val parameters = ScreenViewErrorParameters(
            name = ParametersTestData.overOneHundredString,
            id = exampleId,
            endpoint = "www.signin.gov.uk",
            reason = "",
            status = "404"
        )
        val actual = parameters.asMap()[SCREEN_NAME]
        // Then the screen name value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `endpoint parameter value is truncated to be 100 characters or less`() {
        // Given ScreenViewErrorParameters with endpoint longer than 100 characters
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = exampleId,
            endpoint = ParametersTestData.overOneHundredString,
            reason = "",
            status = "404"
        )
        val actual = parameters.asMap()[ENDPOINT]
        // Then the endpoint value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }

    @Test
    fun `reason parameter value is truncated to be 100 characters or less`() {
        // Given ScreenViewErrorParameters with reason longer than 100 characters
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = exampleId,
            endpoint = "www.signin.gov.uk",
            reason = ParametersTestData.overOneHundredString,
            status = "404"
        )
        val actual = parameters.asMap()[REASON]
        // Then the reason value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT),
            actual = actual
        )
    }
    @Test
    fun `status parameter value is truncated to be 100 characters or less, and lowercase`() {
        // Given ScreenViewErrorParameters with status longer than 100 characters
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = exampleId,
            endpoint = "www.signin.gov.uk",
            reason = "",
            status = ParametersTestData.overOneHundredString
        )
        val actual = parameters.asMap()[STATUS]
        // Then the status value is truncated to be 100 characters or less
        assertEquals(
            expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT).lowercase(),
            actual = actual
        )
    }

    @Test
    fun `hash parameter value is md5 hashed`() {
        // Given ScreenViewParameters
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = exampleId,
            endpoint = "www.signin.gov.uk",
            reason = "",
            status = "404"
        )
        val actual = parameters.asMap()[HASH]
        // Then the screen name value is truncated to be 100 characters or less
        assertEquals(
            expected = ("www.signin.gov.uk_404").lowercase().md5(),
            actual = actual
        )
    }

    @Test
    fun `has required keys`() {
        // Given ScreenViewErrorParameters
        val exampleName = "Signing out will delete your app data"
        val exampleId = "30a6b339-75a8-44a2-a79a-e108546419bf"
        val parameters = ScreenViewErrorParameters(
            name = exampleName,
            id = exampleId,
            endpoint = "www.sigin.gov.uk",
            reason = "",
            status = "404"
        )
        // Then ScreenId and ScreenClass, and ScreenName parameters are set
        requiredKeys.forEach { expectedKey ->
            assertContains(parameters.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val requiredKeys = listOf(
            SCREEN_ID, ENDPOINT, REASON, STATUS, HASH, SCREEN_CLASS, SCREEN_NAME
            )
    }
}
