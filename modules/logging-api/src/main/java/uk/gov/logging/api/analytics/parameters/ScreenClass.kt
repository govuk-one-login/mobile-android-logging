package uk.gov.logging.api.analytics.parameters

sealed class ScreenClass(val value: String) {
    object MODAL: ScreenClass("modal")
    object SPLASH_FRAGMENT: ScreenClass("splashfragment")
    object ERROR_SCREEN: ScreenClass("errorscreen")
    object WEBVIEW: ScreenClass("webview")
}
