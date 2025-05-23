package uk.gov.logging.api.analytics.extensions

import android.content.Context
import android.content.res.Configuration
import androidx.test.platform.app.InstrumentationRegistry
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.logging.api.test.R as T

class ContextExtensionsTest {
    @Test
    fun getEnglishStringOverridesLanguage() {
        // Given a Welsh context
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        val welshContext = context.createConfigurationContext(context.welshConfiguration)
        // When forcing a string resource to be english
        val actual = welshContext.getEnglishString(T.string.translatable_string)
        // Then return the english version
        assertEquals("English", actual)
    }

    @Test
    fun getEnglishStringOverridesLanguageTemporarily() {
        // Given a Welsh context
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        val welshContext = context.createConfigurationContext(context.welshConfiguration)
        // When forcing a string resource to be english
        val englishString = welshContext.getEnglishString(T.string.translatable_string)
        val actual = welshContext.getString(T.string.translatable_string)
        // Then do not affect other string resources
        assertEquals("English", englishString)
        assertEquals("Welsh", actual)
    }

    @Test
    fun getEnglishQuantityStringOverridesLanguage() {
        // Given a Welsh context
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        val welshContext = context.createConfigurationContext(context.welshConfiguration)
        // When forcing a plural resource to be english
        val actual = welshContext.getEnglishQuantityString(T.plurals.translatable_plural, 0)
        // Then return the english version
        assertEquals("English Other", actual)
    }

    @Test
    fun getEnglishQuantityStringOverridesLanguageTemporarily() {
        // Given a Welsh context
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        val welshContext = context.createConfigurationContext(context.welshConfiguration)
        // When forcing a plural resource to be english
        val englishString = welshContext.getEnglishQuantityString(T.plurals.translatable_plural, 0)
        val actual = welshContext.resources.getQuantityString(T.plurals.translatable_plural, 0)
        // Then do not affect other string resources
        assertEquals("English Other", englishString)
        assertEquals("Welsh Zero", actual)
    }

    private val Context.welshConfiguration: Configuration
        get() = Configuration(resources.configuration).apply { setLocale(Locale("cy")) }
}
