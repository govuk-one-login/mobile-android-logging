package uk.gov.logging.impl.v3

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v3.CrashLogger
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

@Suppress("SpreadOperator")
class AndroidLogger(
    private val crashLogger: CrashLogger,
) : Logger {
    override fun log(entries: Collection<LogEntry>) =
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.Basic -> logBasicEntries(entry)
                is LogEntry.Error -> logEntriesWithException(entry)
                is LocalLogEntry.Basic -> logBasicEntries(entry)
                is LocalLogEntry.Error -> logBasicEntries(entry)
                else -> logBasicEntries(entry)
            }
        }

    private fun logBasicEntries(logEntry: LogEntry) {
        if (BuildConfig.DEBUG || logEntry is LocalLogEntry) {
            when (logEntry.level) {
                Log.DEBUG -> Log.d(logEntry.tag, logEntry.message)
                Log.WARN -> Log.w(logEntry.tag, logEntry.message)
                Log.ERROR -> Log.e(logEntry.tag, logEntry.message)
                Log.INFO -> Log.i(logEntry.tag, logEntry.message)
            }
        }
        if (logEntry !is LocalLogEntry) {
            when (logEntry.level) {
                Log.WARN -> crashLogger(logEntry.tag, logEntry.message, "W")
                Log.ERROR -> crashLogger(logEntry.tag, logEntry.message, "E")
                Log.INFO -> crashLogger(logEntry.tag, logEntry.message, "I")
            }
        }
    }

    private fun logEntriesWithException(logEntry: LogEntry.Error) {
        if (BuildConfig.DEBUG) {
            Log.e(logEntry.tag, logEntry.message, logEntry.throwable)
        }

        crashLogger.log(logEntry.throwable, *logEntry.customKeys.toTypedArray())
    }

    private fun crashLogger(
        tag: String,
        message: String,
        suffix: String,
    ) {
        crashLogger.log("$suffix : $tag : $message")
    }
}
