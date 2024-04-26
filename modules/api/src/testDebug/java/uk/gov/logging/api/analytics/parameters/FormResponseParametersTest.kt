package uk.gov.logging.api.analytics.parameters

import com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT

internal class FormResponseParametersTest {

    private val exampleText = "option text"
    private val exampleResponse = arrayOf("option text")
    private val exampleScreenName = "screen_name"

    @Test
    fun `Response text is truncated 100 characters`() {
        FormResponseParameters(
            name = exampleScreenName,
            response = arrayOf(
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789",
                "0123456789"
            ),
            text = exampleText
        ).let {
            assertEquals(
                "0123456789,0123456789,0123456789,0123456789,0123456789,0123456789,0123456789," +
                    "0123456789,0123456789,0",
                it.asMap()[RESPONSE]
            )
        }
    }

    @Test
    fun `FormResponseParameters are correctly built`() {
        FormResponseParameters(
            name = exampleScreenName,
            response = exampleResponse,
            text = exampleText
        ).let {
            assertEquals(exampleResponse[0], it.asMap()[RESPONSE])
            assertEquals(exampleText, it.asMap()[TEXT])
            assertEquals(exampleScreenName, it.asMap()[SCREEN_NAME])
        }
    }
}
