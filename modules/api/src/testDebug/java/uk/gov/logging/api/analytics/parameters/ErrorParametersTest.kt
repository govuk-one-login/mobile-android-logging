package uk.gov.logging.api.analytics.parameters

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.REASON
import uk.gov.logging.api.analytics.logging.STATUS
import uk.gov.logging.api.analytics.parameters.ParametersTestData.acceptableEndpoint
import uk.gov.logging.api.analytics.parameters.ParametersTestData.overOneHundredString

internal class ErrorParametersTest {

    @Test
    fun `Deprecated method internally generates an ApiErrorParameters object`() {
        val expectedMap = mutableMapOf<String, Any?>(
            REASON to overOneHundredString.lowercase(),
            ENDPOINT to acceptableEndpoint,
            STATUS to Integer.MAX_VALUE.toString(),
            HASH to "${Integer.MAX_VALUE}_$acceptableEndpoint".md5().lowercase()
        )
        val mapper = ErrorParameters(
            endpoint = acceptableEndpoint,
            status = Integer.MAX_VALUE,
            reason = overOneHundredString
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
    fun `Converts into a Map of String keys`() {
        val expectedMap = mutableMapOf<String, Any?>(
            REASON to overOneHundredString.lowercase()
        )
        val mapper = ErrorParameters(
            reason = overOneHundredString
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
}
