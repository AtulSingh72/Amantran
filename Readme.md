<p align="center">
  <a href="" rel="noopener">
 <img src=""https://github.com/AtulSingh72/Amantran/blob/master/images/image1.jpg"" alt="Images"></a>
  <a href="" rel="noopener">
 <img src=""https://github.com/AtulSingh72/Amantran/blob/master/images/image3.jpg"" alt="Images"></a>
  <a href="" rel="noopener">
 <img src=""https://github.com/AtulSingh72/Amantran/blob/master/images/image2.jpg"" alt="Images"></a>
  <a href="" rel="noopener">
 <img src=""https://github.com/AtulSingh72/Amantran/blob/master/images/image4.jpg"" alt="Images"></a>
</p>

<h3 align="center">Amantran</h3>

---

<p align="center"> Amantran is an Android App to maintain and edit a Guest List and view others' Guest List.
    <br> 
</p>

## 📝 Table of Contents

- [About](#about)
- [How it's made](#howtouse)
- [Usage](#usage)
- [Built Using](#tech)  

## 🧐 About <a name = "about"></a>

Amantran is an Android Application that aims at easing your task at remembering names for an event. You may simply use this to app to make a list of all your Guests in this App and never forget to call anyone of them. You can also share this app with other members of your family and thus can distribute your task of preparing the Guest List. You can also view there made Guest Lists in real-time.

## :thinking: How it's made <a name = "howtouse"></a>
The App Development consisted of 3 main phases:
- ### API Setup
  For building and seting up the APIs, we used NodeJS, ExpressJS and MongoDB. Using NodeJS and ExpressJS, routes were setuped and the database is was stored using MongoDB. For database storage purposes, cloud storage offered by Mongo Atlas was used. The routes fetched, handled and forwaded data in form of JSON which then could be used in our App or via Postman.
  The code was then pushed onto a Heroku server and was made live for use.

- ### App Backend
  The Android App is built using Java as it's backend language. The routes were hit and the data were parsed using Volley dependency for Android. Evey user (owner of the Guest List) had a unique ID which was stored in the Shared Preferences to enable the app to have a one-time login system. The name which he uses while login is used and his/her list is available to other users by that name. Volley has been the choice for this app solely because it makes it simple to make RestAPI calls.

- ### App Frontend
  The frontend was built using simple UI Material Design elements and a custom ListView Styling.

## 🎈 Usage <a name="usage"></a>

Using the app is fairly simple and intuitive. Below are the detailed steps for the same.
1. Just login to the app with you Name. Remember that this name will be shown to others. This Login Screen will only appear on your first opening of the app.
2. Now you get a screen displaying various other users of the app on top and two options at the bottom to choose your Friend or Family Guest List. You can view other's Guest List by clicking on his/her name.
3. Clicking on Friends or Family will present you with a list of Guests entered and you also get an option to add or remove the Guests.
4. You can also add, edit or delete the Guest from the lists.

## ⛏️ Built Using <a name = "tech"></a>

- [MongoDB](https://www.mongodb.com/) - Database
- [Express](https://expressjs.com/) - Server Framework
- [NodeJs](https://nodejs.org/en/) - Server Environment
- [Android Studio](https://developer.android.com/studio) - Building Android App
- [Volley](https://developer.android.com/training/volley) - RestAPI call in App
