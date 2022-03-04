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
In order to utilise the core AndroidUi module in a project, add the following dependency to your `build.gradle` file:
```groovy
    //TODO
```
To utilise the Fragment module, add the following dependency:
```groovy
    //TODO
```
To utilise the Activity module, add the following dependency:
```groovy
    //TODO
```
[JitPack](https://jitpack.io/) is used to provide the Fusion artifacts.

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
