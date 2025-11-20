package uk.gov.logging.api.analytics.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnalyticsPrefs @Inject constructor(
    @ApplicationContext
    val context: Context,
) : IAnalyticsPrefs {
    private val analyticsPreferences get() = context.getSharedPreferences(NAME, MODE)

    override fun deniedPermanently(): Boolean = this isGranting PERMANENT_KEY

    override fun isGranted(): Boolean = this isGranting GRANTED_KEY

    private infix fun isGranting(preferencesKey: String): Boolean =
        analyticsPreferences.getBoolean(preferencesKey, DEFAULT_VALUE)

    override fun updateGranted(granted: Boolean) {
        val editor: SharedPreferences.Editor = analyticsPreferences.edit()
        editor.putBoolean(GRANTED_KEY, granted)
        editor.apply()
    }

    companion object {
        private const val NAME = "ANALYTICS_DENIED"
        private const val MODE = Context.MODE_PRIVATE
        private const val PERMANENT_KEY = "PERMANENT"
        private const val GRANTED_KEY = "GRANTED"
        private const val DEFAULT_VALUE = false
    }
}
