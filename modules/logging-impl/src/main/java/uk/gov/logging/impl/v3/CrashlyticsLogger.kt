package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.customKeys.CustomKeys

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(
        throwable: Throwable,
        vararg customKeys: CustomKeys,
    ) {
        crashlytics.recordException(throwable)
        customKeys.forEach {
            it.let {
                crashlytics.setCustomKey(it.key, it.value.toString())
            }
        }
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun log(entries: Collection<LogEntry>) {
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.WithException -> {
                    crashlytics.recordException(entry.throwable)
                    entry.customKeys?.forEach { customkeys ->
                        crashlytics.setCustomKey(customkeys.key, customkeys.value.toString())
                    }
                }
                else -> logBasicEntries(entry)
            }
        }
    }

    fun logBasicEntries(entry: LogEntry) {
        when (entry.level) {
            Log.WARN -> crashlytics.log("W : ${entry.tag} : ${entry.message}")
            Log.ERROR -> crashlytics.log("E : ${entry.tag} : ${entry.message}")
            Log.INFO -> crashlytics.log("I : ${entry.tag} : ${entry.message}")
        }
    }
}
