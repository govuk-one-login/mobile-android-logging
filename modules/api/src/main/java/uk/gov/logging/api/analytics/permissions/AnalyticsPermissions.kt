package uk.gov.logging.api.analytics.permissions

import androidx.annotation.Keep
import uk.gov.documentchecking.features.api.permissions.Permission
import uk.gov.logging.api.BuildConfig

/**
 * Permissions used within code that directly relate to Analytics throughout the project.
 *
 * The AndroidManifest.xml contains the official declaration of the permissions defined.
 */
@Keep
sealed class AnalyticsPermissions(private val id: String) : Permission {

    override fun toString(): String = id

    /**
     * String representation of the custom analytics permission used in the app.
     */
    @Keep
    object GoogleAnalytics : AnalyticsPermissions(
        "${BuildConfig.NAME_SPACE}.analytics.permission.GOOGLE"
    )
}
