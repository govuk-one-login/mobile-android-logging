package uk.gov.logging.api.analytics.parameters.data

enum class TaxonomyLevel1(override val value: String) : AnalyticsData.Taxonomy1 {
    ID_CHECK("id check app"),
    ONE_LOGIN("one login mobile application"),
}
