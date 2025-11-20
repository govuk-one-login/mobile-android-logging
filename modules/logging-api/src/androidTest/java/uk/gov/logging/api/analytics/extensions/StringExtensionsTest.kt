package uk.gov.logging.api.analytics.extensions

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import uk.gov.logging.api.test.R
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionsTest {
    @Test
    fun realDomainExtensionProperty() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Given a URL from a string resource
        val url = context.getEnglishString(R.string.openIdConnectBaseUrl, "")
        // When using the domain extension property
        val actual = url.domain
        // Then strip the path, queries,and scheme, leaving only the host / domain
        assertEquals("auth-stub.mobile.build.account.gov.uk", actual)
    }

    @Test
    fun domainExtensionProperty() {
        // Given a URL string
        val url = "https://www.test.please.work.gov.uk/path/to/enlightenment?lang=cy"
        // When using the domain extension property
        val actual = url.domain
        // Then strip the path, queries,and scheme, leaving only the host / domain
        assertEquals("www.test.please.work.gov.uk", actual)
    }

    @Ignore("Uncertain how to make this work gracefully, fixing in place for now")
    @Test
    fun domainExtensionPropertyFormatString() {
        // Given a URL format string
        val url = "https://www.test.please.work.gov.uk%s"
        // When using the domain extension property
        val actual = url.domain
        // Then strip the path, queries,and scheme, leaving only the host / domain
        assertEquals("www.test.please.work.gov.uk", actual)
    }

    @Test
    fun uriLearningTest() {
        // Given a URL string
        val url = "https://www.test.please.work.gov.uk/path/to/enlightenment?lang=en"
        // When breaking the `Uri` apart
        val actual = Uri.parse(url).toString()
        val actualScheme = Uri.parse(url).scheme
        val actualHost = Uri.parse(url).host
        val actualPath = Uri.parse(url).path
        val actualQueries = Uri.parse(url).query
        // Then everything is as expected
        assertEquals(url, actual)
        assertEquals("https", actualScheme)
        assertEquals("www.test.please.work.gov.uk", actualHost)
        assertEquals("/path/to/enlightenment", actualPath)
        assertEquals("lang=en", actualQueries)
    }
}
