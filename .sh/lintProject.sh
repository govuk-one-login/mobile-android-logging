#!/usr/bin/env bash
# Exit immediately if a simple command exits with a non-zero status
# see also: the 'set' command within the 'Shell Builtin Commands' section of `man bash`
set -o errexit

# Applicable values:
# "unit" - Runs only the unit tests
# "instrumentation" - Runs only the instrumentation tests
# "both" - Runs both unit and instrumentation tests
# Any other value, including character casing differences, won't run the tests
TEST_RUNNER_MODE="$1"

# Don't provide this value if you want to omit the OWASP dependency checker.
# The condition only checks whether the value has a non-zero length.
SHOULD_RUN_DEPENDENCY_CHECKER="$2"

shellcheck .sh/*

./gradlew \
  :buildLogic:plugins:detekt \
  detekt \
  :buildLogic:plugins:ktlintCheck \
  ktlintCheck \
  vale

if [[ -n $SHOULD_RUN_DEPENDENCY_CHECKER ]]
then
  ./gradlew dependencyCheckAnalyze
fi

if [[ $TEST_RUNNER_MODE == "unit" ]]
then
  ./gradlew testBuildDebugUnitTest
elif [[ $TEST_RUNNER_MODE == "instrumentation" ]]
then
  ./gradlew googleAtdPixelXLApi30BuildDebugAndroidTest \
      googleAtdPixelXLApi33DevDebugAndroidTest \
      googleAtdPixelXLApi33StagingDebugAndroidTest \
      googleAtdPixelXLApi33IntegrationDebugAndroidTest \
      googleAtdPixelXLApi33ProductionDebugAndroidTest
elif [[ $TEST_RUNNER_MODE == "both" ]]
then
  ./gradlew googleAtdPixelXLApi30BuildDebugAndroidTest \
      googleAtdPixelXLApi33DevDebugAndroidTest \
      googleAtdPixelXLApi33StagingDebugAndroidTest \
      googleAtdPixelXLApi33IntegrationDebugAndroidTest \
      googleAtdPixelXLApi33ProductionDebugAndroidTest
      testBuildDebugUnitTest
fi