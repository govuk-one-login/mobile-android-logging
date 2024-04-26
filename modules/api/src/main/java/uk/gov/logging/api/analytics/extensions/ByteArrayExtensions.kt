package uk.gov.logging.api.analytics.extensions

/**
 * Converts a string into it's hexadecimal representation.
 */
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
