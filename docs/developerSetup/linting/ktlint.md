# Ktlint

The [ktlint Tool] handles code style within the project. The main Gradle tasks
Developers use are:

- `./gradlew ktlintCheck`: Runs the ktlint linter across the project.

- `./gradlew ktlintFormat`: Attempts to automatically resolve issues found by
  the `ktlintCheck`
  command.

Please see the [ktlint Project Configuration] for more information.

### Common ktlint problems

- If `ktlint` fails to parse a file, the most notable reason for this is that
  there is a trailing comma somewhere within the file.

[ktlint Tool]: https://ktlint.github.io/

[ktlint Project Configuration]: /config/ktlint/config.gradle
