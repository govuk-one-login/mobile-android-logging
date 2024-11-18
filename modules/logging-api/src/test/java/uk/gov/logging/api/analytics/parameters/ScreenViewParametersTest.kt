package uk.gov.logging.api.analytics.parameters

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS
import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import uk.gov.logging.api.analytics.logging.EVENT_NAME
import uk.gov.logging.api.analytics.logging.TITLE

class ScreenViewParametersTest {

    private val exampleScreenClass = this::class.java.simpleName
    private val exampleScreenName = "UNIT_TEST"
    private val exampleTitle = "This is a unit test"

    @Test
    fun `Screen class must be alphanumeric`() {
        try {
            ScreenViewParameters(
                clazz = "snake_cased_class",
                name = exampleScreenName,
                title = exampleTitle,
            )
            fail {
                "The screenClass should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertTrue(
                exception.message!!.startsWith(
                    "The screenClass parameter is not considered to be lower-cased " +
                        "alphanumeric",
                ),
            )
        }
    }

    @Test
    fun `Screen name must be upper snake_cased`() {
        try {
            ScreenViewParameters(
                clazz = exampleScreenClass,
                name = "casing-is-handled-already-for-you",
                title = exampleTitle,
            )
            fail {
                "The screenName should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertTrue(
                exception.message!!.startsWith(
                    "The screenName parameter is not considered to be upper snake cased",
                ),
            )
        }
    }

    @Test
    fun `Verify map output`() {
        val expectedMap = mutableMapOf<String, Any?>(
            EVENT_NAME to SCREEN_VIEW,
            SCREEN_CLASS to exampleScreenClass.lowercase(),
            SCREEN_NAME to exampleScreenName.uppercase(),
            TITLE to exampleTitle.lowercase(),
        )

        val mapper = ScreenViewParameters(
            clazz = exampleScreenClass,
            name = exampleScreenName,
            title = exampleTitle,
        )

        val actual = mapper.asMap()

        Assertions.assertArrayEquals(
            expectedMap.keys.toTypedArray(),
            actual.keys.toTypedArray(),
        )

        expectedMap.forEach { (key, value) ->
            Assertions.assertEquals(
                value,
                actual[key],
            )
        }
    }
}
