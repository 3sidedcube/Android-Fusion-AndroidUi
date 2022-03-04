# Android-Fusion-AndroidUi
## Fusion library for Android: AndroidUi module
Fusion: Flexible JSON driven UI rendering

### What is Fusion?
Fusion is a library designed to take JSON data representing UI, and render that data into the described UI.
The JSON data must match the Fusion JSON schema in order to render correctly.
This is part of the Fusion library for Android - there also exists a [Fusion library for iOS](https://github.com/3sidedcube/Fusion), so that Fusion can be used to build cross-platform UI.

### What is the AndroidUi module for?
The AndroidUi module provides implementations for all of the Fusion views using the native Android RecyclerView ViewHolder pattern.
It also provides Fragment and Activity abstract classes / implementations for easily including Fusion content in your app.
Finally, a demo app is included, to demonstrate how a Fusion app could be set up.

## Installation
[JitPack](https://jitpack.io/) is used to provide the Fusion artifacts.
In order to utilise the library in a project, update your `settings.gradle` (or root project `build.gradle` on older projects) to include the Jitpack maven repository:
```groovy
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```
Then, in order to utilise the core AndroidUi module in a project, add the following dependency to your `build.gradle` file:
```groovy
    implementation 'com.github.3sidedcube.Android-Fusion-AndroidUi:core:{versionCode}'
```
To utilise the Fragment module, add the following dependency:
```groovy
    implementation 'com.github.3sidedcube.Android-Fusion-AndroidUi:fragment:{versionCode}'
```
To utilise the Activity module, add the following dependency:
```groovy
    implementation 'com.github.3sidedcube.Android-Fusion-AndroidUi:activity:{versionCode}'
```
If you wish to utilise all modules in this repo, you can alternatively add the following dependency:
```groovy
    implementation 'com.github.3sidedcube:Android-Fusion-AndroidUi:{versionCode}'
```
As these builds are provided using Jitpack, `{versionCode}` can be replaced with:

- A specific commit, e.g `1a2b3c4d5e`
- A specific branch's latest build, e.g `feature~jitpack-setup-SNAPSHOT`
- A specific pre-release tag, e.g `1.0.0-rc1`
- A specific release tag, e.g `1.0.0`

It is recommended that you use this library at a specific release tag, to ensure that the library is in a stable state.

## Usage
See the demo app to get an impression of the behaviour, or see the Wiki.
In order to run the demo app, you will need to update the constants `DEMO_URL` and `START_SCREEN` in `MainActivity.kt` to point to your JSON data source / API.

## FAQ
### Why is this module named "AndroidUi" rather than simply "Ui"?
Whilst this day may never come, we foresee a future Jetpack Compose (or some other UI framework) may become production ready.
If this happens, Fusion UI libraries for the alternative UI frameworks may be built.
Therefore, this module is named "AndroidUi" to denote that it is using Android's native View / RecyclerView frameworks.

### What motivated the name "Fusion"?
Fusion here means to bring together two platforms, iOS and Android, powered by a single server-side API.
For web-server apps, the business logic should sit server-side as much as possible. Fusion aims to be as simple as "server tell me what to draw and I'll draw it".
