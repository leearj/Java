var cs480App = angular.module('lunacyscheduler.createEvent', ['ui.bootstrap', 'firebase']);
var signUpMod = angular.module('SignUp', []);

var config = {
	    apiKey: "AIzaSyC28G2x-Xnel2wtmKnw3xi-a8TmWrnJ7tI",
	    authDomain: "lunacy-scheduler.firebaseapp.com",
	    databaseURL: "https://lunacy-scheduler.firebaseio.com",
	    projectId: "lunacy-scheduler"
	  };
firebase.initializeApp(config);
var database = firebase.database();

    $(document).ready(function() {
    var boxOptions = "";
    var dropDown = document.getElementById("category");
    var query = firebase.database().ref("user").orderByKey();
               query.once("value")
                .then(function(snapshot) {
                    snapshot.forEach(function(childSnapshot) {
                        var user = childSnapshot.key;
                        jQuery('<option/>', {
                                value: user,
                                html: user
                                }).appendTo('#dropdown select');
                    });
                });
    });

signUpMod.controller('SignIn', function ($scope) {
	$scope.signin = function() {
		firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
			  // Handle Errors here.
			  var errorCode = error.code;
			  var errorMessage = error.message;
			  alert(errorMessage);
			  // ...
			});
		alert("Successfully logged in");
	}
});

cs480App.controller('ReadDataCtrl', function ($scope, $firebaseObject,$firebaseArray) {
	var userRef = database.ref("user");
	$scope.users = new $firebaseArray(userRef);

});

signUpMod.controller('SignUpUser', function ($scope) {
	$scope.signup = function() {
		if($scope.first.password != $scope.confirm.password) {
			firebase.auth().createUserWithEmailAndPassword($scope.signup.email, $scope.confirm.password).catch(function(error) {
				  // Handle Errors here.
				  var errorCode = error.code;
				  var errorMessage = error.message;
				  alert(errorMessage);
				  // ...
				});
			alert("Account successfully created");
		} else {
			alert("Passwords do not match!");
		}
	}
});

cs480App.controller('DatepickerPopupDemoCtrl', function ($scope, $log) {


  //DatePicker
  $scope.today = function() {
	    $scope.dt = new Date();
	    $scope.dt.end = new Date();

  };
  $scope.today();

  $scope.clear = function() {
    $scope.dt = null;
    $scope.dt.end = null;
  };


  $scope.dateOptions = {
    minDate: new Date(),
    showWeeks: false
  };

  $scope.dateOptions1 = {
	minDate: $scope.dt,
	showWeeks: false
  };
  $scope.open1 = function() {
    $scope.popup1.opened = true;
  };

  $scope.open2 = function() {
	$scope.dateOptions1.minDate = $scope.dt;
    $scope.popup2.opened = true;
  };

  $scope.setDate = function(year, month, day) {
    $scope.dt = new Date(year, month, day);
  };

  $scope.popup1 = {
    opened: false
  };

  $scope.popup2 = {
    opened: false
  };

	//Timepicker

		  $scope.submit = function() {

			  database.ref('user/' + $scope.title).set({
				  	title: $scope.title,
				  	description: $scope.description,
				    start: $scope.dt.getMonth() + 1 + "/" + $scope.dt.getDate() + "/" + $scope.dt.getFullYear(),
				    end: $scope.dt.end.getMonth() + 1 +  "/" + $scope.dt.end.getDate() + "/" + $scope.dt.end.getFullYear()
				  });

			        alert("Successfully Added");
		  };
    });

        $scope.start_time = {
        	date : ""
        };
        $scope.end_time = {
        	date : ""
        };

    // TODO
    // Submit start and end time
    // Calculate best time available
