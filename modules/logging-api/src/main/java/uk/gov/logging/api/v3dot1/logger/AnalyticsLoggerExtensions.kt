package uk.gov.logging.api.v3dot1.logger

import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent as AEvent

fun AEvent.asLegacyEvent() = AnalyticsEvent(eventType, asMap().toMap())

fun AnalyticsLogger.logEventV3Dot1(event: AEvent) {
    logEvent(true, event.asLegacyEvent())
}
