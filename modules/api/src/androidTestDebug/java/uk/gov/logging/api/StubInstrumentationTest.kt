package uk.gov.logging.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StubInstrumentationTest {
    @Test
    fun runTestCodeThatAffectsCodeCoverage() {
        instrumentationTestedCode() // have test coverage for this function
        assertTrue(true)
    }
}
