package uk.gov.logging.api

/**
 * Abstraction for declaring a default value to use as a tag when logging messages via a [Logger].
 */
interface LogTagProvider {
    /**
     * The value to group logging messages under. Defaults to the simple name of the implementing
     * class.
     */
    val tag: String
        get() = this::class.java.simpleName
}
