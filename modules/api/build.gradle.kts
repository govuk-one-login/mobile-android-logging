import uk.gov.logging.config.ApkConfig

plugins {
    id("uk.gov.logging.android-lib-config")
}

android {
    namespace = ApkConfig.APPLICATION_ID + ".api"
}

dependencies {
    listOf(
        libs.androidx.test.core.ktx,
        libs.androidx.test.ext.junit.ktx,
        libs.androidx.test.runner,
        libs.androidx.test.orchestrator
    ).forEach {
        androidTestImplementation(it)
    }

    androidTestUtil(libs.androidx.test.orchestrator)

    listOf(
        libs.junit
    ).forEach { dependency ->
        androidTestImplementation(dependency)
        testImplementation(dependency)
    }
}
