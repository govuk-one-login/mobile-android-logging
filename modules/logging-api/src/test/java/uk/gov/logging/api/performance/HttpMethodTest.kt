package uk.gov.logging.api.performance

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class HttpMethodTest {
    @ParameterizedTest
    @MethodSource("enumsToNames")
    fun `HttpMethod matches names`(
        enum: HttpMethod,
        nameValue: String,
    ) {
        assertEquals(expected = nameValue, actual = enum.name)
    }

    companion object {
        @JvmStatic
        fun enumsToNames(): Stream<Arguments> =
            Stream.of(
                Arguments.of(HttpMethod.GET, "GET"),
                Arguments.of(HttpMethod.POST, "POST"),
                Arguments.of(HttpMethod.PUT, "PUT"),
                Arguments.of(HttpMethod.DELETE, "DELETE"),
                Arguments.of(HttpMethod.PATCH, "PATCH"),
                Arguments.of(HttpMethod.HEAD, "HEAD"),
                Arguments.of(HttpMethod.OPTIONS, "OPTIONS"),
                Arguments.of(HttpMethod.TRACE, "TRACE"),
                Arguments.of(HttpMethod.CONNECT, "CONNECT"),
            )
    }
}
