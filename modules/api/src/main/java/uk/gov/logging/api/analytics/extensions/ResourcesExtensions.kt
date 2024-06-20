package uk.gov.logging.api.analytics.extensions

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import java.util.Locale

/**
 * Extension function to get the English versions of resource strings regardless of the language
 * the user's device is in.
 *
 * @param context Context of user's current app environment.
 * @param id String resource ID.
 * @param formatArgs Any additional arguments to format the string.
 */
@Suppress("AppBundleLocaleChanges")
fun Resources.getEnString(
    context: Context,
    @StringRes
    id: Int,
    vararg formatArgs: Any
): String {
    val enConfig = Configuration(configuration)
    enConfig.setLocale(Locale("en"))
    val enResources = context.createConfigurationContext(enConfig).resources

    return if (formatArgs.isEmpty()) {
        enResources.getString(id)
    } else {
        enResources.getString(id, *formatArgs)
    }
}

/**
 * Delegates to [Resources.getEnString] using the context that calls this method.
 */
@Suppress("AppBundleLocaleChanges")
fun Context.getEnString(
    @StringRes
    id: Int,
    vararg formatArgs: Any
): String {
    return resources.getEnString(this, id, formatArgs)
}

/**
 * Extension function to get the English versions of resource plurals regardless of the language
 * the user's device is in.
 *
 * @param context Context of user's current app environment.
 * @param id Plural resource ID.
 * @param quantity Integer quantity of plural resource.
 * @param formatArgs Any additional arguments to format the resulting string.
 */
@Suppress("AppBundleLocaleChanges")
fun Resources.getEnQuantityString(
    context: Context,
    @PluralsRes
    id: Int,
    quantity: Int,
    vararg formatArgs: Any
): String {
    val enConfig = Configuration(configuration)
    enConfig.setLocale(Locale("en"))
    val enResources = context.createConfigurationContext(enConfig).resources
    return if (formatArgs.isEmpty()) {
        enResources.getQuantityString(id, quantity)
    } else {
        enResources.getQuantityString(id, quantity, *formatArgs)
    }
}

/**
 * Delegates to [Resources.getEnQuantityString] using the context that calls this method.
 */
@Suppress("AppBundleLocaleChanges")
fun Context.getEnQuantityString(
    @PluralsRes
    id: Int,
    quantity: Int,
    vararg formatArgs: Any
): String {
    return resources.getEnQuantityString(this, id, quantity, formatArgs)
}
