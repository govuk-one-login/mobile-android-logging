@file:Suppress("ktlint:standard:backing-property-naming", "MaxLineLength")

package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import uk.gov.logging.api.analytics.extensions.md5
import uk.gov.logging.api.analytics.logging.ENDPOINT
import uk.gov.logging.api.analytics.logging.FORTY_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.HASH
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LOWER_ALPHANUMERIC_HUNDRED_LIMIT
import uk.gov.logging.api.analytics.logging.STATUS

/**
 * Data class to contain the additional values required for creating an Analytics event based on
 * navigating a User to an Error screen.
 *
 * **see also:**
 * - [Error screen tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%3Aflag_on%3A--Error-Screens---example)
 *
 * @param endpoint Error API endpoint. Must be lower case.
 * @property hash An MD5 Hash of both the [status] and [endpoint], concatenated with an
 * underscore. `500_someapi` is an example of this. This property has a default value which matches
 * the expected formatting.
 * @param status HTTP status code recalled from the backend API when an error occurs. This
 * value is internally converted into a String. This String's maximum length can be 100 characters.
 * Defaults to 500 - Internal Server Error
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 */
data class ApiErrorParameters(
    private val endpoint: String,
    private val status: Int = DEFAULT_HTTP_STATUS,
    private val overrides: Mapper? = null,
) : Mapper {

    private val _endpoint: String get() = endpoint.lowercase()
    private val _httpStatus: String get() = status.toString().lowercase()
    private val hash: String = "${_httpStatus}_$_endpoint".md5().lowercase()

    init {
        validateEndpoint()
        validateHttpStatus()
        validateHash()
    }

    private fun validateHash() {
        require(hash.length <= HUNDRED_CHAR_LIMIT) {
            "The hash parameter length is higher than $HUNDRED_CHAR_LIMIT!: ${hash.length}"
        }
        require(LOWER_ALPHANUMERIC_HUNDRED_LIMIT.matcher(hash).matches()) {
            "The hash parameter is not considered alphanumeric ( " +
                "${LOWER_ALPHANUMERIC_HUNDRED_LIMIT.pattern()} )!: $hash"
        }
    }

    private fun validateEndpoint() {
        require(_endpoint.length <= FORTY_CHAR_LIMIT) {
            "The endpoint parameter is higher than $FORTY_CHAR_LIMIT!: ${_endpoint.length}"
        }
    }

    private fun validateHttpStatus() {
        require(LOWER_ALPHANUMERIC_HUNDRED_LIMIT.matcher(_httpStatus).matches()) {
            "The httpStatus parameter is not considered alphanumeric ( " +
                "${LOWER_ALPHANUMERIC_HUNDRED_LIMIT.pattern()} )!: $_httpStatus"
        }
    }

    @CallSuper
    override fun asMap(): Map<String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            ENDPOINT to _endpoint,
            STATUS to _httpStatus,
            HASH to hash,
        )

        bundle.putAll(overrides?.asMap() ?: mapOf())

        return bundle
    }

    companion object {
        private const val DEFAULT_HTTP_STATUS = 500
    }
}
