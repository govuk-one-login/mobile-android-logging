#!/usr/bin/env bash

./gradlew \
  testBuildDebugUnitTest \
  googleAtdPixelXLApi33BuildDebugAndroidTest \
  --continue \
  --stacktrace \
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
