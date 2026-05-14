package uk.gov.logging.api.v3dot1.logger

import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.v3dot1.model.AnalyticsEvent as AEvent

@Deprecated(
    message =
        "Replace with v3 AEvent.asLegacyEvent() extension function" +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "asLegacyEvent()",
            "uk.gov.logging.api.v3.v3dot1.logger.asLegacyEvent",
        ),
    level = DeprecationLevel.WARNING,
)
fun AEvent.asLegacyEvent() = AnalyticsEvent(eventType, asMap().toMap())

@Deprecated(
    message =
        "Replace with v3  AnalyticsLogger.logEventV3Dot1() extension function" +
            "-aim to remove by 12th of July 2026",
    replaceWith =
        ReplaceWith(
            "logEventV3Dot1()",
            "uk.gov.logging.api.v3.v3dot1.logger.logEventV3Dot1",
        ),
    level = DeprecationLevel.WARNING,
)
fun AnalyticsLogger.logEventV3Dot1(event: AEvent) {
    logEvent(true, event.asLegacyEvent())
}
