package uk.gov.logging.api.analytics.preferences

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StubAnalyticsPrefsTest {
    @Test
    fun check_granted_value() {
        val prefs = StubAnalyticsPrefs()

        assertFalse(prefs.deniedPermanently())
        assertFalse(prefs.isGranted())
        prefs.updateGranted(true)
        assertTrue(prefs.isGranted())
    }

    @Test
    fun check_permanent_denied() {
        val prefs = StubAnalyticsPrefs(stubPermanentValue = true)

        assertTrue(prefs.deniedPermanently())
    }
}
