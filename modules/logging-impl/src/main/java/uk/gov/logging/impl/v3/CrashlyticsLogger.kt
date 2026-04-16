package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.customkey.CustomKey

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : Logger {
    override fun log(entries: Iterable<LogEntry>) {
        entries
            .filter { entry ->
                entry !is LocalLogEntry
            }.forEach { entry ->
                when (entry) {
                    is LogEntry.Basic -> {
                        when (entry.level) {
                            Log.WARN -> "W"
                            Log.ERROR -> "E"
                            Log.INFO -> "I"
                            else -> null
                        }?.let { level ->
                            "$level : ${entry.tag} : ${entry.message}"
                        }?.let(::logBasic)
                    }
                    is LogEntry.Error -> logError(entry.throwable, entry.customKeys)

                    else -> {
                        // do nothing with unrelated log entries
                    }
                }
            }
    }

    private fun logError(
        throwable: Throwable,
        customKey: List<CustomKey>,
    ) {
        customKey.forEach {
            crashlytics.setCustomKey(it.key, it.value.toString())
        }
        crashlytics.recordException(throwable)
    }

    private fun logBasic(message: String) {
        crashlytics.log(message)
    }
}
