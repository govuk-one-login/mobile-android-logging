## Developer setup

* Install [Android Studio] via the [Brew file]. Refer to the [Dependencies tutorial] for more
  information.

* Open this repository within [Android Studio]. This may require cloning the
  repository beforehand, if doing so within [Android Studio]'s UI isn't
  possible.

* Install Android SDK components within [Android Studio]'s `Tools` menu ->
  `SDK Manager`:

    * Within the `SDK Platform` category, download the version matching the
      `minSdk` property found within the [App Module's Gradle File].
    * Within the `SDK Tools` category, download:
        * The version of the `Android SDK Build-Tools` that matches the
          `buildToolsVersion` found within the [App Module's Gradle File].
        * The latest version of the `Android SDK Command-line Tools`.

* Optional: Add the `Android SDK Command-line Tools` to your terminal `PATH`.
  Run one of the proceeding commands, based on the Shell used, then reload the
  terminal or `source` the updated file:

    * Z shell (Zsh) Users:
      `echo 'PATH=$PATH:/path/to/android/sdk/cmdline-tools/latest/bin' >> ~/.zprofile`
    * BaSH Users:
      `echo 'PATH=$PATH:/path/to/android/sdk/cmdline-tools/latest/bin' >> ~/.bash_profile`
    * fish Users: `fish_add_path /path/to/android/sdk/cmdline-tools/latest/bin`

  Once complete, the `which sdkmanager` command should return the location for
  `sdkmanager`. Running the `yes | sdkmanager --licenses` command accepts all
  applicable licenses for Android.

* If there is no emulator devices configured, open the `Device Manager` bookmark
  that's hidden by default on the far right of [Android Studio]'s main window,
  underneath the `Gradle` bookmark, and create a new device. Please see below
  for the general area to look:

  ![The tab or bookmark to open the device manager][Android Studio Device Manager Bookmark]

* Within [Android Studio], run the `App` configuration at the top of the window,
  using an emulated device. Once the project builds, expand the `Emulator`
  bookmark found at the bottom far right by default, and then install the built
  app:

  ![Setting up the 'App' task to run, with an example emulated device][Android Studio Run Configuration location]

  ![Showing the default location of the 'Emulator' bookmark][Android Studio Emulator Bookmark]

[Android Studio]: https://developer.android.com/studio

[Android Studio Device Manager Bookmark]: /docs/img/androidStudioDeviceManagerBookmark.png

[Android Studio Emulator Bookmark]: /docs/img/androidStudioEmulatorBookmark.png

[Android Studio Run Configuration location]: /docs/img/androidStudioRunConfiguration.png

[App Module's Gradle File]: /app/build.gradle

[Brew file]: ../../Brewfile

[Dependencies tutorial]: ./dependencies.md