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

[Logging Module Packages]: https://github.com/orgs/govuk-one-login/packages?repo_name=mobile-android-logging
[Logging Module Tags]: https://github.com/govuk-one-login/mobile-android-logging/tags
[Gradle Version Catalogs]: https://developer.android.com/build/migrate-to-catalogs
