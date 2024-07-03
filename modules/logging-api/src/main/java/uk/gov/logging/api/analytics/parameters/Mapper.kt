package uk.gov.logging.api.analytics.parameters

/**
 * Interface for declaring that a class as the ability to convert itself into a Map of strings.
 */
fun interface Mapper {

    /**
     * Transform the object into a String key-based Map.
     */
    fun asMap(): Map<out String, Any?>
}
