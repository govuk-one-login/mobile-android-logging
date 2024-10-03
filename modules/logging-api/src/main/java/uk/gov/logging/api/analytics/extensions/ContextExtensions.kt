package uk.gov.logging.api.analytics.extensions

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import java.util.Locale

@Suppress("AppBundleLocaleChanges") // Needs to be dealt with in App
private val Context.englishConfiguration: Configuration
    get() = Configuration(resources.configuration).apply { setLocale(Locale.ENGLISH) }

/**
 * Extension function to get the English versions of resource strings regardless of the language
 * the user's device is in.
 *
 * When using getEnString in OneLogin only the resources id was returned,
 * hence why a new method has been created. Unsure of how or why the original issue exists as
 * it is used extensively in the id-check project.
 *
 * @param id String resource ID.
 * @param formatArgs Any additional arguments to format the string.
 */
fun Context.getEnglishString(@StringRes id: Int, vararg formatArgs: Any): String =
    with(createConfigurationContext(englishConfiguration)) { getString(id, *formatArgs) }
