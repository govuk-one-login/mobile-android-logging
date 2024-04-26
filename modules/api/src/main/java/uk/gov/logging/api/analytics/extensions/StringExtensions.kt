package uk.gov.logging.api.analytics.extensions

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * Hashes the byte array representation of a String via the MD5 algorithm.
 *
 * @return the hexadecimal String representation of the hashed byte array.
 */
fun String.md5(): String = MessageDigest.getInstance("MD5")
    .digest(this.toByteArray(StandardCharsets.UTF_8))
    .toHex()
