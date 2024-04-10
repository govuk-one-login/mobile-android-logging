## Logging-API

1. To add Logs in your implementation import:

```kotlin
import uk.gov.logging.api.Logger
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.CrashLogger
```

2. Initialise `Logger`, `CrashLogger` and `LogTagProvider`, and use them appropriately based on your usecase.

```kotlin
   class MyClass(
    private val logger: Logger,
    private val crashLogger: CrashLogger,
) : LogTagProvider {

    fun myFunction(event: String) {
        logger.debug(tag, event)
    }

    fun myInfoFunction(info: String) {
        logger.info(tag, info)
    }

    fun myErrorFunction(errorMessage: String) {
        logger.error(tag, errorMessage)
    }

    fun myErrorThrowable(errorMessage: String, throwable: Throwable) {
        logger.error(tag, errorMessage, throwable)
    }

    fun uncaughtException(exception: Throwable) {
        crashLogger.log(exception)
    }

    fun logCrashError(msg: String) {
        crashLogger.log(msg)
    }
}

```
