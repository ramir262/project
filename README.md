# PantherInspect

## Mebers
* Cindy Ramirez
* Matthew Parnham
* Allison Thompson

## Link
https://github.com/ramir262/project

## Description
PantherInspect gives students the ability to rate a course or view reviews already existing for a course.  A student can easily view an average rating of a course and even filter reviews by star count or professor.  When making a review, a student may select a star count and will be prompted with questions to answer.  Students must create an account to access this service.

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

## Images
* SplashScreen.png
* icon.png
* star.jpeg

## SQL Files
* tables.sql
* courses.sql
* posts.sql

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
