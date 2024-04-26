package uk.gov.logging.api.analytics.logging

import java.util.regex.Pattern

/**
 * Constant value used to identify all Digital Identity events and parameters across web and app.
 * This acts as a map key for analytics events.
 */
const val DIGITAL_IDENTITY_ID = "di_identifier"

/**
 * Value used with the [DIGITAL_IDENTITY_ID] map key.
 */
const val DIGITAL_IDENTITY_ID_VALUE = "digital identity"

/**
 * Constant value used to identify all DCMAW events and parameters within the digital identity
 * programme across web and app. This acts as a map key for analytics events.
 */
const val DIGITAL_IDENTITY_JOURNEY = "di_journey"

/**
 * Value used with the [DIGITAL_IDENTITY_JOURNEY] map key.
 */
const val DIGITAL_IDENTITY_JOURNEY_VALUE = "document checking application"
const val DOCUMENT_TYPE_JOURNEY_KEY = "cri_journey"
const val ENDPOINT = "endpoint"
const val EVENT_NAME = "eventName"
const val EXTERNAL = "external"
const val FORTY_CHAR_LIMIT = 40
const val HASH = "hash"
const val HUNDRED_CHAR_LIMIT = 100
const val LANGUAGE = "language"
const val LINK_DOMAIN = "link_domain"
const val REASON = "reason"
const val RESPONSE = "response"
const val STATUS = "status"
const val TEXT = "text"
const val TITLE = "title"
const val TYPE = "type"

val LOWER_ALPHANUMERIC_FORTY_LIMIT: Pattern = Pattern.compile("^[a-z\\d]{1,40}$")
val LOWER_ALPHANUMERIC_HUNDRED_LIMIT: Pattern = Pattern.compile("^[a-z\\d]{1,100}$")
val LOWER_SNAKE_CASE_FORTY_LIMIT: Pattern = Pattern.compile("^[a-z_]{1,40}$")
val UPPER_SNAKE_CASE_FORTY_LIMIT: Pattern = Pattern.compile("^[A-Z_]{1,40}$")
