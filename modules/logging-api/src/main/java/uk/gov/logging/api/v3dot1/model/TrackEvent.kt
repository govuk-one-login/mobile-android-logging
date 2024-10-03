package uk.gov.logging.api.v3dot1.model

import uk.gov.logging.api.analytics.logging.EXTERNAL
import uk.gov.logging.api.analytics.logging.HUNDRED_CHAR_LIMIT
import uk.gov.logging.api.analytics.logging.LINK_DOMAIN
import uk.gov.logging.api.analytics.logging.RESPONSE
import uk.gov.logging.api.analytics.logging.TEXT
import uk.gov.logging.api.analytics.logging.TYPE
import uk.gov.logging.api.analytics.parameters.data.Type

sealed class TrackEvent(
    type: String,
    params: RequiredParameters
) : AnalyticsEvent {
    override val eventType: String = type
    override val parameters: RequiredParameters = params

    data class Button(
        private val text: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.NAVIGATION_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            TYPE to Type.CallToAction.value,
        ) + params.asMap()
    }

    data class Icon(
        private val text: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.NAVIGATION_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            TYPE to Type.Icon.value,
        ) + params.asMap()
    }

    data class Link(
        private val isExternal: Boolean,
        private val domain: String,
        private val text: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.NAVIGATION_EVENT, params) {
        private val _linkDomain get() = domain.take(HUNDRED_CHAR_LIMIT)
        private val _isExternal get() = isExternal.toString()
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            EXTERNAL to _isExternal,
            LINK_DOMAIN to _linkDomain,
            TEXT to _text,
            TYPE to Type.Link.value,
        ) + params.asMap()
    }

    data class FormActionMenu(
        private val text: String,
        private val response: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.FORM_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)
        private val _response get() = response.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            RESPONSE to _response,
            TYPE to Type.ActionMenu.value,
        ) + params.asMap()
    }

    data class FormCallToAction(
        private val text: String,
        private val response: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.FORM_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)
        private val _response get() = response.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            RESPONSE to _response,
            TYPE to Type.CallToAction.value,
        ) + params.asMap()
    }

    data class Form(
        private val text: String,
        private val response: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.FORM_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)
        private val _response get() = response.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            RESPONSE to _response,
            TYPE to Type.SubmitForm.value,
        ) + params.asMap()
    }

    data class ActionMenu(
        private val text: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.POPUP_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            TYPE to Type.ActionMenu.value,
        ) + params.asMap()
    }

    data class PopUp(
        private val text: String,
        private val params: RequiredParameters
    ) : TrackEvent(EventTypes.POPUP_EVENT, params) {
        private val _text get() = text.take(HUNDRED_CHAR_LIMIT)

        override fun asMap(): Map<out String, Any?> = mapOf<String, Any?>(
            TEXT to _text,
            TYPE to Type.PopUp.value,
        ) + params.asMap()
    }

    object EventTypes {
        const val POPUP_EVENT = "popup"
        const val FORM_EVENT = "form_response"
        const val NAVIGATION_EVENT = "navigation"
    }
}
