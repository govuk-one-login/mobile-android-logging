package uk.gov.logging.impl.v2

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.Logger
import uk.gov.logging.api.v2.errorKeys.ErrorKeys

class AndroidLogger(
    private val crashLogger: CrashLogger,
) : Logger {
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
        throwable: Throwable,
        errorKeys: ErrorKeys?,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable)
        }
        crashLogger.log(throwable, errorKeys)
    }

    override fun error(
        tag: String,
        message: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
        crashLogger.log("E: $tag : $message")
    }
}
