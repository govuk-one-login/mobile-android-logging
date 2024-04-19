# mobile-android-logging
An Android package usable for HTTP logging and passing analytics to a third-party SDK, also included is an abstraction class for using Google's Firebase analytics platform.

## Installation

To use this Logging package in a project using gradle dependencies:

Add the following line to the dependencies in your `buildLogic.plugins.build.gradle` file:

```kotlin
implementation(project(":modules:logging:api"))
implementation(project(":modules:logging:impl"))
implementation(project(":modules:logging:testdouble"))
```

## Running the code
*Complete the [Running the app and tests within the code base] tutorial.

# Running the Codebase
[Running the app and tests within the code base]: docs/developerSetup/runningTheCodeBase.md