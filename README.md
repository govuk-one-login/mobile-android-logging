# mobile-android-logging
An Android package usable for HTTP logging and passing analytics to a third-party SDK, also included is an abstraction class for using Google's Firebase analytics platform.

## Installation

To use this Logging package in a project using gradle dependencies:

1. Add the following line to the dependencies in your `build.gradle` file:

```kotlin
implementation(project(":modules:logging:api"))
implementation(project(":modules:logging:impl"))
implementation(project(":modules:logging:testdouble"))
```

