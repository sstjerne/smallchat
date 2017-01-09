# My project's Small Chat


## Index
1. Project setup
1. Project build
1. Project example like simple add functionality for chatting
1. Instructions on how to install on phone
1. Instructions how to add view
1. Security Server OAuth2
1. References


##Overview

This document has finality to expose all needs about the small chat project to build and run.



##Required

Assumptions or please install the follow tools:

* JDK 1.8
* [Gradle](https://gradle.org/)
* [Android Studio](https://developer.android.com/studio/index.html)
* [Tomcat 8](https://tomcat.apache.org/index.html)
* [Mysql Server](https://dev.mysql.com/downloads/mysql/5.6.html)

##Project

It’s a simple project that support an small chat, between the user and a bot. In this case the bot only respond few questions. Also the application interactive with a REST API to login and register.


###1.Setup

In order to setup de project, we should have Android Studio as IDE. This is an excellent tool, and also it’s being suggested from Google company, who provide the majority API's and development codes.

1. a. Import project from clone a git repository;
1. b. Import project from file;


###2. Build Android application

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


###2. Build Java application - a2r2

*  Build your war file with mvn from commandLine

Execute the following commands from the main project directory:

`cd a2r2`

`mvn clean package`

Your war file is now located in target folder

* Deploy in Tomcat

Check that Tomcat is running and then copy the war file to webapp folder into it:

`cp target/*.war {TOMCAT_HOME}/webapps/`

 [TODO] README.md into a2r2 folder constain more information regarding to REST API.

* Check application is running 

** Register a user

`curl 'http://localhost:8080/a2r2-API-REST/user' -XPOST -H 'Authorization: Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0' -H 'Accept: application/json' -H 'Content-Type: application/json' -H 'Connection: keep-alive' --data '{"authorities":["USER"],"name":"test1","password":"123456","surname":"gmail","username":"test1@gmail.com"}' --compressed`

** Get Access Token

`curl -XPOST "http://localhost:8080/a2r2-API-REST/oauth/token?grant_type=password&username=seba&password=123456" -H "Authorization: Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0" -H "Accept: application/json"`

** Get User

`curl -XGET "http://localhost:8080/a2r2-API-REST/user/seba?access_token={access_token}" -H "Authorization: Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0" -H "Accept: application/json"`

** Get All Users
`curl -XGET "http://localhost:8080/a2r2-API-REST/user/?access_token=00bf6f37-83a4-4a42-82d1-bf0624cc4b24" -H "Authorization: Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0" -H "Accept: application/json"`


###4.Project Example

Android chat with an dummy artificial intelligence, just contains a small list of static word to interactive. The bot start with "Hi" greeting.

#####How are they implemented?
The android app constain a simple activity where the expose the chat, and there is an simple service that respond the bot messages and also there is the text input to be used for user. 
Mainly the app works contains local broadcast to manage messages between the different component of application, and there is a adapter to interpret it and know if it should be show as bot message or user message. This local broadcast just receive message from the same process by a service.
The java applicacion is a simple REST API, that expose a few services to login, it contains spring-security and uses oauth2 approach to login. The application store in Mysql DB.


###5.Instructions how to add activity & View

* Creates the class XXXActivity.java with an implementation of the required onCreate() method, thats generally extends of AppCompatActivity class.
* Creates the corresponding layout file activity_xxxx.xml
* Adds the required <activity> element in AndroidManifest.xml.



###6.Instructions on how to install on phone


####Install apps outside of Google Play:
Android devices have the ability to "sideload" applications that aren't available on the Google Play store. What should we do:

#####Setting up your device

From your smartphone, go to Settings, scroll down to Security, and select Unknown sources. Selecting this option will allow you to install apps outside of the Google Play store. Depending on your device, you can also choose to be warned before installing harmful apps. This can be enabled by selecting the Verify apps option in the Security settings.On devices running an earlier version of Android, go to Settings, open the Applications option, select Unknown sources, and click OK on the popup aler

#####Downloading an app

The next step will be finding an Android package file, also known as an APK, which is the way Android apps are distributed and installed. 
You can either download the APK file on your mobile device or on your computer. 

To get started, download an APK file using either Google Chrome or the stock Android browser. Next, go to your app drawer and click Downloads; here you will find the file you just downloaded. Open the file and install the app.

If you downloaded the APK file on your computer, the process is slightly different. You must connect your Android device to the PC and enable USB mass-storage mode. The next step is to drag and drop the file onto your device. Then, using a file manager, such as Astro or ES File Explorer, you can locate the file on your device and install it.


####Install apps from Google Play: Publish Google Play

You have to have account and Google and then after signed up for a Google Play Developer account, you can upload apps to Google Play using your Google Play Developer Console.

1. Go to your Google Play Developer Console.
1. Select All applications All applications > Add new application.
1. Using the drop down menu, select a default language and add a title for your app. Type the name of your app as you want it to appear in Google Play.
1. Select Upload APK.
1. Choose from the Production, Beta, or Alpha channels and select Upload your APK. For more information on alpha/beta testing, go to use alpha/beta testing & staged rollouts.


###7.Security Server OAuth2

OAuth2 is an standardized authorization protocol/framework. The OAuth 2.0 authorization framework enables a third-party application to obtain limited access to an HTTP service, either on behalf of a resource owner by orchestrating an approval interaction between the resource owner and the HTTP service, or by allowing the third-party application to obtain access on its own behalf.

Our implementation follow Resource-owner Password Grant flow of OAUth2 specification.



### References:
[Android Training](https://developer.android.com/training/basics/firstapp/starting-activity.html)

[Starting Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)

[Google Play Developer](https://play.google.com/apps/publish/signup/)

[The OAuth 2.0 Authorization Framework](https://tools.ietf.org/html/rfc6749)

[Spring](http://projects.spring.io/spring-framework/)
