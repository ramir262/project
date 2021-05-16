# PantherInspect

## Requirements
* Java (JDK 1.8)
* JavaFX 1.3
* mysql-connector-java-8.0.20.jar
* mybatis-3.5.6.jar

## Setup Instructions
* Create a local mySQL database with a database called PantherInspect
* Create a copy of example.env called .env in the root directory of the Project.
* In the .env file, enter your database username and password
    * USER=yourdatabaseusernamehere
    * PASS=yourdatabasepasswordhere
* In the .env file, enter the path of an empty folder on your local machine where account profile pictures can be stored.
    * e.g. UPLOAD_PATH=C:\someFolder\
* Open application

## Source Files
* SignupPage.java
* AccountSettingsPage.java
* ProfileSettingsPage.java
* DeletePostPage.java
* ForgotPasswordPage.java
* HomePage.java
* ProfilePage.java
* RateCoursePage.java
* ViewPostPage.java

* CourseMenu.java
* SettingsMenu.java
* SubjectMenu.java
* SubmitMenu.java

* SplashScreenLoader.java

* BCrypt.java (Copyright (c) 2006 Damien Miller <djm@mindrot.org>)
* Data.java
* Database.java
* QueryProcessor.java
* Env.java
* ErrorPopup.java
* Time.java

## Runner
* PantherInspectProject.java
