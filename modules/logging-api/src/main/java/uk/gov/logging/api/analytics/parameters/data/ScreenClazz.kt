package uk.gov.logging.api.analytics.parameters.data

@Suppress("MaxLineLength")
/**
 * enum to contain the values required for the `screen_class` parameter as described in v3.1 (below)
 *
 * **see also:**
 * - See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)
 * - [Manually track screen view events](https://firebase.google.com/docs/analytics/screenviews#manually_track_screens)
 *
 * @param value field used to supply the actual string sent to Firebase
 * **/
enum class ScreenClazz(val value: String) {
    MODAL("modal"),
    SPLASH_SCREEN("splashfragment"),
    ERROR_SCREEN("errorscreen"),
    UI_CONTROLLER("uicontroller"),
    WEB_VIEW("webview"),
    UNDEFINED("undefined")
}
