# Smart Rate

[![Build Status](https://travis-ci.com/RankoR/android-smart-rate.svg?branch=master)](https://travis-ci.com/RankoR/android-smart-rate) [ ![Download](https://api.bintray.com/packages/rankor/maven/android-smart-rate/images/download.svg) ](https://bintray.com/rankor/maven/android-smart-rate/_latestVersion)

[Smart rate](https://github.com/RankoR/android-smart-rate) is a library that helps you improve your app's rating and get user feedback, inspired by [Smart App Rate](https://github.com/codemybrainsout/smart-app-rate) library.

![](screenshots/screenshot.png)

## Demo

See demo app on Google Play

[![](screenshots/gp.svg)](https://play.google.com/store/apps/details?id=com.g2pdev.smartrate.demo)

## Features

- Auto-fetch app icon to display it in the dialog
- Appears after the specified session count
- Redirects to app store (see below), if the given rating is greater then minimal value, specified by you
- Shows feedback form (with callback to your code), if rating is below then specified
- Supports multiple app stores:
  - Google Play
  - Amazon
  - Xiaomi (currently link is duplicate of Google Play)
  - Samsung
  - Aptoide (temporary disabled)
  - Custom link
- Prompts for rating later after given amount of sessions (if user clicked «Later»)
- Customizable texts (all!). Two default translations provided — English and Russian.
- Customizable text and buttons colors
- Size is about ~290 KB

## Limitations

- Minimum SDK version is 21
- App must use [Material Components](https://material.io/develop/android/docs/getting-started/) theme

## Installation

Add dependency to your module's `build.gradle` and sync:

```groovy
implementation 'com.g2pdev:smartrate:(insert latest version)'
```

## Set up

### Initialize library

In your `Application.onCreate()` method call:

```kotlin
SmartRate.init(this)
```

For example:

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        SmartRate.init(this)    
    }
}

```
 
**Note:** calling `SmartRate.init()` with context other than `Application` will cause exception.

### Show dialog

#### With default config

In your `Activity.onCreate()` call `SmartRate.show(this)`:

```kotlin
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        SmartRate.show(this)
    }
}
```

See values of default config in [Config section](#config).

#### With custom config

Call method `SmartRate.show(this, config)`:

```kotlin
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        SmartRate.show(this)
    }
}
```

See config section [below](#config).

## Config

Configuration is represented by class [`SmartRateConfig`](/library/src/main/java/com/g2pdev/smartrate/logic/model/SmartRateConfig.kt).

| Field  | Type | Description | Default value |
|---|---|---|---|
| `minSessionCount` | `Int` | Minimum session count before showing the dialog | `3` |
| `minSessionCountBetweenPrompts` | `Int` | Minimum session count before showing the dialog after user clicks «Later» | `3` |
| `minRatingForStore` | `Float` | Minimum rating to redirect user to app store. If user rates app lower, it will show the «Feedback» dialog  | `4.0f` |
| `iconDrawableResId` | `Int?` | Icon drawable to override default (default is app icon retrieved automatically) | `null` |
| `titleResId` | `Int` | Rate dialog title | `R.string.sr_title` |
| `rateNeverAskResId` | `Int` | «Never ask» button title | `R.string.sr_never_ask` |
| `rateLaterResId` | `Int` | «Maybe later» button title | `R.string.sr_maybe_later` |
| `rateTitleTextColorResId` | `Int` | Rate dialog title text color | `R.color.rateTitleText` |
| `rateNeverAskButtonTextColorResId` | `Int` | «Never ask» button text color | `R.color.rateNeverButtonText` |
| `rateLaterButtonTextColorResId` | `Int?` | «Maybe later» button text color | `null` (will use `colorAccent` of your app |
| `feedbackTitleResId` | `Int` | Feedback dialog title | `R.string.sr_title_feedback` |
| `feedbackHintResId` | `Int` | Feedback text field hint | `R.string.sr_hint_feedback` |
| `feedbackCancelResId` | `Int` | Feedback «Cancel» button text | `R.string.sr_feedback_cancel` |
| `feedbackSubmitResId` | `Int` | Feedback «Submit» button text | `R.string.sr_feedback_submit` |
| `feedbackTitleTextColorResId` | `Int` | Feedback dialog title text color | `R.color.feedbackTitleText` |
| `feedbackCancelButtonTextColorResId` | `Int` | Feedback «Cancel» button text color | `R.color.feedbackCancelButtonText` |
| `feedbackSubmitButtonTextColorResId` | `Int` | Feedback «Submit» button text color | `null` (will use `colorAccent` of your app |
| `isRateDismissible` | `Boolean` | Is rate dialog dismissible by clicking «back» button or outside? | `false` |
| `store` | `Store` | Store where to redirect user to rate app. Ignored if `customStoreLink` is set. | `Store.GOOGLE_PLAY` |
| `customStoreLink` | `String?` | Custom link where to redirect user to rate app | `null` |
| `showFeedbackDialog` | `Boolean` | Show feedback dialog if rating is below then `minRatingForStore`?  | `true` |
| `isFeedbackDismissible` | `Boolean` | Is feedback dialog dismissible by clicking «back» button or outside?  | `false` |

Listeners are also located in the config:

| Field  | Type | Description | Default value |
|---|---|---|---|
| `onRateDialogShowListener` | `(() -> Unit)?` | Called when rate dialog is shown | `null` |
| `onRateDismissListener` | `(() -> Unit)?` | Called when rate dialog is dismissed (by clicking «Back» button or outside) | `null` |
| `onRateListener` | `((rating: Float) -> Unit)?` | Called when user rated app | `null` |
| `onNeverAskClickListener` | `(() -> Unit)?` | Called when user clicks «Never ask» button | `null` |
| `onLaterClickListener` | `(() -> Unit)?` | Called when user clicks «Maybe later» button | `null` |
| `onFeedbackDismissListener` | `(() -> Unit)?` |  Called when feedback dialog is dismissed (by clicking «Back» button or outside) | `null` |
| `onFeedbackCancelClickListener` | `(() -> Unit)?` | Called when user clicks «Cancel» button in feedback dialog | `null` |
| `onFeedbackSubmitClickListener` | `((text: String) -> Unit)?` | Called when user clicks «Submit» button in feedback dialog | `null` |

Configuring is pretty easy — just create the default config and then modify it using, for example [`apply()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/apply.html):

