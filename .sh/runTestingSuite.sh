#!/usr/bin/env bash

./gradlew \
  testBuildDebugUnitTest \
  googleAtdPixelXLApi33BuildDebugAndroidTest \
  googleApisPlaystorePixelXLApi30DevDebugAndroidTest \
  googleApisPlaystorePixelXLApi30StagingDebugAndroidTest \
  googleApisPlaystorePixelXLApi30IntegrationDebugAndroidTest \
  googleApisPlaystorePixelXLApi30ProductionDebugAndroidTest \
  --continue \
  --stacktrace \
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
