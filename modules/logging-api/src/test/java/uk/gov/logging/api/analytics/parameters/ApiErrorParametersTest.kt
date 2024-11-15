package uk.gov.logging.api.analytics.parameters

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.STATUS
import uk.gov.logging.api.analytics.parameters.ParametersTestData.acceptableEndpoint
import uk.gov.logging.api.analytics.parameters.ParametersTestData.fortyTwoString

class ApiErrorParametersTest {

    @Test
    fun `Endpoint length must be under 40 characters`() {
        try {
            ApiErrorParameters(
                endpoint = fortyTwoString,
                status = 200
            )
            fail {
                "The endpoint should have thrown an exception!"
            }
        } catch (exception: IllegalArgumentException) {
            assertEquals(
                "The endpoint parameter is higher than 40!: ${fortyTwoString.length}",
                exception.message
            )
        }
    }

    @Test
    fun `Max integer value has no issue with expected output`() {
        val expectedMap = mutableMapOf<String, Any?>(
            ENDPOINT to acceptableEndpoint,
            STATUS to Integer.MAX_VALUE.toString(),
            HASH to "${Integer.MAX_VALUE}_$acceptableEndpoint".md5().lowercase()
        )
        val mapper = ApiErrorParameters(
            endpoint = acceptableEndpoint,
            status = Integer.MAX_VALUE
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
