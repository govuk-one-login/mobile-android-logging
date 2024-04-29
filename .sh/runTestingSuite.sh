#!/usr/bin/env bash

./gradlew \
  testBuildDebugUnitTest \
  googleAtdPixelXLApi33BuildDebugAndroidTest \
  googleAtdPixelXLApi33DevDebugAndroidTest \
  googleAtdPixelXLApi33StagingDebugAndroidTest \
  googleAtdPixelXLApi33IntegrationDebugAndroidTest \
  googleAtdPixelXLApi33ProductionDebugAndroidTest \
  --continue \
  --stacktrace \
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
