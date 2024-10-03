package uk.gov.logging.api.v3dot1.model

import com.google.firebase.analytics.FirebaseAnalytics
import uk.gov.logging.api.analytics.parameters.data.TaxonomyLevel2
import kotlin.test.Test
import kotlin.test.assertEquals

class AnalyticsEventTest {
    private val required = RequiredParameters(taxonomyLevel2 = TaxonomyLevel2.GOVUK)

    @Test
    fun `TrackEvent Button has event name of navigation`() {
        // Given a TrackEvent.Button
        val event = TrackEvent.Button("", required)
        // Then the event name is `navigation`
        assertEquals(expected = "navigation", actual = event.eventType)
    }

    @Test
    fun `TrackEvent Icon has event name of navigation`() {
        // Given a TrackEvent.Icon
        val event = TrackEvent.Icon("", required)
        // Then the event name is `navigation`
        assertEquals(expected = "navigation", actual = event.eventType)
    }

    @Test
    fun `TrackEvent Link has event name of navigation`() {
        // Given a TrackEvent.Link
        val event = TrackEvent.Link(false, "", "", required)
        // Then the event name is `navigation`
        assertEquals(expected = "navigation", actual = event.eventType)
    }

    @Test
    fun `TrackEvent FormActionMenu has event name of form_response`() {
        // Given a TrackEvent.FormActionMenu
        val event = TrackEvent.FormActionMenu("", "", required)
        // Then the event name is `form_response`
        assertEquals(expected = "form_response", actual = event.eventType)
    }

    @Test
    fun `TrackEvent FormCallToAction has event name of form_response`() {
        // Given a TrackEvent.FormCallToAction
        val event = TrackEvent.FormCallToAction("", "", required)
        // Then the event name is `form_response`
        assertEquals(expected = "form_response", actual = event.eventType)
    }

    @Test
    fun `TrackEvent Form has event name of form_response`() {
        // Given a TrackEvent.Form
        val event = TrackEvent.Form("", "", required)
        // Then the event name is `form_response`
        assertEquals(expected = "form_response", actual = event.eventType)
    }

    @Test
    fun `TrackEvent ActionMenu has event name of popup`() {
        // Given an TrackEvent.ActionMenu
        val event = TrackEvent.ActionMenu("", required)
        // Then the event name is `popup`
        assertEquals(expected = "popup", actual = event.eventType)
    }

    @Test
    fun `TrackEvent PopUp has event name of popup`() {
        // Given a TrackEvent.PopUp
        val event = TrackEvent.PopUp("", required)
        // Then the event name is `popup`
        assertEquals(expected = "popup", actual = event.eventType)
    }

    @Test
    fun `ViewEvent Screen has event name of screen_view`() {
        // Given a ViewEvent.Screen
        val event = ViewEvent.Screen("", "", required)
        // Then the event name is `screen_view`
        assertEquals(
            expected = FirebaseAnalytics.Event.SCREEN_VIEW,
            actual = event.eventType
        )
    }

    @Test
    fun `ViewEvent Error has event name of screen_view`() {
        // Given a ViewEvent.Error
        val event = ViewEvent.Error("", "", "", "", "", required)
        // Then the event name is `screen_view`
        assertEquals(
            expected = FirebaseAnalytics.Event.SCREEN_VIEW,
            actual = event.eventType
        )
    }
}
