package uk.gov.logging.api.v3.v3dot1.logger

import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.v3.AnalyticsLogger
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent as AEvent

/**
 * Converts an [AEvent] to a legacy [AnalyticsEvent].
 */
fun AEvent.asLegacyEvent() = AnalyticsEvent(eventType, asMap().toMap())

/**
 * Logs an [AnalyticsEvent] using the v3.1 API.
 *
 * Consumers are responsible for providing the correct event type and coroutine
 * scope when calling this function.
 */

fun AnalyticsLogger.logEventV3Dot1(event: AEvent) {
    logEvent(true, event.asLegacyEvent())
}
