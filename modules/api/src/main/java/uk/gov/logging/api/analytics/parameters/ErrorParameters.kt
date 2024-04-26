package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import io.ktor.http.HttpStatusCode
import uk.gov.logging.api.analytics.logging.REASON

private const val USE_OVERRIDES_INSTEAD_OF_PARAM = "Pass an ApiErrorParameters object as the " +
    "overrides parameter instead of using this constructor. Or use the vararg " +
    "AnalyticsEvent.trackEvent function."

@Suppress("MaxLineLength")
/**
 * Data class to contain the additional values required for creating an Analytics event based on
 * navigating a User to an Error screen.
 *
 * **see also:**
 * - [Error screen tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%3Aflag_on%3A--Error-Screens---example)
 *
 * @param overrides [Mapper] object that's applied after the setup specific to this
 * class. This is most often a [RequiredParameters] object.
 * @param reason Error reason. Must be lower case.
 */
data class ErrorParameters(
    private val reason: String,
    private val overrides: Mapper? = null
) : Mapper {

    /**
     * Data class to contain the additional values required for creating an Analytics event based on
     * navigating a User to an Error screen, with additional parameters for internally creating an
     * [ApiErrorParameters] object.
     *
     * **see also:**
     * - [Error screen tracking](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide#%3Aflag_on%3A--Error-Screens---example)
     *
     * @param endpoint Error API endpoint. Must be lower case.
     * @param status HTTP status code recalled from the backend API when an error occurs. This
     * value is internally converted into a String. This String's maximum length can be 100 characters.
     * Defaults to 500 - Internal Server Error
     * @param overrides [Mapper] object that's applied after the setup specific to this
     * class. This is most often a [RequiredParameters] object.
     * @param reason Error reason. Must be lower case.
     */
    @Deprecated(USE_OVERRIDES_INSTEAD_OF_PARAM)
    constructor(
        reason: String,
        endpoint: String,
        status: Int = HttpStatusCode.InternalServerError.value,
        overrides: Mapper? = null
    ) : this(
        reason = reason,
        overrides = ApiErrorParameters(endpoint, status, overrides)
    )

    private val _reason: String get() = reason.lowercase()

    @CallSuper
    override fun asMap(): Map<String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            REASON to _reason
        )

        bundle.putAll(overrides?.asMap() ?: mapOf())

        return bundle
    }
}
