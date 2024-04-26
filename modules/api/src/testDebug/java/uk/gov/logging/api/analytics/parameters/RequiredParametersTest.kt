package uk.gov.logging.api.analytics.parameters

import java.util.Locale
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE
import uk.gov.documentchecking.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.documentchecking.repositories.api.webhandover.documenttype.DocumentType.UNDEFINED
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_ID_VALUE
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY
import uk.gov.logging.api.analytics.logging.DIGITAL_IDENTITY_JOURNEY_VALUE
import uk.gov.logging.api.analytics.logging.DOCUMENT_TYPE_JOURNEY_KEY
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
            document = UNDEFINED
        )

        assertEquals(
            expectedMap,
            mapper.asMap()
        )
    }

    @ParameterizedTest
    @EnumSource(
        value = DocumentType::class,
        names = ["UNDEFINED"],
        mode = EXCLUDE
    )
    fun `Journey type is included from known Document types`(documentType: DocumentType) {
        expectedMap[DOCUMENT_TYPE_JOURNEY_KEY] = documentType.journeyType

        val mapper = RequiredParameters(
            document = documentType
        )

        assertEquals(
            expectedMap,
            mapper.asMap()
        )
    }
}
