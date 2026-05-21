package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.api.v3.customkey.CustomKey
import uk.gov.logging.api.v3.customkey.ErrorKeys
import uk.gov.logging.impl.v3.customkey.actionKey
import uk.gov.logging.impl.v3.customkey.componentKey
import java.util.Map.entry

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : Logger {
    override fun log(
        entry: LogEntry,
        properties: LoggingProperties,
    ) {
        if (!properties.allowRemote) return

        crashlytics.log(entry.asLogMessage())

        if (entry is LogEntry.Exception) {
            crashlytics.setErrorCustomKeys(entry.errorKeys)
            crashlytics.recordException(entry.throwable)

            // Prevent the error keys leaking into the next event
            crashlytics.clearErrorCustomKeys()
        }
    }
}

internal fun LogEntry.asLogMessage(): String = "${level.symbol()} : $tag : $message"

internal fun LogLevel.symbol(): String =
    when (this) {
        LogLevel.Verbose -> "V"
        LogLevel.Debug -> "D"
        LogLevel.Info -> "I"
        LogLevel.Warn -> "W"
        LogLevel.Error -> "E"
    }

private fun FirebaseCrashlytics.setErrorCustomKeys(errorKeys: ErrorKeys) {
    setCustomKey(errorKeys.componentKey())
    setCustomKey(errorKeys.actionKey())
}

private fun FirebaseCrashlytics.clearErrorCustomKeys() = setErrorCustomKeys(ErrorKeys())

private fun FirebaseCrashlytics.setCustomKey(customKey: CustomKey) =
    when (customKey) {
        is CustomKey.BooleanKey -> setCustomKey(customKey.key, customKey.value)
        is CustomKey.DoubleKey -> setCustomKey(customKey.key, customKey.value)
        is CustomKey.IntKey -> setCustomKey(customKey.key, customKey.value)
        is CustomKey.StringKey -> setCustomKey(customKey.key, customKey.value)
    }
