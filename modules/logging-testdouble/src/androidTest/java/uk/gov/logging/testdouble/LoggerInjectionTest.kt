package uk.gov.logging.testdouble

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.api.Logger

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoggerInjectionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var crashLogger: CrashLogger

    @Inject
    lateinit var logger: Logger

    @Test
    fun verifySuccessfulInjection() {
        hiltRule.inject()

        assertTrue(
            "There should have been a successful CrashLogger injection!",
            crashLogger is FakeCrashLogger
        )

        assertTrue(
            "There should have been a successful Logger injection!",
            logger is SystemLogger
        )
    }
}
