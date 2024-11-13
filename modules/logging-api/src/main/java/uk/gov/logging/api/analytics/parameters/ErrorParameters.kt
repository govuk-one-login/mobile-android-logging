package uk.gov.logging.api.analytics.parameters

import androidx.annotation.CallSuper
import uk.gov.logging.api.analytics.logging.REASON

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
    private val overrides: Mapper? = null,
) : Mapper {
    private val _reason: String get() = reason.lowercase()

    @CallSuper
    override fun asMap(): Map<String, Any?> {
        val bundle = mutableMapOf<String, Any?>(
            REASON to _reason,
        )

        bundle.putAll(overrides?.asMap() ?: mapOf())

        return bundle
    }
}
