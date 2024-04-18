package uk.gov.logging.api.fake

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InternalTest {
    @Test
    fun testAssertTrue() {
        assertTrue(true)
    }

    @Test
    fun testAssertFalse() {
        assertFalse(false)
    }
}
