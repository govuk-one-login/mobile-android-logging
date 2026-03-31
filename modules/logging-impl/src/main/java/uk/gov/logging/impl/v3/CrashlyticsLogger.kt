package uk.gov.logging.impl.v3

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LogEntry

class CrashlyticsLogger(
    private val crashlytics: FirebaseCrashlytics,
) : CrashLogger {
    override fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    ) {
        crashlytics.recordException(throwable)
        errorKeys.forEach {
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
                is LogEntry.WithException -> error(entry.tag, entry.message, entry.throwable, entry.errorKeys)
                else -> logBasicEntries(entry)
            }
        }
    }

    fun logBasicEntries(entry: LogEntry) {
        when (entry.level) {
            Log.WARN -> warning(entry.tag, entry.message)
            Log.ERROR -> error(entry.tag, entry.message)
            Log.INFO -> info(entry.tag, entry.message)
        }
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        crashlytics.log("I : $tag : $message")
    }

    override fun error(
        tag: String,
        message: String,
    ) {
        crashlytics.log("E : $tag : $message")
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        crashlytics.recordException(throwable)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) {
        if (errorKeys == null) {
            crashlytics.recordException(throwable)
        } else {
            crashlytics.recordException(throwable)
            errorKeys.let {
                it.key.let { key -> crashlytics.setCustomKey(key, errorKeys.value.toString()) }
            }
        }
    }

    override fun warning(
        tag: String,
        message: String,
    ) {
        crashlytics.log("W : $tag : $message")
    }
}
