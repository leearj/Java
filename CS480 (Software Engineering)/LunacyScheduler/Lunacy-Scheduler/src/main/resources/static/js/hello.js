var cs480App = angular.module('check', ['firebase']);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };

firebase.initializeApp(config);
var database = firebase.database();
var currentUser;

cs480App.controller('hello', function ($scope, $firebaseArray,$firebaseObject, $log) {

  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      window.user = user; // user is undefined if no user signed in
      console.log("UID" + user.uid);
			console.log("Window" + window.user);
			console.log(user);
      currentUser = firebase.auth().currentUser;
    } else {
      console.log("No user is signed in")
    }

  });

});
