# Subletting Made Easy
![Homepage](homepage.jpeg)
Subletting Made Easy was made specifically to connect students who are looking to sublet thier apartments with those who are seeking for a place to stay. This platform makes finding and posting sublets as simple as possible, and provides an organized way to manage and display information. 

## Getting Started
This app was built using Android Studio. Simply install that [here](https://developer.android.com/studio) and follow the instructions on the site. 

### Dependencies
We utilize Google's Firebase database and authentication tools to store and retrieve information gathered from the app, and Butter Knife to bind different android views and callbacks to fields and methods. Add both these dependecies in `build.gradle` to build the app.

Firebase will also need to be connected with a Google Account to handle information transfer. 

### Integrating DocuSign
As part of our terms and service agreement within the app, we use DocuSign's *Click* API to capture consent for users. Creating a unique clickwrap for our application can be found [here](https://support.docusign.com/en/guides/click-user-guide).

## Usage
Simply build and run the application from an emulator or phone. After starting up the app, create an account to see create or see listings. Firebase provides a method of authenticating users without the need to log in after creating an account. Currently, this feature can be toggled on or off under `LoginActivity.java` under `onStart()`. 

Users seeking to sublet their apartment can add an listing to the app through a button on the bottom right-hand corner, which also automatically gets stored by Firebase and gets updated in the listings homepage.
