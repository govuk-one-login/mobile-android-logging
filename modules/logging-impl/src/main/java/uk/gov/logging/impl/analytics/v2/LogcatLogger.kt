package uk.gov.logging.impl.analytics.v2

import android.util.Log
import uk.gov.logging.api.v2.LogEntry
import uk.gov.logging.api.v2.Logger

/**
 * [Logger] implementation for android Log functions
 */
data object LogcatLogger : Logger {
    override fun log(entries: Collection<LogEntry>): Unit =
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.WithException -> handleExceptionLogEntry(entry)
                else -> handleBasicLogEntry(entry)
            }
        }

    private fun handleExceptionLogEntry(entry: LogEntry.WithException) {
        val logFunction: ((String, String, Throwable) -> Unit)? =
            when (entry.level) {
                Log.DEBUG -> Log::d
                Log.ERROR -> Log::e
                Log.INFO -> Log::i
                Log.VERBOSE -> Log::v
                Log.WARN -> Log::w
                else -> null
            }

        logFunction?.invoke(
            entry.tag,
            entry.message,
            entry.throwable,
        )
    }

    private fun handleBasicLogEntry(entry: LogEntry) {
        val logFunction: ((String, String) -> Unit)? =
            when (entry.level) {
                Log.DEBUG -> Log::d
                Log.ERROR -> Log::e
                Log.INFO -> Log::i
                Log.VERBOSE -> Log::v
                Log.WARN -> Log::w
                else -> null
            }

        logFunction?.invoke(
            entry.tag,
            entry.message,
        )
    }
}
