package uk.gov.logging.api.analytics.preferences

/*
    Abstraction for fetching analytics values from the shared preferences
 */
interface IAnalyticsPrefs {
    /**
     * @return [Boolean] is the analytics permission permanently denied
     */
    fun deniedPermanently(): Boolean

    /**
     * @return [Boolean] is the analytics permission granted
     */
    fun isGranted(): Boolean

    /**
     * Set the analytics permission state
     */
    fun updateGranted(granted: Boolean)
}
