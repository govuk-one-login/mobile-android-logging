package uk.gov.logging.api.v3dot1.model

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
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import uk.gov.logging.api.v3dot1.model.RequiredParametersTest.Companion.requiredKeys
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ErrorTest {
    private val exampleName = "Signing out will delete your app data"
    private val exampleId = "30a6b339-75a8-44a2-a79a-e108546419bf"
    private val required = RequiredParameters(
        taxonomyLevel2 = TaxonomyLevel2.GOVUK
    )

    @Test
    fun `parameter values are truncated to be 100 characters or less`() {
        // Given a ViewEvent.Error with parameter values longer than 100 characters
        val parameters = ViewEvent.Error(
            name = ParametersTestData.overOneHundredString,
            id = ParametersTestData.overOneHundredString,
            endpoint = ParametersTestData.overOneHundredString,
            reason = ParametersTestData.overOneHundredString,
            status = ParametersTestData.overOneHundredString,
            params = required
        )
        val actualName = parameters.asMap()[SCREEN_NAME]
        val actualId = parameters.asMap()[SCREEN_ID]
        val actualEndpoint = parameters.asMap()[ENDPOINT]
        val actualReason = parameters.asMap()[REASON]
        val actualStatus = parameters.asMap()[STATUS]
        // Then truncate to 100 characters or less the parameters' values
        val expected = ParametersTestData.overOneHundredString.take(HUNDRED_CHAR_LIMIT)
        assertEquals(expected, actualName)
        assertEquals(expected, actualId)
        assertEquals(expected, actualEndpoint)
        assertEquals(expected, actualReason)
        assertEquals(expected.lowercase(), actualStatus)
    }

    @Test
    fun `hash parameter value is md5 hashed`() {
        // Given ViewEvent.Error
        val parameters = ViewEvent.Error(
            name = exampleName,
            id = exampleId,
            endpoint = "www.signin.gov.uk",
            reason = "",
            status = "404",
            params = required
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
        // Given ViewEvent.Error
        val exampleName = "Signing out will delete your app data"
        val exampleId = "30a6b339-75a8-44a2-a79a-e108546419bf"
        val event = ViewEvent.Error(
            name = exampleName,
            id = exampleId,
            endpoint = "www.sigin.gov.uk",
            reason = "",
            status = "404",
            params = required
        )
        // Then ScreenId and ScreenClass, and ScreenName parameters are set
        errorKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
        requiredKeys.forEach { expectedKey ->
            assertContains(event.asMap().toMap(), expectedKey)
        }
    }

    companion object {
        private val errorKeys = listOf(
            SCREEN_ID, ENDPOINT, REASON, STATUS, HASH, SCREEN_CLASS, SCREEN_NAME
        )
    }
}
