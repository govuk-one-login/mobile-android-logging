package uk.gov.logging.testdouble.analytics

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import javax.inject.Inject

class ToastingAnalyticsLogger @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : AnalyticsLogger {

    private var enabled: Boolean = false

    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    ) {
        if (enabled) {
            events.forEach { event ->
                showToast(event.eventType)
            }
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        enabled = isEnabled

        val text = if (isEnabled) {
            "Analytics logger enabled"
        } else {
            "Analytics logger disabled"
        }

        showToast(text)
    }

    private fun showToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT,
        ).show()
    }
}
