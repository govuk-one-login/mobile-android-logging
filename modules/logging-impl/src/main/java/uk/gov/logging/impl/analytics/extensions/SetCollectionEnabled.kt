package uk.gov.logging.impl.analytics.extensions

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.perf.performance

/**
 * Enables / disables both Firebase analytics, crashlytics, and performance.
 * Crashlytics is always allowed. It is included here to ensure any accidental disabling are undone
 * Needs updating when performance is allowed
 */
fun Firebase.setCollectionEnabled(isEnabled: Boolean) {
    this.analytics.setAnalyticsCollectionEnabled(isEnabled)
    this.crashlytics.isCrashlyticsCollectionEnabled = true
    this.performance.isPerformanceCollectionEnabled = false
}
