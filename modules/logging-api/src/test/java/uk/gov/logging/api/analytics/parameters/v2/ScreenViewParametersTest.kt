package uk.gov.logging.api.analytics.parameters.v2

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.SCREEN_ID
import uk.gov.logging.api.analytics.parameters.data.ScreenClazz

class ScreenViewParametersTest {

    private val exampleScreenClass = ScreenClazz.UNDEFINED
    private val exampleScreenName = "unit_test"
    private val exampleId = "someid"

    @Test
    fun `Screen class must be alphanumeric`() {
        try {
            ScreenViewParameters(
                name = exampleScreenName,
                clazz = "snake_cased_class",
                id = exampleId
            )
            fail {
                "The screenClass should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertTrue(
                exception.message!!.startsWith(
                    "The screenClass parameter is not considered to be lower-cased " +
                        "alphanumeric"
                )
            )
        }
    }

    @Test
    fun `Screen name must be lower snake_cased`() {
        try {
            ScreenViewParameters(
                name = "CASING-IS-HANDLED-ALREADY-FOR-YOU",
                clazz = exampleScreenClass.value,
                id = exampleId
            )
            fail {
                "The screenName should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertTrue(
                exception.message!!.startsWith(
                    "The screenName parameter is not considered to be lower snake cased"
                )
            )
        }
    }

    @Test
    fun `Verify map output`() {
        val expectedMap = mutableMapOf<String, Any?>(
            EVENT_NAME to SCREEN_VIEW,
            SCREEN_ID to exampleId,
            SCREEN_CLASS to exampleScreenClass.value.lowercase(),
            SCREEN_NAME to exampleScreenName.lowercase(),
        )

        val mapper = ScreenViewParameters(
            name = exampleScreenName,
            clazz = exampleScreenClass.value,
            id = exampleId
        )

        val actual = mapper.asMap()

        Assertions.assertArrayEquals(
            expectedMap.keys.toTypedArray(),
            actual.keys.toTypedArray()
        )

        expectedMap.forEach { (key, value) ->
            Assertions.assertEquals(
                value,
                actual[key]
            )
        }
    }
}
