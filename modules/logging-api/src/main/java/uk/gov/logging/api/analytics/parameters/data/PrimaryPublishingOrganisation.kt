package uk.gov.logging.api.analytics.parameters.data

enum class PrimaryPublishingOrganisation(override val value: String) :
    AnalyticsData.PrimaryPublishingOrganisation {
    GDS_DI("government digital service - digital identity"),
}
