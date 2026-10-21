package uk.gov.logging.api.analytics.parameters.data

enum class TaxonomyLevel2(override val value: String) : AnalyticsData.Taxonomy2 {
    ACCOUNT("account"),
    APP_SYSTEM("app system"),
    DOCUMENT_CHECKING_APP("document checking application"),
    GOVUK("govuk"),
    HOME("home"),
    LOCAL_AUTH_MANAGER("local auth manager"),
    LOGIN("login"),
    ONBOARDING("onboarding"),
    SETTINGS("settings"),
    WALLET("wallet"),
}
