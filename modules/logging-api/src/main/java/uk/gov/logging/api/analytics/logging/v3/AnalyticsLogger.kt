package uk.gov.logging.api.analytics.logging.v3

import uk.gov.logging.api.analytics.AnalyticsEvent

/**
 * Logger for sending analytics events to data sources, such as Firebase.
 */
interface AnalyticsLogger {
    /**
     * Sends the provided [AnalyticsEvent] objects to the data source.
     *
     * @param shouldLogEvent Boolean for holding the necessary validations.
     * @param events The data that's sent.
     */
    suspend fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    )

    /**
     * Enables / disables the data source's acceptance of [AnalyticsEvent] objects.
     *
     * When set to `false`, the implementation won't send data.
     */
    fun setEnabled(isEnabled: Boolean)
}
