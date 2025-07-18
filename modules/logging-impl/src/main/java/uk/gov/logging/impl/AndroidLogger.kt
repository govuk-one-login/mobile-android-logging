package uk.gov.logging.impl

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.api.Logger
import javax.inject.Inject

class AndroidLogger @Inject constructor(
    private val crashLogger: CrashLogger,
) : Logger {
    override fun debug(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    override fun info(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
        crashLogger.log("I : $tag : $msg")
    }

    override fun error(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, throwable)
        }
        crashLogger.log(throwable)
    }

    override fun error(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
        crashLogger.log("E : $tag : $msg")
    }
}
