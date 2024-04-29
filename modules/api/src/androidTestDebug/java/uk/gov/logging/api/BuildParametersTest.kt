package uk.gov.logging.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.logging.api.test.TestApplicationVariantFilter.Companion.Flavor

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BuildParametersTest {
    @Test
    fun nameSpaceIsSetCorrectly() {
        assertEquals(
            "The NAME_SPACE property for ${BuildConfig.FLAVOR} is not set correctly!",
            expectedBuildParameters[BuildConfig.FLAVOR]!!["NAME_SPACE"],
            BuildConfig.NAME_SPACE
        )
    }

    private val expectedBuildParameters = mapOf(
        Flavor.BUILD to mapOf(
            "NAME_SPACE" to "uk.gov.logging.build.api"
        ),
        Flavor.DEV to mapOf(
            "NAME_SPACE" to "uk.gov.logging.dev.api"
        ),
        Flavor.STAGING to mapOf(
            "NAME_SPACE" to "uk.gov.logging.staging.api"
        ),
        Flavor.INTEGRATION to mapOf(
            "NAME_SPACE" to "uk.gov.logging.integration.api"
        ),
        Flavor.PRODUCTION to mapOf(
            "NAME_SPACE" to "uk.gov.logging.api"
        )
    )
}
