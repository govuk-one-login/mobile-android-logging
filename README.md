# mobile-android-logging

An Android package usable for HTTP logging and passing analytics to a third-party SDK. Also included
is an abstraction class for using Google's Firebase analytics platform.

## Functionality provided by Logging module

The main functionality of the logging module is to provide a single source of truth for standardised
code logging functionality that makes it simple to switch logging framework if desired.

The three packages have the following functionality:

1. The `:api` module contains logger interfaces.
2. The `:impl` module contains logger implementations and their associated Hilt modules for dependency injection into their constructors.
3. The `:testdouble` module contains fake logger implementations for testing and their Hilt modules for dependency injection into their constructors.

## Getting started with Logging module dependencies

### Without a version catalog

In your `build.gradle.kts` files, for each module (and for each build flavor), add dependencies
needed for example:

```kotlin
// Refer to the uploaded packages for the latest version:
// https://github.com/orgs/govuk-one-login/packages?repo_name=mobile-android-logging
val loggingLibraryVersion: String by rootProject.extra("1.2.3")

// Both `impl` and `testdouble` artifacts provide the `api` module as a gradle `api` dependency.
implementation("uk.gov.logging:logging-impl:$loggingLibraryVersion")
testImplementation("uk.gov.logging:logging-testdouble:$loggingLibraryVersion")
androidTestImplementation("uk.gov.logging:logging-testdouble:$loggingLibraryVersion")
```

### With a version catalog (recommended)

To migrate to using version catalogs, see [Gradle Version Catalogs].

In your root `./gradle/libs.versions.toml` file:

```toml
[versions]
gov-logging = "1.2.3" # https://github.com/orgs/govuk-one-login/packages?repo_name=mobile-android-logging

[libraries]
uk-gov-logging-api = { module = "uk.gov.logging:logging-api", version.ref = "gov-logging"}
uk-gov-logging-impl = { module = "uk.gov.logging:logging-impl", version.ref = "gov-logging"}
uk-gov-logging-testdouble = { module = "uk.gov.logging:logging-testdouble", version.ref = "gov-logging"}
```

Then in your `build.gradle.kts` files:

```kotlin
// Both `impl` and `testdouble` artifacts provide the `api` module as a gradle `api` dependency.
implementation(libs.uk.gov.logging.impl)
testImplementation(libs.uk.gov.logging.testdouble)
androidTestImplementation(libs.uk.gov.logging.testdouble)
```

## Hilt Configuration

Despite there being usable Hilt modules in the `logging` module, these are mainly for
Android Logging. You should also create your own `Singleton` scoped Hilt modules to provide the
specific analytics logger implementation that you need, and also to provide the Firebase analytics
implementation. For example:

```kotlin
@InstallIn(SingletonComponent::class)
@Module
object AnalyticsSingletonModule {
    @Provides
    @Singleton
    fun providesAnalyticsLogger(
        analyticsLogger: FirebaseAnalyticsLogger
    ): AnalyticsLogger = MemorisedAnalyticsLogger(analyticsLogger)
}

@InstallIn(SingletonComponent::class)
@Module
class FirebaseSingletonModule {
    @Provides
    @Singleton
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
}
```

## Releases

The packages can be found at here at [Logging Module Packages].

The tags can be found at here at [Logging Module Tags].

## GA4 Migration Guide
There has been a breaking change on some of the required parameters. We support both the original
and the new GA4. However you will need to import the correct type based on your need.
```kotlin
// GA4 type
import uk.gov.logging.api.analytics.parameters.v2.RequiredParameters
```
```kotlin
// Original type
import uk.gov.logging.api.analytics.parameters.RequiredParameters
```

If you need to use both GA4 and the original one in the same file, you will need to import 
as an alias
```kotlin
import uk.gov.logging.api.analytics.parameters.RequiredParameters
import uk.gov.logging.api.analytics.parameters.v2.RequiredParameters as RequiredParametersV2
```
# Legacy 
[Implementation Guide](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3543171117/Google+Analytics+Implementation+Guide)
[One Login Mobile Application Data Schema V1.0](https://govukverify.atlassian.net/wiki/spaces/PI/pages/3790995627/GA4+One+Login+Mobile+Application+Data+Schema+V1.0+amended+to+V1.1#Tracked-Events).

# GA4
* See [GA4 | One Login Mobile Application Data Schema V3.1](https://govukverify.atlassian.net/wiki/x/qwD24Q)

[Logging Module Packages]: https://github.com/orgs/govuk-one-login/packages?repo_name=mobile-android-logging
[Logging Module Tags]: https://github.com/govuk-one-login/mobile-android-logging/tags
[Gradle Version Catalogs]: https://developer.android.com/build/migrate-to-catalogs

## Updating gradle-wrapper

Gradle SHA pinning is in place through the `distributionSha256Sum` attribute in gradle-wrapper.properties. This means the gradle-wrapper must be upgraded properly through the `./gradlew wrapper` command.
Example gradle-wrapper.properties
```
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionSha256Sum=2db75c40782f5e8ba1fc278a5574bab070adccb2d21ca5a6e5ed840888448046
distributionUrl=https\://services.gradle.org/distributions/gradle-8.10.2-bin.zip
networkTimeout=10000
validateDistributionUrl=true
 ```

Use the following command to update the gradle wrapper. Run the same command twice, [reason](https://sp4ghetticode.medium.com/the-elephant-in-the-room-how-to-update-gradle-in-your-android-project-correctly-09154fe3d47b).

```bash
./gradlew wrapper --gradle-version=8.10.2 --distribution-type=bin --gradle-distribution-sha256-sum=31c55713e40233a8303827ceb42ca48a47267a0ad4bab9177123121e71524c26
```

Flags:
- `gradle-version` self explanatory
- `distribution-type` set to `bin` short for binary refers to the gradle bin, often in this format `gradle-8.10.2-bin.zip`
- `gradle-distribution-sha256-sum` the SHA 256 checksum from this [page](https://gradle.org/release-checksums/), pick the binary checksum for the version used

The gradle wrapper update can include:
- gradle-wrapper.jar
- gradle-wrapper.properties
- gradlew
- gradlew.bat

You can use the following command to check the SHA 256 checksum of a file

```bash
shasum -a 256 gradle-8.10.2-bin.zip
```
