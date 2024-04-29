package uk.gov.logging.api.test

/**
 * Annotation used to filter the unit test that should be running
 * according to the flavors and build types given on it
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestApplicationVariantFilter(
    val flavors: Array<String> = [Flavor.BUILD],
    val notFlavors: Array<String> = [],
    val buildTypes: Array<String> = [BuildType.DEBUG],
    val notBuildTypes: Array<String> = []
) {
    companion object {
        object Flavor {
            const val DEV = "dev"
            const val BUILD = "build"
            const val STAGING = "staging"
            const val INTEGRATION = "integration"
            const val PRODUCTION = "production"
        }

        object BuildType {
            const val DEBUG = "debug"
            const val RELEASE = "release"
        }
    }
}
