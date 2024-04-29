package uk.gov.logging.api.analytics.parameters

import com.google.firebase.analytics.FirebaseAnalytics
import java.util.Locale
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID_VALUE
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY_VALUE
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.LANGUAGE
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE

internal class ButtonParametersTest {

    private val exampleCallToActionText = "button text"
    private val exampleEventName = "unit_test"
    private val exampleScreenName = "screen_name"

    @Test
    fun `CTA length must be under 100 characters`() {
        try {
            ButtonParameters(
                callToActionText = ParametersTestData.overOneHundredString,
                type = exampleEventName,
                name = exampleScreenName
            )
            fail {
                "The callToActionText should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertEquals(
                "The callToActionText parameter length is higher than 100!: " +
                        "${ParametersTestData.overOneHundredString.length}",
                exception.message
            )
        }
    }

    @Test
    fun `Event Name length must be under 40 characters`() {
        try {
            ButtonParameters(
                callToActionText = exampleCallToActionText,
                type = ParametersTestData.fortyTwoString,
                name = exampleScreenName
            )
            fail {
                "The eventName should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertEquals(
                "The eventName parameter length is higher than 40!: " +
                        "${ParametersTestData.fortyTwoString.length}",
                exception.message
            )
        }
    }

    @Test
    fun `Event Name length must be snake cased`() {
        try {
            ButtonParameters(
                callToActionText = exampleCallToActionText,
                type = "CASING-IS-HANDLED-ALREADY",
                name = exampleScreenName
            )
            fail {
                "The eventName should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertTrue(
                exception.message!!.startsWith(
                    "The eventName parameter is not considered lower snake-cased"
                )
            )
        }
    }

    @Test
    fun `Match output map`() {
        val expectedMap = mutableMapOf<String, Any?>(
            EVENT_NAME to exampleEventName.lowercase(),
            TEXT to exampleCallToActionText.lowercase(),
            TYPE to "submit form",
            FirebaseAnalytics.Param.SCREEN_NAME to exampleScreenName
        )

        val mapper = ButtonParameters(
            callToActionText = exampleCallToActionText,
            type = exampleEventName,
            name = exampleScreenName,
            action = "submit form"
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
    fun `example popup event`() {
        val callToActionType = "call to action"
        val popUpEventName = "popup"

        val expectedMap = mutableMapOf<String, Any?>(
            // ButtonParameters properties
            EVENT_NAME to popUpEventName.lowercase(),
            TEXT to exampleCallToActionText.lowercase(),
            TYPE to callToActionType,
            FirebaseAnalytics.Param.SCREEN_NAME to exampleScreenName,

            // RequiredParameters properties
            DIGITAL_IDENTITY_ID to DIGITAL_IDENTITY_ID_VALUE,
            DIGITAL_IDENTITY_JOURNEY to DIGITAL_IDENTITY_JOURNEY_VALUE,
            LANGUAGE to Locale.getDefault().language,
            DOCUMENT_TYPE_JOURNEY_KEY to "driving licence"
        )

        val popupEventParameters = ButtonParameters(
            callToActionText = exampleCallToActionText,
            name = exampleScreenName,
            type = popUpEventName,
            action = callToActionType,
            overrides = RequiredParameters(
                digitalIdentityJourney = "document checking application",
                journeyType = "driving licence"
            )
        )

        val actual = popupEventParameters.asMap()

        assertEquals(expectedMap.keys.size, actual.keys.size) {
            "Screen keys do not match!: Expected: ${expectedMap.keys}; Actual: ${actual.keys}"
        }

        expectedMap.forEach { (key, value) ->
            assertEquals(
                value,
                actual[key]
            )
        }
    }
}
