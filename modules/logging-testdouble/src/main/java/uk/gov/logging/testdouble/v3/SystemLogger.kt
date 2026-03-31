package uk.gov.logging.testdouble.v3

import android.util.Log
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import uk.gov.logging.api.v3.LocalLogEntry
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.Logger

@Suppress("TooManyFunctions", "CyclomaticComplexMethod")
class SystemLogger : Logger {
    override fun log(entries: Collection<LogEntry>) {
        entries.forEach { entry ->
            when (entry) {
                is LogEntry.Basic -> logBasicEntries(entry)
                is LogEntry.Error -> {
                    error(entry.tag, entry.message, entry.throwable, entry.errorKeys)
                }
                is LocalLogEntry.Basic -> {
                    logBasicEntries(entry)
                }
                is LocalLogEntry.Error -> error(entry.tag, entry.message, entry.throwable, entry.errorKeys)
                is LogEntry.WithException -> error(entry.tag, entry.message, entry.throwable, entry.errorKeys)
            }
        }
    }

    private fun logBasicEntries(entry: LogEntry) {
        when (entry.level) {
            Log.WARN -> warning(entry.tag, entry.message)
            Log.ERROR -> error(entry.tag, entry.message)
            Log.INFO -> info(entry.tag, entry.message)
        }
    }

    private var logs = mutableListOf<LogEntry>()

    val size: Int get() = logs.size

    fun any(condition: (LogEntry) -> Boolean) = logs.any(condition)

    operator fun contains(message: String): Boolean =
        logs.any { entry ->
            entry.message == message
        }

    operator fun contains(entry: LogEntry): Boolean =
        when (entry) {
            is LogEntry.Basic -> entry in logs
            is LogEntry.Error ->
                logs.any { logEntry ->
                    logEntry is LogEntry.Error &&
                        logEntry.tag == entry.tag &&
                        logEntry.message == entry.message &&
                        logEntry.throwable.javaClass == entry.throwable.javaClass &&
                        logEntry.throwable.message == entry.throwable.message &&
                        logEntry.errorKeys?.key == entry.errorKeys?.key &&
                        logEntry.errorKeys?.value == entry.errorKeys?.value
                }

            is LocalLogEntry.Basic -> entry in logs
            is LocalLogEntry.Error ->
                logs.any { logEntry ->
                    logEntry is LogEntry.Error &&
                        logEntry.tag == entry.tag &&
                        logEntry.message == entry.message &&
                        logEntry.throwable.javaClass == entry.throwable.javaClass &&
                        logEntry.throwable.message == entry.throwable.message &&
                        logEntry.errorKeys?.key == entry.errorKeys?.key &&
                        logEntry.errorKeys?.value == entry.errorKeys?.value
                }

            is LogEntry.WithException ->
                logs.any { logEntry ->
                    logEntry is LogEntry.Error &&
                        logEntry.tag == entry.tag &&
                        logEntry.message == entry.message &&
                        logEntry.throwable.javaClass == entry.throwable.javaClass &&
                        logEntry.throwable.message == entry.throwable.message &&
                        logEntry.errorKeys?.key == entry.errorKeys?.key &&
                        logEntry.errorKeys?.value == entry.errorKeys?.value
                }
        }

    operator fun get(i: Int): LogEntry = this.logs[i]

    override fun toString(): String = "SystemLogger(logs=${logs.toTypedArray().contentToString()})"

    override fun debug(
        tag: String,
        message: String,
    ) {
        doLog(tag, message, Log.DEBUG)
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        doLog(tag, message, Log.INFO)
    }

    override fun error(
        tag: String,
        message: String,
    ) {
        doLog(tag, message, Log.ERROR)
    }

    override fun warning(
        tag: String,
        message: String,
    ) {
        doLog(tag, message, Log.WARN)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        doLog(tag, message, throwable, null)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) {
        doLog(tag, message, throwable, errorKeys)
    }

    private fun doLog(
        tag: String,
        msg: String,
        level: Int,
    ) {
        println("$tag: $msg")
        logs.add(LogEntry.Basic(level, msg, tag))
    }

    private fun doLog(
        tag: String,
        msg: String,
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) {
        println("$tag: $msg")
        logs.add(LogEntry.Error(Log.ERROR, msg, tag, throwable, errorKeys))
    }
}