```kotlin
val config = SmartRateConfig()
            .apply {
                minSessionCount = 10
                minRatingForStore = 3.5f
            }
           
// ...
```

## Proguard

Not needed — rules are included in library.

## TODO

- Check for minimum text length in the feedback dialog
- UI Tests
- Test Xiaomi app store
- Add Aptoide
- Refactor config internals?

## Internals

This library uses:

- [Kotlin](https://kotlinlang.org/)
- [Dagger 2](https://github.com/google/dagger) for dependency injection
- [RxJava 2](https://github.com/ReactiveX/RxJava) for Reactive programming
- [Moxy](https://github.com/moxy-community/Moxy) for MVP implementation
- [Gson](https://github.com/google/gson) for cache values serialization
- [Timber](https://github.com/JakeWharton/timber) for logging (disabled in production)
- [LeakCanary](https://github.com/square/leakcanary) for leaks detection (disabled in production)

I believe it's implemented following the Clean Architecture principles.
This implementation probably seems over-engineered for such simple task, but, on the other hand, it's pretty testable and extensible.

All interactors, repository and some caches are covered by tests (see [tests](library/src/androidTest/java/com/g2pdev/smartrate/))

## Contributions

Any contributions, including translations, are highly appreciated.

Please, report any issues [here](https://github.com/RankoR/android-smart-rate/issues).

## Credits

This library is inspired by [Smart App Rate](https://github.com/codemybrainsout/smart-app-rate).

Author: [Artem Smirnov](https://github.com/RankoR).

## License

```
Copyright (C) 2020 Artem Smirnov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```