#!/usr/bin/env bash

PRODUCT_FLAVOR=$1 # Capitalised product flavor, such as "Build" or "Staging"
BUILD_TYPE=$2 # Capitalised build type, such as "Debug" or "Release"
SONAR_TOKEN=$3 # Sonar API token to authenticate the sending of analysis results

GIT_ROOT=$(git rev-parse --show-toplevel)

"${GIT_ROOT}"/gradlew \
  "combined${BUILD_TYPE}JacocoTestReport" \
  "combined${PRODUCT_FLAVOR}${BUILD_TYPE}JacocoTestReport" \
  "connected${BUILD_TYPE}AndroidTest" \
  "connected${PRODUCT_FLAVOR}${BUILD_TYPE}AndroidTest" \
  "dependencyCheckAnalyze" \
  "detekt" \
  "ktlintCheck" \
  "lint${BUILD_TYPE}" \
  "lint${PRODUCT_FLAVOR}${BUILD_TYPE}" \
  "shellcheck" \
  "test${BUILD_TYPE}UnitTest" \
  "test${PRODUCT_FLAVOR}${BUILD_TYPE}UnitTest"

"${GIT_ROOT}"/gradlew sonarqube \
  -Dsonar.host.url="http://localhost:9000" \
  -Dsonar.token="${SONAR_TOKEN}"