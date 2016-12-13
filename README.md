# My project's Small Chat


## Index
1. Project setup
1. Project build
1. Project example like simple add functionality for chatting
1. Instructions on how to install on phone
1. Instructions how to add view


##Overview

This document has finality to expose all needs about the small chat project to build and run.



##Required

Assumptions or please install the follow tools:

* JDK 1.8
* [Gradle](https://gradle.org/)
* [Android Studio](https://developer.android.com/studio/index.html)


##Project

It’s a simple project that support an small chat, between the user and a bot. In this case the bot only respond few questions.


###1.Setup

In order to setup de project, we should have Android Studio as IDE. This is an excellent tool, and also it’s being suggested from Google company, who provide the majority API's and development codes.

1. a. Import project from clone a git repository;
1. b. Import project from file;


###2. Build

*  Build your apk(unsigned) file with gradle from commandLine

Execute the following commands from the main project directory:

`./gradlew clean build`

Build project complete from scratch

* Build your apk(unsigned) file with gradle from Android Studio, follow the steps:

1. Click the dropdown menu in the toolbar at the top
1. Select "Edit Configurations"
1. Click the "+"
1. Select "Gradle"
1. Choose your module as Gradle project
1. In Tasks: enter assemble
1. Press Run

Your unsigned apk is now located in ProjectName\app\build\outputs\apk


###3.Project Example
WIP 


###4.Instructions how to add activity & View

[Starting Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)



###5.Instructions on how to install on phone



### References:
[Android Training](https://developer.android.com/training/basics/firstapp/starting-activity.html)


###
I choice Android api 18, Jelly Bean, because around the %70 of device support this API.