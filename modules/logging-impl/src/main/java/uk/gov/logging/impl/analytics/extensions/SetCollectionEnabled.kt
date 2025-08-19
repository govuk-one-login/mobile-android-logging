package uk.gov.logging.impl.analytics.extensions

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics

/**
 * Enables / disables both Firebase analytics and Firebase crashlytics.
 */
fun Firebase.setCollectionEnabled(isEnabled: Boolean) {
    this.analytics.setAnalyticsCollectionEnabled(isEnabled)
    this.crashlytics.isCrashlyticsCollectionEnabled = isEnabled
}
