package uk.gov.logging.api.analytics.extensions

import android.net.Uri
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

/**
 * @return the domain (host) section of a String URL
 */
val String.domain: String get() = Uri.parse(this).host.orEmpty()
