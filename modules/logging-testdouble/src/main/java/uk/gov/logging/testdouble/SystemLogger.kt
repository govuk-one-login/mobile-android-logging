package uk.gov.logging.testdouble

import uk.gov.logging.api.Logger
import javax.inject.Inject

@Suppress("TooManyFunctions")
class SystemLogger @Inject constructor() : Logger {
    private var logs = mutableListOf<LogEntry>()

    val size: Int get() = logs.size

    fun any(condition: (LogEntry) -> Boolean) = logs.any(condition)

    operator fun contains(message: String): Boolean = logs.any { entry ->
        entry.message == message
    }

    operator fun contains(entry: LogEntry): Boolean = when (entry) {
        is LogEntry.Message -> entry in logs
        is LogEntry.Error ->
            logs.any { logEntry ->
                logEntry is LogEntry.Error &&
                    logEntry.tag == entry.tag &&
                    logEntry.message == entry.message &&
                    logEntry.throwable.javaClass == entry.throwable.javaClass &&
                    logEntry.throwable.message == entry.throwable.message
            }
    }

    operator fun get(i: Int): LogEntry = this.logs[i]

    override fun debug(tag: String, msg: String) {
        doLog(tag, msg)
    }

    override fun info(tag: String, msg: String) {
        doLog(tag, msg)
    }

    override fun error(tag: String, msg: String, throwable: Throwable) {
        doLog(tag, msg, throwable)
    }

    override fun error(tag: String, msg: String) {
        doLog(tag, msg)
    }

    override fun toString(): String =
        "SystemLogger(logs=${logs.toTypedArray().contentToString()})"

    private fun doLog(tag: String, msg: String) {
        println("$tag: $msg")
        logs.add(LogEntry.Message(tag, msg))
    }

    private fun doLog(tag: String, msg: String, throwable: Throwable) {
        println("$tag: $msg")
        logs.add(LogEntry.Error(tag, msg, throwable))
    }
}
