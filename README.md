# Mvvm-architecture-koin with rx

[![](https://jitpack.io/v/cuongnv219/mvvm-rx.svg)](https://jitpack.io/#cuongnv219/mvvm-rx) [![Build Status]
(https://travis-ci.org/cuongnv219/mvvm-rx.svg?branch=master)](https://travis-ci.org/cuongnv219/mvvm-rx) [![Codacy
Badge](https://api.codacy.com/project/badge/Grade/76a3276b749e4be7bfb39c56314b11d3)](https://www.codacy
.com/app/cuongnv219/mvvm-rx?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cuongnv219/mvvm-rx&amp;
utm_campaign=Badge_Grade)

Sample base mvvm with rx
This repository contains a detailed sample app that implements MVVM architecture using Koin, Room, RxJava, FastAndroidNetworking...

### Requirements
* SDK 16 and and higher

### Install
Download via **Gradle**:

Add this to the **project `build.gradle`** file:
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

And then add the dependency to the **module `build.gradle`** file:
```gradle
implementation 'com.github.cuongnv219:mvvm-rx:latest_version'
```

Download via **Maven**:
```
<dependency>
  <groupId>com.github.cuongnv219</groupId>
  <artifactId>mvvm-rx</artifactId>
  <version>latest_version</version>
  <type>pom</type>
</dependency>
```

### License
```
Copyright (C) 2018

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
