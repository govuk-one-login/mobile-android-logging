#!/usr/bin/env bash

./gradlew \
  testDebugUnitTest \
  googleAtdPixelXLApi33DebugAndroidTest \
  --continue \
  --stacktrace \
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
