package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.Logger

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : Logger {
    override fun log(entry: LogEntry) {
        if (entry.isLocalOnly) {
            return
        }

        crashlytics.log(entry.asLogMessage())

        if (entry is LogEntry.Exception) {
            entry.customKeys.forEach {
                crashlytics.setCustomKey(it.key, it.value.toString())
            }
            crashlytics.recordException(entry.throwable)
        }
    }
}

fun LogEntry.asLogMessage(): String = "${level.symbol()} : $tag : $message"

fun LogLevel.symbol(): String =
    when (this) {
        LogLevel.Verbose -> "V"
        LogLevel.Debug -> "D"
        LogLevel.Info -> "I"
        LogLevel.Warn -> "W"
        LogLevel.Error -> "E"
    }
