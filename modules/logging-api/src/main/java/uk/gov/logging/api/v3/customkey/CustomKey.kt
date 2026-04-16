package uk.gov.logging.api.v3.customkey

/**
 * Sealed class for setting custom  key on Firebase Crashlytics V3
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
