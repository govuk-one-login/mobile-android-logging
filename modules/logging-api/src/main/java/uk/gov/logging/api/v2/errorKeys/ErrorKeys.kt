package uk.gov.logging.api.v2.errorKeys

sealed class ErrorKeys(
    open val key: String,
    open val value: Any,
) {
    data class StringKey(
        override val key: String,
        override val value: String,
    ) : ErrorKeys(key, value)

    data class IntKey(
        override val key: String,
        override val value: Int,
    ) : ErrorKeys(key, value)

    data class DoubleKey(
        override val key: String,
        override val value: Double,
    ) : ErrorKeys(key, value)

    data class BooleanKey(
        override val key: String,
        override val value: Boolean,
    ) : ErrorKeys(key, value)
}
