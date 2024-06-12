package uk.gov.logging.api.analytics.extensions

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import java.util.Locale

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

@Suppress("AppBundleLocaleChanges")
fun Context.getEnString(
    @StringRes
    id: Int,
    vararg formatArgs: Any
): String {
    return resources.getEnString(this, id, formatArgs)
}

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

@Suppress("AppBundleLocaleChanges")
fun Context.getEnQuantityString(
    @PluralsRes
    id: Int,
    quantity: Int,
    vararg formatArgs: Any
): String {
    return resources.getEnQuantityString(this, id, quantity, formatArgs)
}
