package uk.gov.logging.api.analytics.preferences

data class StubAnalyticsPrefs(
    private var stubPermanentValue: Boolean = false,
    private var stubGrantedValue: Boolean = false
) : IAnalyticsPrefs {
    override fun deniedPermanently() = stubPermanentValue

    override fun isGranted() = stubGrantedValue

    override fun updateGranted(granted: Boolean) {
        stubGrantedValue = granted
    }
}
