package uk.gov.logging.api.analytics.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnalyticsPrefs @Inject constructor(
    @ApplicationContext
    val context: Context
) : IAnalyticsPrefs {

    private val analyticsPreferences get() = context.getSharedPreferences(name, mode)

    override fun deniedPermanently(): Boolean = this isGranting permanentKey

    override fun isGranted(): Boolean = this isGranting grantedKey

    private infix fun isGranting(preferencesKey: String): Boolean {
        return analyticsPreferences.getBoolean(preferencesKey, defValue)
    }

    override fun updateGranted(granted: Boolean) {
        val editor: SharedPreferences.Editor = analyticsPreferences.edit()
        editor.putBoolean(grantedKey, granted)
        editor.apply()
    }

    companion object {
        private const val name = "ANALYTICS_DENIED"
        private const val mode = Context.MODE_PRIVATE
        private const val permanentKey = "PERMANENT"
        private const val grantedKey = "GRANTED"
        private const val defValue = false
    }
}
