var cs480App = angular.module('check2', ['firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };

firebase.initializeApp(config);
var database = firebase.database();
var currentUser;

cs480App.controller('hello2', function ($scope, $firebaseArray,$firebaseObject, $log) {

  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      window.user = user; // user is undefined if no user signed in
      console.log(user);

      currentUser = firebase.auth().currentUser;
      saveCurrentUser(currentUser);
    } else {
      console.log("No user is signed in")
    }
  });

  function saveCurrentUser(user){
    currentUser = user;
  }

  console.log("Window: " + window.user);
  console.log("currentUser: " + currentUser);

});
