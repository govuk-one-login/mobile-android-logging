package uk.gov.logging.api.analytics.parameters.data

enum class Type(override val value: String) : AnalyticsData.EventType {
    ActionMenu("action menu"),
    CallToAction("call to action"),
    DeepLink("deeplink"),
    Icon("icon"),
    Link("generic link"),
    PopUp("popup"),
    SimpleSmartAnswer("simple smart answer"),
    SubmitForm("submit form"),
    Toggle("toggle"),
}
