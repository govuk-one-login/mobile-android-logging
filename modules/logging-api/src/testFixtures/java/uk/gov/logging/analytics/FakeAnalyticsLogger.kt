package uk.gov.logging.analytics

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.v3.AnalyticsLogger

@Suppress("TooManyFunctions")
class FakeAnalyticsLogger(
    val loggedEvents: MutableList<AnalyticsEvent> = mutableListOf(),
) : AnalyticsLogger,
    Iterable<AnalyticsEvent> by loggedEvents {
    private var memorisedEvent: AnalyticsEvent? = null

    val size: Int get() = loggedEvents.size

    var enabled: Boolean? = null

    override fun logEvent(
        shouldLogEvent: Boolean,
        vararg events: AnalyticsEvent,
    ) {
        events.forEach { event ->
            if (shouldLogEvent) {
                if (isScreenView(event)) {
                    memorisedEvent = event
                }
                this.loggedEvents
                    .add(event)
            }
        }
    }

    fun shouldLog(event: AnalyticsEvent): Boolean =
        !isScreenView(event) ||
            !isDuplicateScreenView(event)

    private fun isScreenView(event: AnalyticsEvent): Boolean = event.eventType === SCREEN_VIEW

    fun isDuplicateScreenView(event: AnalyticsEvent): Boolean = event == memorisedEvent

    override fun setEnabled(isEnabled: Boolean) {
        enabled = isEnabled
    }

    operator fun contains(event: AnalyticsEvent): Boolean = event in this.loggedEvents

    operator fun contains(conditionBlock: (AnalyticsEvent) -> Boolean): Boolean =
        this.loggedEvents.any {
            conditionBlock(it)
        }

    operator fun get(i: Int): AnalyticsEvent = this.loggedEvents[i]

    override operator fun iterator() = this.loggedEvents.iterator()

    operator fun contains(events: Collection<AnalyticsEvent>) = this.loggedEvents.containsAll(events)

    fun containsOnly(events: Collection<AnalyticsEvent>): Boolean {
        require(this.size == events.size) {
            "Mismatched collection size!"
        }

        return events in this
    }

    fun filter(predicate: (AnalyticsEvent) -> Boolean): List<AnalyticsEvent> = this.loggedEvents.filter(predicate)

    override fun toString(): String = "FakeAnalyticsLogger(size=$size, events=$loggedEvents)"
}
