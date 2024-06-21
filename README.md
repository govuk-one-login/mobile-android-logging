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
needed e.g.:

```kotlin
implementation("uk.gov.logging:logging-api") {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
flavorImplementation("uk.gov.logging:logging-impl") {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
testImplementation("uk.gov.logging:logging-testdouble") {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
androidTestImplementation("uk.gov.logging:logging-testdouble") {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
```

### With a version catalog (recommended)

To migrate to using version catalogs, see [Gradle Version Catalogs].

In your root `./gradle/libs.versions.toml` file:

```toml
[versions]
gov-logging = "0.4.2"

[libraries]
gov-logging-api = { group = "uk.gov.logging", name = "logging-api", version.ref = "gov-logging"}
gov-logging-impl = { group = "uk.gov.logging", name = "logging-impl", version.ref = "gov-logging"}
gov-logging-testdouble = { group = "uk.gov.logging", name = "logging-testdouble", version.ref = "gov-logging"}
```

Then in your `build.gradle.kts` files:

```kotlin
implementation(libs.gov.logging.api) {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
flavorImplementation(libs.gov.logging.impl) {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
testImplementation(libs.gov.logging.testdouble) {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
androidTestImplementation(libs.gov.logging.testdouble) {
    artifact {
        classifier = "release"
        type = "aar"
    }
}
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
