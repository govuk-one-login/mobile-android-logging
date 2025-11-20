package uk.gov.logging.api.analytics.preferences

import android.content.Context
import android.content.SharedPreferences
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AnalyticsPrefsTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var analyticsPrefs: AnalyticsPrefs

    @BeforeEach
    fun setUp() {
        context = mock()
        sharedPreferences = mock()
        editor = mock()

        whenever(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor)

        analyticsPrefs = AnalyticsPrefs(context)
    }

    @Test
    fun `deniedPermanently should return value from shared preferences for permanent key`() {
        whenever(sharedPreferences.getBoolean("PERMANENT", false)).thenReturn(true)
        assertTrue(analyticsPrefs.deniedPermanently())

        whenever(sharedPreferences.getBoolean("PERMANENT", false)).thenReturn(false)
        assertFalse(analyticsPrefs.deniedPermanently())
    }

    @Test
    fun `isGranted should return value from shared preferences for granted key`() {
        whenever(sharedPreferences.getBoolean("GRANTED", false)).thenReturn(true)
        assertTrue(analyticsPrefs.isGranted())

        whenever(sharedPreferences.getBoolean("GRANTED", false)).thenReturn(false)
        assertFalse(analyticsPrefs.isGranted())
    }

    @Test
    fun `updateGranted should update granted value in shared preferences`() {
        analyticsPrefs.updateGranted(true)
        verify(editor).putBoolean("GRANTED", true)

        analyticsPrefs.updateGranted(false)
        verify(editor).putBoolean("GRANTED", false)
        verify(editor, times(2)).apply()
    }
}
