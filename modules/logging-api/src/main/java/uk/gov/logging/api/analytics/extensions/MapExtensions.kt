package uk.gov.logging.api.analytics.extensions

import android.os.Bundle
import androidx.core.os.bundleOf

/**
 * Container for extensions relating to the [Map] interface.
 */
object MapExtensions {

    /**
     * Converts the [Map] object into a [Bundle] by internally transforming the key-value pairs
     * into a typed array of [Pair] objects.
     */
    @Suppress("SpreadOperator")
    fun Map<String, Any?>.toBundle(): Bundle {
        return bundleOf(
            *this.map { entry ->
                entry.key to entry.value
            }.toTypedArray(),
        )
    }
}
