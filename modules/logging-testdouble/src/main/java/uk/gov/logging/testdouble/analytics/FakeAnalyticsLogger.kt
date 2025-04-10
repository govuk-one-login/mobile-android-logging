package uk.gov.logging.testdouble.analytics

import com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW
import uk.gov.logging.api.analytics.AnalyticsEvent
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import javax.inject.Inject

@Suppress("TooManyFunctions")
class FakeAnalyticsLogger @Inject constructor() : AnalyticsLogger {
    private var memorisedEvent: AnalyticsEvent? = null

    private val events: MutableList<AnalyticsEvent> = mutableListOf()

    val size: Int get() = events.size

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

                this.events.add(event)
            }
        }
    }

    fun shouldLog(event: AnalyticsEvent): Boolean = !isScreenView(event) ||
        !isDuplicateScreenView(event)

    private fun isScreenView(event: AnalyticsEvent): Boolean = event.eventType === SCREEN_VIEW

    fun isDuplicateScreenView(event: AnalyticsEvent): Boolean = event == memorisedEvent

    override fun setEnabled(isEnabled: Boolean) {
        enabled = isEnabled
    }

    operator fun contains(event: AnalyticsEvent): Boolean = event in this.events

    operator fun contains(
        conditionBlock: (AnalyticsEvent) -> Boolean,
    ): Boolean = this.events.any(conditionBlock)

    operator fun get(i: Int): AnalyticsEvent = this.events[i]

    operator fun iterator() = this.events.iterator()

    operator fun contains(events: Collection<AnalyticsEvent>) = this.events.containsAll(events)

    fun containsOnly(events: Collection<AnalyticsEvent>): Boolean {
        require(this.size == events.size) {
            "Mismatched collection size!"
        }

        return events in this
    }

    fun filter(
        predicate: (AnalyticsEvent) -> Boolean,
    ): List<AnalyticsEvent> = this.events.filter(predicate)

    override fun toString(): String {
        return "FakeAnalyticsLogger(size=$size, events=$events)"
    }
}
