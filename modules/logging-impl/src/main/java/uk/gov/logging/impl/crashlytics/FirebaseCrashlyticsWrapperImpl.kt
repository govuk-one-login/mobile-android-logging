package uk.gov.logging.impl.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.recordException

/**
 * Thin wrapper that delegates to Firebase Crashlytics.
 *
 * This class should contain as little logic as possible.
 */
class FirebaseCrashlyticsWrapperImpl(
    private val crashlytics: FirebaseCrashlytics,
) : FirebaseCrashlyticsWrapper {
    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun recordException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun recordException(
        throwable: Throwable,
        keys: Map<String, String>,
    ) {
        crashlytics.recordException(throwable) {
            keys.forEach { (k, v) -> key(k, v) }
        }
    }
}
