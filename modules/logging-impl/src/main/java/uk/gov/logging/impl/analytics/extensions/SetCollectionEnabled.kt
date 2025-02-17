package uk.gov.logging.impl.analytics.extensions

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

/**
 * Enables / disables both Firebase analytics and Firebase crashlytics.
 */
fun Firebase.setCollectionEnabled(isEnabled: Boolean) {
    this.analytics.setAnalyticsCollectionEnabled(isEnabled)
    this.crashlytics.isCrashlyticsCollectionEnabled = isEnabled
}
