package uk.gov.logging.api.analytics.parameters.data

enum class Type(val value: String) {
    SimpleSmartAnswer("simple smart answer"),
    PopUp("popup"),
    Link("generic link"),
    DeepLink("deeplink"),
    CallToAction("call to action"),
    SubmitForm("submit form"),
    ActionMenu("action menu"),
    Icon("icon"),
    Toggle("toggle"),
}
