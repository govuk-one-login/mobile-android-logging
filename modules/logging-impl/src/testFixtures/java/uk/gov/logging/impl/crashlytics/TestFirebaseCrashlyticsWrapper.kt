package uk.gov.logging.impl.crashlytics

class TestFirebaseCrashlyticsWrapper : FirebaseCrashlyticsWrapper {
    val loggedMessages = mutableListOf<String>()
    val recordedExceptions = mutableListOf<RecordedException>()

    override fun log(message: String) {
        loggedMessages += message
    }

    override fun recordException(throwable: Throwable) {
        recordedExceptions += RecordedException(throwable, emptyMap())
    }

    override fun recordException(
        throwable: Throwable,
        keys: Map<String, String>,
    ) {
        recordedExceptions += RecordedException(throwable, keys)
    }

    data class RecordedException(
        val throwable: Throwable,
        val keys: Map<String, String>,
    )
}
