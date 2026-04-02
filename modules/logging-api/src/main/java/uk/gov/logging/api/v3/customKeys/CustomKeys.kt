package uk.gov.logging.api.v3.customKeys

/**
 * Sealed class for setting custom  keys on Firebase Crashlytics V3
 */

sealed class CustomKeys(
    open val key: String,
    open val value: Any,
) {
    data class StringKey(
        override val key: String,
        override val value: String,
    ) : CustomKeys(key, value)

    data class IntKey(
        override val key: String,
        override val value: Int,
    ) : CustomKeys(key, value)

    data class DoubleKey(
        override val key: String,
        override val value: Double,
    ) : CustomKeys(key, value)

    data class BooleanKey(
        override val key: String,
        override val value: Boolean,
    ) : CustomKeys(key, value)
}
