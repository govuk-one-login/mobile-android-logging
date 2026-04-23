package uk.gov.logging.impl.v3

import android.util.Log
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.Logger

/**
 * [Logger] implementation for android [Log] functions
 */
data object LogcatLogger : Logger {
    override fun log(entry: LogEntry) =
        when (entry) {
            is LogEntry.Exception -> logException(entry)
            is LogEntry.Message -> logMessage(entry)
        }

    private fun logMessage(entry: LogEntry) {
        val logFunction: ((String, String) -> Unit)? =
            when (entry.level) {
                LogLevel.Verbose -> Log::v
                LogLevel.Debug -> Log::d
                LogLevel.Info -> Log::i
                LogLevel.Warn -> Log::w
                LogLevel.Error -> Log::e
            }

        logFunction?.invoke(
            entry.tag,
            entry.message,
        )
    }

    private fun logException(entry: LogEntry.Exception) {
        val logFunction: ((String, String, Throwable) -> Unit)? =
            when (entry.level) {
                LogLevel.Debug -> Log::d
                LogLevel.Error -> Log::e
                LogLevel.Info -> Log::i
                LogLevel.Verbose -> Log::v
                LogLevel.Warn -> Log::w
            }

        logFunction?.invoke(
            entry.tag,
            entry.message,
            entry.throwable,
        )
    }
}
