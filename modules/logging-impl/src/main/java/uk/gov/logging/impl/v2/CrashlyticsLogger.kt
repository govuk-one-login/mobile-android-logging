package uk.gov.logging.impl.v2

import com.google.firebase.crashlytics.FirebaseCrashlytics
import uk.gov.logging.api.v2.CrashLogger
import uk.gov.logging.api.v2.errorKeys.ErrorKeys
import uk.gov.logging.impl.crashlytics.FirebaseCrashlyticsWrapper
import uk.gov.logging.impl.crashlytics.FirebaseCrashlyticsWrapperImpl

@Deprecated(
    message =
        "Replace with  v3 CrashlyticsLogger" +
            "-aim to remove by 27th of July 2026",
    replaceWith =
        ReplaceWith(
            "uk.gov.logging.impl.v3.CrashlyticsLogger",
        ),
    level = DeprecationLevel.WARNING,
)
class CrashlyticsLogger internal constructor(
    private val crashlytics: FirebaseCrashlyticsWrapper,
) : CrashLogger {
    constructor(
        crashlytics: FirebaseCrashlytics,
    ) : this(
        crashlytics = FirebaseCrashlyticsWrapperImpl(crashlytics),
    )

    override fun log(
        throwable: Throwable,
        vararg errorKeys: ErrorKeys,
    ) {
        crashlytics.recordException(
            throwable,
            errorKeys.associate { it.key to it.value.toString() },
        )
    }

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }
}
