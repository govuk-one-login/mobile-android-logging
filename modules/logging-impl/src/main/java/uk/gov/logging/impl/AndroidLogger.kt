package uk.gov.logging.impl

import android.util.Log
import uk.gov.logging.api.BuildConfig
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.api.Logger
import javax.inject.Inject

@Deprecated(
    message =
        "Replace with v3 MultiLogger " +
            "-aim to remove by 27th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.impl.v3.MultiLogger",
        ),
    level = DeprecationLevel.WARNING,
)
class AndroidLogger @Inject constructor(
    private val crashLogger: CrashLogger,
) : Logger {
    override fun debug(
        tag: String,
        msg: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    override fun info(
        tag: String,
        msg: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg)
        }
        crashLogger.log("I : $tag : $msg")
    }

    override fun error(
        tag: String,
        msg: String,
        throwable: Throwable,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, throwable)
        }
        crashLogger.log(throwable)
    }

    override fun error(
        tag: String,
        msg: String,
    ) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
        crashLogger.log("E : $tag : $msg")
    }
}
