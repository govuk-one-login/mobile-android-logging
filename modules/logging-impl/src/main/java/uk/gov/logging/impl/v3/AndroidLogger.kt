package uk.gov.logging.impl.v3

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
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
        when (logEntry.level) {
            Log.DEBUG -> if (BuildConfig.DEBUG) debug(logEntry.tag, logEntry.message)
            Log.WARN -> warning(logEntry.tag, logEntry.message)
            Log.ERROR -> error(logEntry.tag, logEntry.message)
            Log.INFO -> info(logEntry.tag, logEntry.message)
        }
    }

    private fun logEntriesWithException(logEntry: LogEntry.WithException) {
        when (logEntry.level) {
            Log.ERROR ->
                if (logEntry.errorKeys == null) {
                    error(logEntry.tag, logEntry.message, logEntry.throwable)
                } else {
                    error(logEntry.tag, logEntry.message, logEntry.throwable, logEntry.errorKeys)
                }
        }
    }

    override fun debug(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    override fun warning(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }

        crashLogger.log("W : $tag : $message")
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
        crashLogger.log("I : $tag : $message")
    }

    override fun error(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
        crashLogger.log("E : $tag : $message")
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable)
        }
        crashLogger.log(throwable)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable)
        }
        crashLogger.log(throwable)
    }
}
