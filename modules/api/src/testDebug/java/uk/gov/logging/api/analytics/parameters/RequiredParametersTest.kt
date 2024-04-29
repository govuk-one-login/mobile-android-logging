package uk.gov.logging.api.analytics.parameters

import java.util.Locale
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID_VALUE
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY_VALUE
import uk.gov.logging.api.analytics.logging.LANGUAGE

internal class RequiredParametersTest {

    private val expectedMap = mutableMapOf(
        DIGITAL_IDENTITY_ID to DIGITAL_IDENTITY_ID_VALUE,
        DIGITAL_IDENTITY_JOURNEY to DIGITAL_IDENTITY_JOURNEY_VALUE,
        LANGUAGE to Locale.getDefault().language
    )

    @Test
    fun `Undefined DocumentType is skipped from result`() {
        val mapper = RequiredParameters(
            digitalIdentityJourney = "",
            journeyType = ""
        )

        assertEquals(
            expectedMap,
            mapper.asMap()
        )
    }
}
