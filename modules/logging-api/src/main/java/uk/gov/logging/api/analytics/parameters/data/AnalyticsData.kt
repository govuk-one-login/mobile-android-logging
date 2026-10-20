package uk.gov.logging.api.analytics.parameters.data

sealed interface AnalyticsData {
    val value: String

    interface EventType : AnalyticsData
    interface PrimaryPublishingOrganisation : AnalyticsData
    interface Organisation : AnalyticsData
    interface Taxonomy1 : AnalyticsData
    interface Taxonomy2 : AnalyticsData
    interface Taxonomy3 : AnalyticsData
}
