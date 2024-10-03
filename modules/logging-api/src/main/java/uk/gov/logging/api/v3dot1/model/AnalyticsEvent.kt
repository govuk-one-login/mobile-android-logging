package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.parameters.Mapper

/**
 * eventType [String] The event name.
 * parameters [RequiredParameters] shared parameters that are not specific to this
 * event.
 *
 * @see RequiredParameters for the parameters required for any event.
 *
 *  **see also:**
 *  - [GA4 Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 */
sealed interface AnalyticsEvent: Mapper {
    val eventType: String
    val parameters: RequiredParameters
}
