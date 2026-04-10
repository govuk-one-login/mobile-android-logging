package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customKeys.CustomKey

@Suppress("SpreadOperator")
class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(
        throwable: Throwable,
        vararg customKeys: CustomKey,
    ) {
        customKeys.forEach {
            crashlytics.setCustomKey(it.key, it.value.toString())
        }
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun log(entries: Collection<LogEntry>) {
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.Error -> log(entry.throwable, *entry.customKeys.toTypedArray())
                is LocalLogEntry.Basic -> logBasicEntries(entry)
                is LocalLogEntry.Error -> logBasicEntries(entry)
                is LogEntry.WithException -> crashlytics.recordException(entry.throwable)

                else -> logBasicEntries(entry)
            }
        }
    }

    fun logBasicEntries(entry: LogEntry) {
        when (entry.level) {
            Log.WARN -> log("W : ${entry.tag} : ${entry.message}")
            Log.ERROR -> log("E : ${entry.tag} : ${entry.message}")
            Log.INFO -> log("I : ${entry.tag} : ${entry.message}")
        }
    }
}
