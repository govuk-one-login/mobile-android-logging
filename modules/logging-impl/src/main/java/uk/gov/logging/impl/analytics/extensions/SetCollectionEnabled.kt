package uk.gov.logging.impl.analytics.extensions

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.perf.performance

/**
 * Enables / disables both Firebase analytics, crashlytics, and performance.
 * Needs updating when performance is allowed
 */
fun Firebase.setCollectionEnabled(isEnabled: Boolean) {
    this.analytics.setAnalyticsCollectionEnabled(isEnabled)
    this.crashlytics.isCrashlyticsCollectionEnabled = isEnabled
    this.performance.isPerformanceCollectionEnabled = false
}
