package uk.gov.logging.api.v3.customkey

import uk.gov.logging.api.v3.LogEntry

/**
 * Represents a key-value pair that may be associated with and logged together with an exception log.
 *
 * In particular, it is to support setting Firebase custom keys for Crashlytics.
 *
 * @see [LogEntry.Exception]
 */
sealed class CustomKey(
    open val key: String,
    open val value: Any,
) {
    data class StringKey(
        override val key: String,
        override val value: String,
    ) : CustomKey(key, value)

    data class IntKey(
        override val key: String,
        override val value: Int,
    ) : CustomKey(key, value)

    data class DoubleKey(
        override val key: String,
        override val value: Double,
    ) : CustomKey(key, value)

    data class BooleanKey(
        override val key: String,
        override val value: Boolean,
    ) : CustomKey(key, value)
}
