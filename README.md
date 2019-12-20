# WormProgressBar

A worm like animation of a progress bar in Android

[![](https://jitpack.io/v/trisl/WormProgressBar.svg)](https://jitpack.io/#trisl/WormProgressBar)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

![Worm](gifs/worm.gif)

## Usage
- Add view into your layout file
```xml
<com.tristanroussel.worm.WormProgressBar
    android:id="@+id/worm_view"
    android:layout_width="match_parent"
    android:layout_height="10dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintTop_toTopOf="parent"/>
```

- Once in your view file, init the animation
```kotlin
wormProgressBar.initAnimation()
```

- You can now start the animation ...

```kotlin
wormProgressBar.start()
```

- ... pause it ...

```kotlin
wormProgressBar.pause()
```

- ... Or complete it with an animation or not

```kotlin
wormProgressBar.complete(animating = true)
```

## Configuration
You can configure the duration and interpolator of the animation. By default, the duration is 500ms and has an AccelerateDecelerateInterpolator.
```kotlin
wormProgressBar.initAnimation(
    WormAnimationConfiguration(
        duration = 1000L,
        interpolator = AccelerateInterpolator()
    )
)
```

## Setup
- Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

- Add the dependency
```
dependencies {
    implementation 'com.github.trisl:WormProgressBar:1.0.0'
}
```

## License
MIT License

Copyright (c) 2019 Tristan Roussel

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.