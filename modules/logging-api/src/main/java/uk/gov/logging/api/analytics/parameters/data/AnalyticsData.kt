package uk.gov.logging.api.analytics.parameters.data

sealed interface AnalyticsData<Type> {
    val value: Type

    interface EventType : AnalyticsData<String>
    interface PrimaryPublishingOrganisation : AnalyticsData<String>
    interface Organisation : AnalyticsData<String>
    interface Taxonomy1 : AnalyticsData<String>
    interface Taxonomy2 : AnalyticsData<String>
    interface Taxonomy3 : AnalyticsData<String>
}