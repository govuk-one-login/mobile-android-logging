package uk.gov.logging.robolectric

import android.util.Log
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ShadowLogFileTest {
    @get:Rule
    val shadowLogFile = ShadowLogFile("testFile")

    @Test
    fun `captures log output`() {
        Log.d("TAG", "test message")

        assertThat(shadowLogFile.readLines(), hasItem(containsString("test message")))
    }

    @Test
    fun `iterates over log lines`() {
        Log.i("TAG", "line1")
        Log.i("TAG", "line2")

        val lines = shadowLogFile.toList()
        assertThat(lines, hasItem(containsString("line1")))
        assertThat(lines, hasItem(containsString("line2")))
    }
}
