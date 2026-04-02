package uk.gov.logging.impl.v3

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

class AndroidLogger(
    private val crashLogger: CrashLogger,
) : Logger {
    override fun log(entries: Collection<LogEntry>) {
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.WithException -> logEntriesWithException(entry)
                else -> logBasicEntries(entry)
            }
        }
    }

    fun logBasicEntries(logEntry: LogEntry) {
        if (BuildConfig.DEBUG) {
            when (logEntry.level) {
                Log.DEBUG -> Log.d(logEntry.tag, logEntry.message)
                Log.WARN -> Log.w(logEntry.tag, logEntry.message)
                Log.ERROR -> Log.e(logEntry.tag, logEntry.message)
                Log.INFO -> Log.i(logEntry.tag, logEntry.message)
            }
        }
        when (logEntry.level) {
            Log.WARN -> crashLogger(logEntry.tag, logEntry.message, "W")
            Log.ERROR -> crashLogger(logEntry.tag, logEntry.message, "E")
            Log.INFO -> crashLogger(logEntry.tag, logEntry.message, "I")
        }
    }

    private fun logEntriesWithException(logEntry: LogEntry.WithException) {
        if (BuildConfig.DEBUG) {
            Log.e(logEntry.tag, logEntry.message, logEntry.throwable)
        }
        when (logEntry.level) {
            Log.ERROR ->
                {
                    crashLogger.log(logEntry.throwable)

                    logEntry.customKeys?.forEach {
                        crashLogger.log(logEntry.throwable, it)
                    }
                }
        }
    }

    fun crashLogger(
        tag: String,
        message: String,
        suffix: String,
    ) {
        crashLogger.log("$suffix : $tag : $message")
    }
}
