# Unit Converter M3E (Android)

A clean Android unit converter built with Kotlin and Jetpack Compose, following a Material 3 expressive visual style.

## Project Goals

- Provide a simple and reliable length unit converter.
- Keep the project easy to clone and open in the latest Android Studio.
- Maintain text-only source files in Git to avoid binary file issues in pull requests.
- Keep commit history clear and logically grouped.

## Features

- Convert between millimeter, centimeter, meter, kilometer, inch, and foot.
- Input validation with clear error messaging.
- Modern Compose UI using Material 3 components.
- Dynamic color support on Android 12+.

## Tech Stack

- Kotlin
- Android Gradle Plugin 8.7.3
- Jetpack Compose
- Material 3
- Min SDK 24, Target SDK 35

## Open in Android Studio

1. Clone the repository.
2. Open the project root in Android Studio (latest stable version).
3. Allow Gradle sync to complete.
4. Run the `app` configuration on an emulator or device.

## Build from Command Line

This repository intentionally avoids committing binary artifacts. To build locally, use a local Gradle installation:

```bash
gradle :app:assembleDebug
```

If you prefer Gradle Wrapper, generate it locally after cloning:

```bash
gradle wrapper --gradle-version 8.14.3
```

## Repository Hygiene

- No generated APK/AAB outputs are tracked.
- No IDE metadata is tracked.
- No binary files are required for source review.

## Package Name

`com.takumi.unitconverter`
