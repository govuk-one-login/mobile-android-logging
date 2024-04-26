package uk.gov.logging.impl.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import uk.gov.documentchecking.features.api.permissions.PermissionConditions
import uk.gov.logging.api.Logger
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.logging.api.analytics.permissions.AnalyticsPermissions
import uk.gov.logging.impl.analytics.extensions.setCollectionEnabled

/**
 * Analytics Logging implementation that utilises the static
 * [com.google.firebase.analytics.FirebaseAnalytics] object for logging [AnalyticsEvent]s.
 */
class FirebaseAnalyticsLogger @Inject constructor(
    private val analytics: FirebaseAnalytics,
    private val logger: Logger
) : AnalyticsLogger {

    override fun logEvent(
        conditions: PermissionConditions,
        vararg events: AnalyticsEvent
    ) {
        if (shouldLogEvent(conditions)) {
            events.forEach(::internalLogEvent)
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        Firebase.setCollectionEnabled(isEnabled)
    }

    /**
     * Thread sleeps for 1 millisecond before and after each analytics event to enforce
     * that separate events don't get sent at exactly the same time stamp, which would
     * cause Firebase to display them under a single event.
     */
    private fun internalLogEvent(event: AnalyticsEvent) {
        Thread.sleep(1)
        val bundledParameters = event.toBundle()
        analytics.logEvent(event.eventType, bundledParameters)
        debugLog(
            tag = this::class.java.simpleName,
            msg = "Firebase event sent with: $bundledParameters"
        )
        Thread.sleep(1)
    }

    private fun shouldLogEvent(
        permissionConditions: PermissionConditions
    ): Boolean = permissionConditions.hasGrantedPermission(
        AnalyticsPermissions.GoogleAnalytics
    ).also {
        debugLog(
            tag = this::class.java.simpleName,
            msg = "Should log event: $it"
        )
    }

    override fun debugLog(tag: String, msg: String) = logger.debug(tag, msg)
}
