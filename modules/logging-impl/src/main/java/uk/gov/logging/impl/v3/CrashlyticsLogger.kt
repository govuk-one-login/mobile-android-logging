package uk.gov.logging.impl.v3

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v3.LogEntry
import uk.gov.logging.api.v3.LogLevel
import uk.gov.logging.api.v3.Logger
import uk.gov.logging.api.v3.LoggingProperties
import uk.gov.logging.impl.crashlytics.FirebaseCrashlyticsWrapper
import uk.gov.logging.impl.crashlytics.FirebaseCrashlyticsWrapperImpl

class CrashlyticsLogger internal constructor(
    private val crashlytics: FirebaseCrashlyticsWrapper,
) : Logger {
    constructor(
        crashlytics: FirebaseCrashlytics,
    ) : this(
        crashlytics = FirebaseCrashlyticsWrapperImpl(crashlytics),
    )

    override fun log(
        entry: LogEntry,
        properties: LoggingProperties,
    ) {
        if (!properties.allowRemote) return

        crashlytics.log(entry.asLogMessage())

        if (entry is LogEntry.Exception) {
            crashlytics.recordException(
                entry.throwable,
                entry.customKeys.associate { it.key to it.value.toString() },
            )
        }
    }
}

internal fun LogEntry.asLogMessage(): String = "${level.symbol()} : $tag : $message"

internal fun LogLevel.symbol(): String =
    when (this) {
        LogLevel.Verbose -> "V"
        LogLevel.Debug -> "D"
        LogLevel.Info -> "I"
        LogLevel.Warn -> "W"
        LogLevel.Error -> "E"
    }
