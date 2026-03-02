package uk.gov.logging.impl.v2

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.LogEntry
import uk.gov.logging.api.v2.Logger
import javax.inject.Inject

class AndroidLogger @Inject constructor(
    private val crashLogger: CrashLogger,
) : Logger {
    override fun log(entries: Collection<LogEntry>): Unit =
        entries.forEach { entries ->

            if (BuildConfig.DEBUG) {
                Log.d(entries.tag, entries.message)
            }
            crashLogger.log(
                entries.message,
            )
        }

    override fun debug(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
        crashLogger.log("I : $tag : $message")
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
        crashLogger.log(message)
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
}
