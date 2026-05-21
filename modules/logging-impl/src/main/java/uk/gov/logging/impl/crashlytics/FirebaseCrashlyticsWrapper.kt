package uk.gov.logging.impl.crashlytics

interface FirebaseCrashlyticsWrapper {
    fun log(message: String)

    fun recordException(throwable: Throwable)

    fun recordException(
        throwable: Throwable,
        keys: Map<String, String>,
    )
}
